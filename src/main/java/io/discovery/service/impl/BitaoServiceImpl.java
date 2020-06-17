package io.discovery.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.request.*;
import com.taobao.api.response.*;
import io.discovery.common.exception.SzException;
import io.discovery.config.TaobaoConfig;
import io.discovery.constant.CommonConstant;
import io.discovery.constant.TaobaoOrderConst;
import io.discovery.dao.BitaoDao;
import io.discovery.dao.WalletDao;
import io.discovery.entity.*;
import io.discovery.form.AuthorizationTaobaoForm;
import io.discovery.form.FavoriteForm;
import io.discovery.form.TpwdForm;
import io.discovery.service.BitaoService;
import io.discovery.util.HttpUtil;
import io.discovery.util.Md5;
import io.discovery.vo.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 币淘模块Service实现层
 *
 * @author fzx
 * @date 2018/1/2
 */
@Service
public class BitaoServiceImpl extends ServiceImpl<BitaoDao, RotationChartEntity> implements BitaoService {
  @Autowired
  private BitaoDao bitaoDao;
  @Autowired
  private TaobaoConfig taobaoConfig;
  @Autowired
  private WalletDao walletDao;

  @Override
  public Map<String, Object> getAdvertisementPhoto() {
    Map<String, Object> resultMap = new HashMap<>(1);
    List<RotationChartVO> rotationChartVOS = bitaoDao.getAdvertisementPhoto();
    resultMap.put("events", rotationChartVOS);
    return resultMap;
  }

  @Override
  public IPage<HotGoodsVO> getHotGoods(Integer page, Integer limit) {
    IPage<HotGoodsVO> hotGoodsVOPage = new Page<>();
    List<HotGoodsVO> records = new ArrayList(limit);
    try {
      //公共请求参数
      DefaultTaobaoClient client = new DefaultTaobaoClient(taobaoConfig.getTbkUrl(), taobaoConfig.getAppKey(), taobaoConfig.getAppSecret());
      //请求类（根据不同需求更换）
      TbkDgItemCouponGetRequest req = new TbkDgItemCouponGetRequest();
      //封装私有请求参数
      req.setAdzoneId(Long.parseLong(taobaoConfig.getAdzongId()));
      req.setPageSize(limit.longValue());
      req.setPageNo(page.longValue());
      //执行请求
      TbkDgItemCouponGetResponse rsp = client.execute(req);
      //解析请求结果
      Map result = (Map) JSON.parse(rsp.getBody());
      Map m1 = (Map) result.get("tbk_dg_item_coupon_get_response");
      Map m2 = (Map) m1.get("results");
      JSONArray jsonArray = (JSONArray) m2.get("tbk_coupon");
      //封装VO
      for (int i = 0; i < jsonArray.size(); i++) {
        HotGoodsVO hotGoodsVO = new HotGoodsVO();
        //淘宝宝贝ID
        hotGoodsVO.setItemId(Long.parseLong(jsonArray.getJSONObject(i).get("num_iid").toString()));
        //商品主图
        hotGoodsVO.setPicUrl(jsonArray.getJSONObject(i).get("pict_url").toString().replaceAll("https:", "").replaceAll("http:", ""));
        //标题
        hotGoodsVO.setTitle(jsonArray.getJSONObject(i).get("title").toString());
        //来源（根据user_type判断 0集市-->淘宝 1商城-->天猫）
        hotGoodsVO.setSource(Integer.parseInt(jsonArray.getJSONObject(i).get("user_type").toString()) == 0 ? "taobao" : "tmall");
        //根据折扣信息是否为空判断此商品有无优惠券
        hotGoodsVO.setHasCoupon(jsonArray.getJSONObject(i).get("coupon_info").toString() != null ? true : false);
        //折扣价
        BigDecimal zkFinalPrice = new BigDecimal(jsonArray.getJSONObject(i).get("zk_final_price").toString());
        hotGoodsVO.setZkFinalPrice(zkFinalPrice);
        //若有优惠券 则通过优惠信息解析出券面额
        String couponDenomination = null;
        if (hotGoodsVO.getHasCoupon()) {
          String couponInfo = jsonArray.getJSONObject(i).get("coupon_info").toString().trim();
          String number = "[^0-9]";
          String symbol = ",";
          for (String str : couponInfo.replaceAll(number, symbol).split(symbol)) {
            if (str.length() > 0) {
              couponDenomination = str;
            }
          }
          //券面额
          hotGoodsVO.setCouponDiscount(new BigDecimal(couponDenomination));
        }
        //计算得出券后价（zk_final_price折后价-couponDenomination券面额）
        hotGoodsVO.setCouponedPrice(zkFinalPrice.subtract(new BigDecimal(couponDenomination)));
        //计算得出返币量（（zk_final_price折后价-couponDenomination券面额）*佣金率(需乘0.01转为小数)*闪住给用户比率*闪住币汇率）
        BigDecimal szcToCny = walletDao.getProportion(CommonConstant.SZCID, CommonConstant.CNYID).getAmount();
        hotGoodsVO.setCommissionAmount((zkFinalPrice.subtract(new BigDecimal(couponDenomination)))
            .multiply(new BigDecimal(jsonArray.getJSONObject(i).get("commission_rate").toString()))
            .multiply(new BigDecimal(0.01)).multiply(new BigDecimal(CommonConstant.SHANZHURATE).divide(szcToCny)));
        //月销量
        hotGoodsVO.setVolume(Integer.parseInt(jsonArray.getJSONObject(i).get("volume").toString()));
        //跳转链接
        hotGoodsVO.setCouponUrl(jsonArray.getJSONObject(i).get("coupon_click_url").toString());
        //入库（有当前itemId则更新，没有则新增）
        TaobaoGoodsEntity taobaoGoodsEntity = groupDO(jsonArray.getJSONObject(i), "hotGoods");
        Integer count = bitaoDao.selectCount(taobaoGoodsEntity.getItemId());
        if (count == 1) {
          bitaoDao.updateGoods(taobaoGoodsEntity);
        } else {
          bitaoDao.saveGoods(taobaoGoodsEntity);
        }
        //将数据封装至page下的records
        records.add(hotGoodsVO);
      }
      hotGoodsVOPage.setCurrent(page.longValue());
      hotGoodsVOPage.setSize(limit);
      hotGoodsVOPage.setTotal(Integer.parseInt(m1.get("total_results").toString()));
      hotGoodsVOPage.setRecords(records);
    } catch (ApiException e) {
      e.printStackTrace();
    }
    return hotGoodsVOPage;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public IPage<SearchGoodsVO> searchGoods(UserEntity userEntity, Integer page, Integer limit, String keyword, String sort, String order, HttpServletRequest request) {
    if (StringUtils.isBlank(keyword)) {
      throw new SzException("搜索内容不能为空！");
    }
    IPage<SearchGoodsVO> searchGoodsVOIPage = new Page<>();
    List<SearchGoodsVO> records = new ArrayList(limit);
    try {
      //封装公共请求参数
      DefaultTaobaoClient client = new DefaultTaobaoClient(taobaoConfig.getTbkUrl(), taobaoConfig.getAppKey(), taobaoConfig.getAppSecret());
      //请求类（根据不同需求更换）
      TbkDgMaterialOptionalRequest req = new TbkDgMaterialOptionalRequest();
      //封装私有请求参数
      req.setStartDsr(10L);
      req.setPageSize(limit.longValue());
      req.setPageNo(page.longValue());
      req.setPlatform(1L);
      req.setSort(sort + "_" + order);
      req.setQ(keyword);
      req.setMaterialId(2836L);
      req.setHasCoupon(true);
      req.setIp(HttpUtil.getRealIp(request));
      req.setAdzoneId(Long.parseLong(taobaoConfig.getAdzongId()));
      req.setNeedFreeShipment(true);

      //解析数据
      TbkDgMaterialOptionalResponse rsp = client.execute(req);
      Map result = (Map) JSON.parse(rsp.getBody());

      String errorResponse = "error_response";
      String errCode = "50001";
      String returnCode = "sub_code";
      if (null != result.get(errorResponse)) {
        Map errMap = (Map) result.get(errorResponse);
        if (errCode.equals(errMap.get(returnCode).toString())) {
          searchGoodsVOIPage.setTotal(0);
          return searchGoodsVOIPage;
        }
      }
      Map m1 = (Map) result.get("tbk_dg_material_optional_response");
      Map m2 = (Map) m1.get("result_list");
      JSONArray jsonArray = (JSONArray) m2.get("map_data");
      for (int i = 0; i < jsonArray.size(); i++) {
        //封装VO用于返回前端
        SearchGoodsVO searchGoodsVO = groupVO(jsonArray.getJSONObject(i));
        //封装数据进page
        records.add(searchGoodsVO);

        //封装DO用于更新数据库
        TaobaoGoodsEntity taobaoGoodsEntity = groupDO(jsonArray.getJSONObject(i), "searchGoods");

        //入库（有当前itemId则更新，没有则新增）
        Integer count = bitaoDao.selectCount(taobaoGoodsEntity.getItemId());
        if (count == 1) {
          bitaoDao.updateGoods(taobaoGoodsEntity);
        } else {
          bitaoDao.saveGoods(taobaoGoodsEntity);
        }
      }

      //存入搜索关键字
      if (userEntity == null) {
        bitaoDao.saveKeyword(null, keyword);
      } else {
        bitaoDao.saveKeyword(userEntity.getId(), keyword);
      }
      //封装Page数据
      searchGoodsVOIPage.setCurrent(page.longValue());
      searchGoodsVOIPage.setSize(limit);
      searchGoodsVOIPage.setTotal(Integer.parseInt(m1.get("total_results").toString()));
      searchGoodsVOIPage.setRecords(records);
    } catch (ApiException e) {
      e.printStackTrace();
    }
    return searchGoodsVOIPage;
  }

  /**
   * 封装VO
   */
  private SearchGoodsVO groupVO(Map map) {
    //封装数据返回前端
    SearchGoodsVO searchGoodsVO = new SearchGoodsVO();
    //淘宝宝贝ID
    searchGoodsVO.setItemId(Long.parseLong(map.get("num_iid").toString()));
    //商品主图
    searchGoodsVO.setPicUrl(map.get("pict_url").toString().replaceAll("https:", "").replaceAll("http:", ""));
    //标题
    searchGoodsVO.setTitle(map.get("title").toString());
    //来源（根据user_type判断 0集市-->淘宝 1商城-->天猫）
    searchGoodsVO.setSource(Integer.parseInt(map.get("user_type").toString()) == 0 ? "taobao" : "tmall");
    //根据折扣信息是否为空判断此商品有无优惠券
    searchGoodsVO.setHasCoupon(map.get("coupon_info").toString() != null ? true : false);
    //折扣价
    BigDecimal zkFinalPrice = new BigDecimal(map.get("zk_final_price").toString());
    searchGoodsVO.setZkFinalPrice(zkFinalPrice);
    //若有优惠券 则通过优惠信息解析出券面额
    String couponDenomination = null;
    if (searchGoodsVO.getHasCoupon()) {
      String couponInfo = map.get("coupon_info").toString().trim();
      String number = "[^0-9]";
      String symbol = ",";
      for (String str : couponInfo.replaceAll(number, symbol).split(symbol)) {
        if (str.length() > 0) {
          couponDenomination = str;
        }
      }
      //券面额
      searchGoodsVO.setCouponDiscount(new BigDecimal(couponDenomination));
    }
    //计算得出券后价（zk_final_price折后价-couponDenomination券面额）
    searchGoodsVO.setCouponedPrice(zkFinalPrice.subtract(new BigDecimal(couponDenomination)));
    //计算得出返币量（（zk_final_price折后价-couponDenomination券面额）*佣金率(需乘0.0001转为小数)*闪住给用户比率*闪住币汇率）
    BigDecimal szcToCny = walletDao.getProportion(CommonConstant.SZCID, CommonConstant.CNYID).getAmount();
    searchGoodsVO.setCommissionAmount((zkFinalPrice.subtract(new BigDecimal(couponDenomination)))
        .multiply(new BigDecimal(map.get("commission_rate").toString()))
        .multiply(new BigDecimal(0.0001)).multiply(new BigDecimal(CommonConstant.SHANZHURATE).divide(szcToCny)));
    //月销量
    searchGoodsVO.setVolume(Integer.parseInt(map.get("volume").toString()));
    //所在地
    searchGoodsVO.setProvcity(map.get("provcity").toString());
    //二合一链接
    String couponShareUrl = "https:" + map.get("coupon_share_url").toString();
    searchGoodsVO.setCouponUrl(couponShareUrl);

    return searchGoodsVO;
  }

  /**
   * 封装DO
   */
  private TaobaoGoodsEntity groupDO(Map map, String type) {
    TaobaoGoodsEntity taobaoGoodsEntity = new TaobaoGoodsEntity();
    try {
      taobaoGoodsEntity.setItemId(Long.parseLong(map.get("num_iid").toString()));
      taobaoGoodsEntity.setPicUrl(map.get("pict_url").toString().replaceAll("https:", "").replaceAll("http:", ""));
      taobaoGoodsEntity.setItemUrl(map.get("item_url").toString());
      String smallImagesStr = "small_images";
      if (null != map.get(smallImagesStr)) {
        Map smallImages = (Map) map.get("small_images");
        JSONArray jsonArray = (JSONArray) smallImages.get("string");
        List<String> photos = new ArrayList<>(jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
          String photo = "\"" + jsonArray.get(i).toString().replaceAll("https:", "").replaceAll("http:", "") + "\"";
          photos.add(photo);
        }
        taobaoGoodsEntity.setPhotos(photos.toString());
      }
      taobaoGoodsEntity.setTitle(map.get("title").toString());
      taobaoGoodsEntity.setSource(Integer.parseInt(map.get("user_type").toString()) == 0 ? "taobao" : "tmall");
      BigDecimal zkFinalPrice = new BigDecimal(map.get("zk_final_price").toString());
      taobaoGoodsEntity.setHasCoupon(map.get("coupon_info").toString() != null ? true : false);
      taobaoGoodsEntity.setZkFinalPrice(zkFinalPrice);
      String couponDenomination = null;
      if (taobaoGoodsEntity.getHasCoupon()) {
        String couponInfo = map.get("coupon_info").toString().trim();
        String number = "[^0-9]";
        String symbol = ",";
        for (String str : couponInfo.replaceAll(number, symbol).split(symbol)) {
          if (str.length() > 0) {
            couponDenomination = str;
          }
        }
      }
      taobaoGoodsEntity.setCouponDiscount(new BigDecimal(couponDenomination));
      taobaoGoodsEntity.setCouponedPrice(zkFinalPrice.subtract(new BigDecimal(couponDenomination)));
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      taobaoGoodsEntity.setCouponStartTime(sdf.parse(map.get("coupon_start_time").toString()));
      taobaoGoodsEntity.setCouponEndTime(sdf.parse(map.get("coupon_end_time").toString()));
      String hotGoods = "hotGoods";
      String searchGoods = "searchGoods";
      String hotGoodsRate = "0.01";
      String searchGoodsRate = "0.0001";
      if (type == hotGoods) {
        taobaoGoodsEntity.setCommissionRate(new BigDecimal(map.get("commission_rate").toString()).multiply(new BigDecimal(hotGoodsRate)));
      } else if (type == searchGoods) {
        taobaoGoodsEntity.setCommissionRate(new BigDecimal(map.get("commission_rate").toString()).multiply(new BigDecimal(searchGoodsRate)));
      }
      taobaoGoodsEntity.setVolume(Integer.parseInt(map.get("volume").toString()));
      taobaoGoodsEntity.setSellerId(Long.parseLong(map.get("seller_id").toString()));
      String provcity = "provcity";
      String couponShareurl = "coupon_share_url";
      if (map.get(provcity) != null) {
        taobaoGoodsEntity.setProvcity(map.get("provcity").toString());
      }
      if (map.get(couponShareurl) != null) {
        String couponShareUrl = "https:" + map.get("coupon_share_url").toString();
        taobaoGoodsEntity.setCouponUrl(couponShareUrl);
      } else {
        taobaoGoodsEntity.setCouponUrl(map.get("coupon_click_url").toString());
      }
      //封装店铺信息存库
      TaobaoShopEntity taobaoShopEntity = new TaobaoShopEntity();
      taobaoShopEntity.setSellerId(Long.parseLong(map.get("seller_id").toString()));
      taobaoShopEntity.setShopTitle(map.get("shop_title").toString());
      String shopDsr = "shop_dsr";
      if (map.get(shopDsr) != null) {
        taobaoShopEntity.setShopDsr(Integer.parseInt(map.get("shop_dsr").toString()));
      }
      taobaoShopEntity.setSource(Integer.parseInt(map.get("user_type").toString()) == 0 ? "taobao" : "tmall");
      Integer count = bitaoDao.getShopCountBySellerId(Long.parseLong(map.get("seller_id").toString()));
      if (count > 0) {
        bitaoDao.updateShop(taobaoShopEntity);
      } else {
        bitaoDao.savePartShop(taobaoShopEntity);
      }
      //取出刚刚存入的店铺信息再存入商品表中
      taobaoGoodsEntity.setShopId(bitaoDao.getShopIdBySellerId(Long.parseLong(map.get("seller_id").toString())));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return taobaoGoodsEntity;
  }

  /**
   * 封装猜你喜欢VO
   */
  private GuessLikeVO groupGuessLikeVO(Map map) {
    //封装数据返回前端
    GuessLikeVO guessLikeVO = new GuessLikeVO();
    //淘宝宝贝ID
    guessLikeVO.setItemId(Long.parseLong(map.get("item_id").toString()));
    //商品主图
    guessLikeVO.setPicUrl(map.get("pict_url").toString().replaceAll("https:", "").replaceAll("http:", ""));
    //标题
    guessLikeVO.setTitle(map.get("title").toString());
    //来源 再调接口获取
    TaobaoGoodsEntity taobaoGoodsEntity = getItemDetail(map.get("item_id").toString());
    guessLikeVO.setSource(taobaoGoodsEntity.getSource());
    //二合一链接
    String couponClickUrl = "coupon_click_url";
    if (map.get(couponClickUrl) != null) {
      String couponShareUrl = "https:" + map.get("coupon_click_url").toString();
      guessLikeVO.setCouponUrl(couponShareUrl);
    }
    return guessLikeVO;
  }

  /**
   * 封装猜你喜欢DO
   */
  private TaobaoGoodsEntity groupGuessLikeDO(Map map) {
    TaobaoGoodsEntity taobaoGoodsEntity = new TaobaoGoodsEntity();
    //直接可获取数据
    taobaoGoodsEntity.setItemId(Long.parseLong(map.get("item_id").toString()));
    taobaoGoodsEntity.setPicUrl(map.get("pict_url").toString().replaceAll("https:", "").replaceAll("http:", ""));
    String prefix = "https:";
    String clickUrl = "click_url";
    if (!map.get(clickUrl).toString().startsWith(prefix)) {
      taobaoGoodsEntity.setItemUrl("https:" + map.get("click_url").toString());
    } else {
      taobaoGoodsEntity.setItemUrl(map.get("click_url").toString());
    }
    taobaoGoodsEntity.setTitle(map.get("title").toString());
    BigDecimal zkFinalPrice = new BigDecimal(map.get("zk_final_price").toString());
    taobaoGoodsEntity.setZkFinalPrice(zkFinalPrice);
    taobaoGoodsEntity.setHasCoupon(map.get("coupon_amount").toString() != null ? true : false);
    taobaoGoodsEntity.setCouponDiscount(new BigDecimal(map.get("coupon_amount").toString()));
    //计算券后价，判断是否达到满减
    BigDecimal couponStartFee = new BigDecimal(map.get("coupon_start_fee").toString());
    if (couponStartFee.compareTo(zkFinalPrice) <= 0) {
      taobaoGoodsEntity.setCouponedPrice(zkFinalPrice.subtract(new BigDecimal(map.get("coupon_amount").toString())));
    } else {
      taobaoGoodsEntity.setCouponedPrice(zkFinalPrice);
    }
    taobaoGoodsEntity.setVolume(Integer.parseInt(map.get("volume").toString()));
    taobaoGoodsEntity.setCommissionRate(new BigDecimal(map.get("commission_rate").toString()).multiply(new BigDecimal(0.01)));
    String couponClickUrl = "coupon_click_url";
    if (map.get(couponClickUrl) != null) {
      String couponClickUri = "coupon_click_url";
      if (!map.get(couponClickUri).toString().startsWith(prefix)) {
        taobaoGoodsEntity.setCouponUrl("https:" + map.get("coupon_click_url").toString());
      } else {
        taobaoGoodsEntity.setCouponUrl(map.get("coupon_click_url").toString());
      }
    }
    //需要调用商品详情接口获取的数据
    //根据itemId获取调用商品详情接口获取数据
    TaobaoGoodsEntity taobaoGoodsDeatilEntity = getItemDetail(map.get("item_id").toString());
    taobaoGoodsEntity.setPhotos(taobaoGoodsDeatilEntity.getPhotos());
    taobaoGoodsEntity.setSource(taobaoGoodsDeatilEntity.getSource());
    taobaoGoodsEntity.setSellerId(taobaoGoodsDeatilEntity.getSellerId());
    taobaoGoodsEntity.setProvcity(taobaoGoodsDeatilEntity.getProvcity());

    //封装店铺信息存库
    TaobaoShopEntity taobaoShopEntity = new TaobaoShopEntity();
    taobaoShopEntity.setSellerId(taobaoGoodsDeatilEntity.getSellerId());
    taobaoShopEntity.setSource(taobaoGoodsDeatilEntity.getSource());
    Integer count = bitaoDao.getShopCountBySellerId(taobaoGoodsDeatilEntity.getSellerId());
    if (count > 0) {
      bitaoDao.updateShop(taobaoShopEntity);
    } else {
      bitaoDao.savePartShop(taobaoShopEntity);
    }
    //取出刚刚存入的店铺信息再存入商品表中
    taobaoGoodsEntity.setShopId(bitaoDao.getShopIdBySellerId(taobaoGoodsDeatilEntity.getSellerId()));
    return taobaoGoodsEntity;
  }

  /**
   * 从淘宝获取商品详情
   */
  private TaobaoGoodsEntity getItemDetail(String itemId) {
    TaobaoGoodsEntity taobaoGoodsEntity = new TaobaoGoodsEntity();
    try {
      DefaultTaobaoClient client = new DefaultTaobaoClient(taobaoConfig.getTbkUrl(), taobaoConfig.getAppKey(), taobaoConfig.getAppSecret());
      TbkItemInfoGetRequest req = new TbkItemInfoGetRequest();
      req.setNumIids(itemId);
      TbkItemInfoGetResponse rsp = client.execute(req);
      Map result = (Map) JSON.parse(rsp.getBody());
      Map tbkItemInfoGetResponse = (Map) result.get("tbk_item_info_get_response");
      com.alibaba.fastjson.JSONObject tbkItemInfo = (com.alibaba.fastjson.JSONObject) result.get("tbk_item_info_get_response");
      String identification = "results";
      if (null != tbkItemInfo.get(identification)) {
        Map results = (Map) tbkItemInfoGetResponse.get("results");
        JSONArray jsonArray = (JSONArray) results.get("n_tbk_item");
        for (int i = 0; i < jsonArray.size(); i++) {
          Map smallImages = (Map) ((Map) jsonArray.get(i)).get("small_images");
          if (null != smallImages) {
            JSONArray pArray = (JSONArray) smallImages.get("string");
            List<String> photos = new ArrayList<>(jsonArray.size());
            for (int j = 0; j < pArray.size(); j++) {
              String photo = "\"" + pArray.get(j).toString().replaceAll("https:", "").replaceAll("http:", "") + "\"";
              photos.add(photo);
            }
            taobaoGoodsEntity.setPhotos(photos.toString());
          }
          taobaoGoodsEntity.setSource(Integer.parseInt(jsonArray.getJSONObject(i).get("user_type").toString()) == 0 ? "taobao" : "tmall");
          taobaoGoodsEntity.setSellerId(Long.parseLong(jsonArray.getJSONObject(i).get("seller_id").toString()));
          taobaoGoodsEntity.setProvcity(jsonArray.getJSONObject(i).get("provcity").toString());
          taobaoGoodsEntity.setTitle(jsonArray.getJSONObject(i).get("title").toString());
          taobaoGoodsEntity.setItemUrl(jsonArray.getJSONObject(i).get("item_url").toString());
          taobaoGoodsEntity.setZkFinalPrice(new BigDecimal(jsonArray.getJSONObject(i).get("zk_final_price").toString()));
          taobaoGoodsEntity.setVolume(Integer.parseInt(jsonArray.getJSONObject(i).get("volume").toString()));
          taobaoGoodsEntity.setSellerId(Long.parseLong(jsonArray.getJSONObject(i).get("seller_id").toString()));
          taobaoGoodsEntity.setItemId(Long.parseLong(jsonArray.getJSONObject(i).get("num_iid").toString()));
        }
      }
    } catch (ApiException e) {
      e.printStackTrace();
    }
    return taobaoGoodsEntity;
  }

  @Override
  public Map<String, List<String>> getSearchHistory(UserEntity userEntity) {
    Map<String, List<String>> resultMap = new HashMap<>(1);
    if (userEntity != null) {
      resultMap.put("keyword", bitaoDao.getSearchHistory(userEntity.getId()));
    } else {
      List<SearchHistoryVO> searchHistoryVOS = bitaoDao.getSearchHistoryUnLogin();
      List<String> keywordList = new ArrayList<>(5);
      for (int i = 0; i < searchHistoryVOS.size(); i++) {
        keywordList.add(searchHistoryVOS.get(i).getKeyword());
      }
      resultMap.put("keyword", keywordList);
    }
    return resultMap;
  }

  @Override
  public Map<String, Object> deleteSearchHistory(UserEntity userEntity) {
    Map<String, Object> resultMap = new HashMap<>(2);
    try {
      bitaoDao.deleteSearchHistory(userEntity.getId());
      resultMap.put("code", CommonConstant.SUCCESS);
      resultMap.put("msg", "删除搜索历史成功");
    } catch (Exception e) {
      e.printStackTrace();
      throw new SzException("删除搜索历史失败");
    }
    return resultMap;
  }

  @Override
  public ItemDetailVO getItemDetail(UserEntity userEntity, Long itemId, String source) {
    ItemDetailVO itemDetail = bitaoDao.getItemDetail(itemId, source);
    //用户进入商品详情足迹记录
    if (userEntity != null) {
      bitaoDao.userTrack(userEntity.getId(), itemDetail.getId(), source);
    } else {
      bitaoDao.userTrack(null, itemDetail.getId(), source);
    }
    Date gmtModified = bitaoDao.getItemGmtModify(itemId, source);
    Date now = new Date();
    if (itemDetail == null || itemDetail.getProvcity() == null || now.getTime() - gmtModified.getTime() > CommonConstant.REFLASHLIMIT) {
      //对应itemId不存在或者宝贝所在地为空或上一次更新记录的时间超过2小时则调详情接口更新数据库
      TaobaoGoodsEntity taobaoGoodsEntity = getItemDetail(itemId.toString());
      //入库（有当前itemId则更新，没有则新增）
      Integer count = bitaoDao.selectCount(taobaoGoodsEntity.getItemId());
      if (count == 1) {
        bitaoDao.updateGoodsPart(taobaoGoodsEntity);
      } else {
        bitaoDao.saveGoods(taobaoGoodsEntity);
      }
    }
    //计算返币量
    //在更新后的数据库中重新查出该宝贝详情
    ItemDetailVO itemDetailVO = bitaoDao.getItemDetail(itemId, source);
    BigDecimal szcToCny = walletDao.getProportion(CommonConstant.SZCID, CommonConstant.CNYID).getAmount();
    itemDetailVO.setCommissionAmount(itemDetailVO.getCouponedPrice().multiply(itemDetailVO.getCommissionRate())
        .multiply(new BigDecimal(CommonConstant.SHANZHURATE))
        .divide(szcToCny));
    //是否收藏
    if (userEntity == null) {
      itemDetailVO.setFavorite(false);
    } else {
      Integer count = bitaoDao.getFavoriteCount(userEntity.getId(), itemId, source);
      itemDetailVO.setFavorite(count == 1 ? true : false);
    }
    //相册
    if (null != itemDetailVO.getPictures()) {
      String pictures = itemDetailVO.getPictures();
      net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(pictures);
      itemDetailVO.setPhotos(jsonArray.toArray());
    }
    return itemDetailVO;
  }

  @Override
  public Object[] getDescription(String itemId, String source) {
    String detail = bitaoDao.getDescription(itemId, source);
    if (StringUtils.isNotBlank(detail)) {
      net.sf.json.JSONArray detailArray = net.sf.json.JSONArray.fromObject(detail);
      return detailArray.toArray();
    } else {
      return null;
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public ShopVO getShopInfo(Long itemId) {
    ShopVO shopVO = new ShopVO();
    try {
      //判断数据库对应itemId的宝贝店铺logo是否为空
      Integer count = bitaoDao.getShopCount(itemId);
      if (count == 0) {
        //对应itemId的宝贝店铺logo为空的情况调淘宝接口返回并更新数据库
        String id = "{\"itemNumId\":\"" + itemId + "\"}";
        String url = taobaoConfig.getShopUrl() + URLEncoder.encode(id, "utf-8");
        String jsonObject = HttpUtil.sendGet(url);
        JSONObject data = JSONObject.fromObject(jsonObject).getJSONObject("data").getJSONObject("seller");
        TaobaoShopEntity taobaoShopEntity = new TaobaoShopEntity();
        taobaoShopEntity.setSellerId(data.getLong("userId"));
        taobaoShopEntity.setSellerNick(data.getString("sellerNick"));
        taobaoShopEntity.setShopLogoUrl(data.getString("shopIcon"));
        taobaoShopEntity.setShopUrl(data.getString("taoShopUrl"));
        taobaoShopEntity.setShopTitle(data.getString("shopName"));

        net.sf.json.JSONArray jsonArray = data.getJSONArray("evaluates");
        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
        //判断店铺表中dsr是否已存在精准值
        Integer countDsr = bitaoDao.getDsr(data.getLong("userId"));
        if (countDsr == 0) {
          taobaoShopEntity.setShopDsr((int) (Double.parseDouble(jsonObject1.getString("score")) * 10000));
        }
        bitaoDao.updateShopInfo(taobaoShopEntity);

        //判断商品表图文详情是否为空
        Integer descCount = bitaoDao.getDetailDescriptionCount(itemId);
        if (descCount == 0) {
          //获取图文详情并存库
          List<String> detailDescription = new ArrayList<>(10);
          JSONObject descData = JSONObject.fromObject(jsonObject).getJSONObject("data").getJSONObject("item");
          //判断有没有图文详情链接
          String moduleDescUrl = "moduleDescUrl";
          if (null != descData.get(moduleDescUrl)) {
            String descUrl = "https:" + descData.getString("moduleDescUrl");
            String returnStr = HttpUtil.sendGet(descUrl);
            Map dataMap = (Map) JSON.parse(returnStr);
            Map d = (Map) dataMap.get("data");
            JSONArray children = (JSONArray) d.get("children");
            for (int i = 0; i < children.size(); i++) {
              com.alibaba.fastjson.JSONObject child = children.getJSONObject(i);
              String prefix = "detail_pic";
              if (child.get("ID").toString().startsWith(prefix)) {
                com.alibaba.fastjson.JSONObject params = child.getJSONObject("params");
                String picUrl = "picUrl";
                if (null != params.get(picUrl)) {
                  detailDescription.add("\"" + params.get("picUrl").toString() + "\"");
                }
              }
            }
            //入库
            bitaoDao.addDetailDescription(itemId, detailDescription.toString());
          }
        }
      }
      //查询数据库返回店铺信息
      shopVO = bitaoDao.getShopInfo(itemId);
      shopVO.setShopDsr(new BigDecimal(shopVO.getShopDsr().toString()).setScale(1, RoundingMode.HALF_UP));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return shopVO;
  }

  @Override
  public Map<String, Object> createTpwd(TpwdForm tpwdForm) {
    Map<String, Object> map = new HashMap<>(1);
    try {
      DefaultTaobaoClient client = new DefaultTaobaoClient(taobaoConfig.getTbkUrl(), taobaoConfig.getAppKey(), taobaoConfig.getAppSecret());
      TbkTpwdCreateRequest req = new TbkTpwdCreateRequest();
      req.setText(tpwdForm.getText());
      req.setUrl(tpwdForm.getUrl());
      req.setLogo(tpwdForm.getLogo());
      req.setExt("{}");
      TbkTpwdCreateResponse rsp = client.execute(req);
      Map result = (Map) JSON.parse(rsp.getBody());
      Map data = (Map) result.get("tbk_tpwd_create_response");
      map = (Map) data.get("data");
    } catch (ApiException e) {
      e.printStackTrace();
    }
    return map;
  }

  @Override
  public IPage<GuessLikeVO> getGuessLikeGoods(UserEntity userEntity, Integer page, Integer limit) {
    IPage<GuessLikeVO> guessLikeVOPage = new Page<>();
    List<GuessLikeVO> records = new ArrayList(limit);
    try {
      DefaultTaobaoClient client = new DefaultTaobaoClient(taobaoConfig.getTbkUrl(), taobaoConfig.getAppKey(), taobaoConfig.getAppSecret());
      TbkDgOptimusMaterialRequest req = new TbkDgOptimusMaterialRequest();
      req.setAdzoneId(Long.parseLong(taobaoConfig.getAdzongId()));
      req.setPageSize(limit.longValue());
      req.setPageNo(page.longValue());
      req.setMaterialId(9647L);
      if (userEntity != null) {
        req.setDeviceValue(Md5.calc(userEntity.getId().toString()));
      } else {
        req.setDeviceValue(Md5.calc(UUID.randomUUID().toString()));
      }
      req.setDeviceEncrypt("MD5");
      TbkDgOptimusMaterialResponse rsp = client.execute(req);
      Map result = (Map) JSON.parse(rsp.getBody());
      Map tbkDgOptimusMaterialResponse = (Map) result.get("tbk_dg_optimus_material_response");
      Map resultList = (Map) tbkDgOptimusMaterialResponse.get("result_list");
      JSONArray data = (JSONArray) resultList.get("map_data");

      for (int i = 0; i < data.size(); i++) {
        //封装VO用于返回前端
        GuessLikeVO guessLikeVO = groupGuessLikeVO(data.getJSONObject(i));
        //封装数据进page
        records.add(guessLikeVO);

        //封装DO用于更新数据库
        TaobaoGoodsEntity taobaoGoodsEntity = groupGuessLikeDO(data.getJSONObject(i));

        //入库（有当前itemId则更新，没有则新增）
        Integer count = bitaoDao.selectCount(taobaoGoodsEntity.getItemId());
        if (count == 1) {
          bitaoDao.updateGoods(taobaoGoodsEntity);
        } else {
          bitaoDao.saveGoods(taobaoGoodsEntity);
        }

        guessLikeVOPage.setCurrent(page.longValue());
        guessLikeVOPage.setSize(limit);
        guessLikeVOPage.setRecords(records);
      }
    } catch (ApiException e) {
      e.printStackTrace();
    }
    return guessLikeVOPage;
  }

  @Override
  public Map<String, Object> addFavorite(UserEntity userEntity, FavoriteForm favoriteForm) {
    Map<String, Object> map = new HashMap<>(2);
    //判断当前ID的宝贝是否存在
    Integer goodsCount = bitaoDao.getCountByGoodsId(favoriteForm.getGoodsId(), favoriteForm.getSource());
    if (goodsCount == 0) {
      throw new SzException("不存在当前商品");
    }
    //判断时候已收藏
    Integer favoriteCount = bitaoDao.getFavoriteCount(userEntity.getId(), favoriteForm.getGoodsId(), favoriteForm.getSource());
    if (favoriteCount == 1) {
      throw new SzException("该宝贝已收藏");
    }
    Integer influenceCount = bitaoDao.addFavorite(userEntity.getId(), favoriteForm.getGoodsId(), favoriteForm.getSource());
    if (influenceCount == 1) {
      map.put("code", CommonConstant.SUCCESS);
      map.put("msg", "收藏成功");
    } else {
      map.put("code", CommonConstant.ERROR);
      map.put("msg", "收藏失败");
    }
    return map;
  }

  @Override
  public Map<String, Object> deleteFavorites(List<Long> ids) {
    Map<String, Object> map = new HashMap<>(2);
    Integer influenceCount = bitaoDao.deleteFavorites(ids);
    if (influenceCount == ids.size()) {
      map.put("code", CommonConstant.SUCCESS);
      map.put("msg", "删除成功");
    } else {
      map.put("code", CommonConstant.ERROR);
      map.put("msg", "删除失败");
    }
    return map;
  }

  @Override
  public IPage<FavoriteVO> getFavorites(Page p, UserEntity userEntity, String sort, String order) {
    IPage<FavoriteVO> favoriteVOIPage = bitaoDao.getFavorites(p, userEntity.getId(), sort, order);
    return favoriteVOIPage;
  }

  @Override
  public Map<String, Object> authorizationTaobao(UserEntity userEntity, AuthorizationTaobaoForm
      authorizationTaobaoForm) {
    Map<String, Object> map = new HashMap<>(2);
    try {
      TaobaoUserEntity taobaoUser = bitaoDao.getTaobaoUser(userEntity.getId());
      //未授权
      if (taobaoUser == null) {
        TaobaoUserEntity taobaoUserEntity = taobaoAuthorization(authorizationTaobaoForm.getCode(), userEntity.getId());
        //判断淘宝返回的taobaoUserId在数据库有没有记录 若有则返回该淘宝账号也被其他用户绑定！（多个闪住账号不能绑定同一个淘宝账号）
        Integer thirdUserIdCount = bitaoDao.getThirdUserIdCount(taobaoUserEntity.getThirdUserId());
        if (thirdUserIdCount == 1) {
          //todo 两个闪住账号不能绑定同一个淘宝账号 后期还需加返回状态码
          throw new SzException("该淘宝账号也被其他用户绑定！请使用其他淘宝账号授权登录。");
        } else {
          bitaoDao.saveTaobaoUser(taobaoUserEntity);
          map.put("code", CommonConstant.SUCCESS);
          map.put("msg", "授权成功！");
        }
        return map;
      }
      Date now = new Date();
      //refreshToken过期 则重新授权并更新数据库
      if (taobaoUser.getRefreshTokenExpireTime().getTime() < now.getTime() && taobaoUser.getAccessTokenExpireTime().getTime() < now.getTime()) {
        TaobaoUserEntity taobaoUserEntity = taobaoAuthorization(authorizationTaobaoForm.getCode(), userEntity.getId());
        //判断淘宝返回的taobaoUserId和数据库当前登录用户的thirUserId不同 若有则返回该闪住账号已绑定淘宝账号！（一个闪住账号不能绑定多个淘宝账号）
        String thirdUserId = bitaoDao.getTaobaoUserId(userEntity.getId());
        if (!thirdUserId.equals(taobaoUserEntity.getThirdUserId())) {
          //todo 两个闪住账号不能绑定同一个淘宝账号 后期还需加返回状态码
          throw new SzException("该闪住账号已绑定淘宝账号！请使用已绑定淘宝账号授权登录。");
        } else {
          bitaoDao.updateTaobaoUser(taobaoUserEntity);
          map.put("code", CommonConstant.SUCCESS);
          map.put("msg", "重新授权成功！");
        }
        return map;
      }
    } catch (NumberFormatException e) {
      e.printStackTrace();
      throw new SzException("授权失败！");
    }
    return map;
  }

  /**
   * 淘宝授权
   */
  private TaobaoUserEntity taobaoAuthorization(String code, Long userId) {
    Map<String, String> props = new HashMap<>(6);
    props.put("grant_type", "authorization_code");
    props.put("code", code);
    props.put("client_id", taobaoConfig.getAppKey());
    props.put("client_secret", taobaoConfig.getAppSecret());
    props.put("redirect_uri", taobaoConfig.getRedirectUri());
    props.put("view", "web");
    String returnStr = HttpUtil.sendPost(taobaoConfig.getTokenUrl(), props);
    Map returnMap = (Map) JSON.parse(returnStr);
    TaobaoUserEntity taobaoUserEntity = new TaobaoUserEntity();
    taobaoUserEntity.setUserId(userId);
    taobaoUserEntity.setThirdUserId(returnMap.get("taobao_user_id").toString());
    taobaoUserEntity.setThirdOpenUid(returnMap.get("taobao_open_uid").toString());
    taobaoUserEntity.setAccessToken(returnMap.get("access_token").toString());
    taobaoUserEntity.setRefreshToken(returnMap.get("refresh_token").toString());
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date accessToken = new Date(Long.parseLong(returnMap.get("expire_time").toString()));
    Date refreshToken = new Date(Long.parseLong(returnMap.get("refresh_token_valid_time").toString()));
    taobaoUserEntity.setAccessTokenExpireTime(accessToken);
    taobaoUserEntity.setRefreshTokenExpireTime(refreshToken);
    return taobaoUserEntity;
  }

  @Override
  public TaobaoOrdersVO getOrders(UserEntity userEntity, Integer page, Integer limit, String state) {
    TaobaoOrdersVO taobaoOrdersVO = new TaobaoOrdersVO();
    Page<TaobaoOrderPageVO> taobaoOrderVOPage = new Page<>(page, limit);
    //判断该用户的accessToken是否有效
    TaobaoUserEntity taobaoUserEntity = bitaoDao.getTaobaoUser(userEntity.getId());
    Date now = new Date();

    //从未授权 返回授权信息
    if (taobaoUserEntity == null) {
      taobaoOrdersVO.setUnAuthorized(true);
      taobaoOrdersVO.setAuthorizeExpired(true);
      taobaoOrdersVO.setAuthorizeUrl(taobaoConfig.getAuthorizationUri().replaceAll("REDIRECTURI", taobaoConfig.getRedirectUri()).replaceAll("STATE", "1212").replaceAll("APPKEY", taobaoConfig.getAppKey()));
      return taobaoOrdersVO;
    } else {
      //抓取订单
      IPage<TaobaoOrderPageVO> taobaoOrderVOIPage = bitaoDao.getOrders(taobaoOrderVOPage, userEntity.getId(), state);
      List<TaobaoOrderPageVO> records = taobaoOrderVOIPage.getRecords();
      for (int i = 0; i < records.size(); i++) {
        TaobaoOrderPageVO taobaoOrderPageVO = records.get(i);
        //计算得出返币量（券后价*佣金率*闪住给用户比率*闪住币汇率）
        BigDecimal szcToCny = walletDao.getProportion(CommonConstant.SZCID, CommonConstant.CNYID).getAmount();
        taobaoOrderPageVO.setCommissionAmount(taobaoOrderPageVO.getCouponedPrice().multiply(taobaoOrderPageVO.getCommissionRate())
            .multiply(new BigDecimal(CommonConstant.SHANZHURATE)).divide(szcToCny));
        //状态码释义
        taobaoOrderPageVO.setStateStr(TaobaoOrderConst.getOrderState(taobaoOrderPageVO.getState()).getState());
      }
      taobaoOrdersVO.setOrders(taobaoOrderVOIPage);

      // accessToken和refreshToken均过期
      if (taobaoUserEntity.getRefreshTokenExpireTime().getTime() < now.getTime() && taobaoUserEntity.getAccessTokenExpireTime().getTime() < now.getTime()) {
        taobaoOrdersVO.setUnAuthorized(false);
        taobaoOrdersVO.setAuthorizeExpired(true);
        taobaoOrdersVO.setAuthorizeUrl(taobaoConfig.getAuthorizationUri().replaceAll("REDIRECTURI", taobaoConfig.getRedirectUri()).replaceAll("STATE", "1212").replaceAll("APPKEY", taobaoConfig.getAppKey()));
      } else {
        //均未过期 授权有效
        taobaoOrdersVO.setUnAuthorized(false);
        taobaoOrdersVO.setAuthorizeExpired(false);
        taobaoOrdersVO.setAuthorizeUrl("");
      }
    }
    return taobaoOrdersVO;
  }

  @Override
  public Map<String, Object> deleteOrder(UserEntity userEntity, Long id) {
    Map<String, Object> map = new HashMap<>(2);
    //判断该订单是不是此用户订单
    Integer count = bitaoDao.getOrderCount(userEntity.getId(), id);
    if (count == 0) {
      throw new SzException("此订单非当前登录用户订单");
    }
    Integer influenceCount = bitaoDao.deleteOrder(userEntity.getId(), id);
    if (influenceCount == 1) {
      map.put("code", CommonConstant.SUCCESS);
      map.put("msg", "订单删除成功");
    } else {
      map.put("code", CommonConstant.ERROR);
      map.put("msg", "订单删除失败");
    }
    return map;
  }
}



