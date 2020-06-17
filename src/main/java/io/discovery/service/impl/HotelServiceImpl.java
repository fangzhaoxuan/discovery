package io.discovery.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.discovery.common.exception.SzException;
import io.discovery.constant.CommonConstant;
import io.discovery.dao.HotelDao;
import io.discovery.dao.WalletDao;
import io.discovery.entity.HotelEntity;
import io.discovery.entity.HotelFacilityEntity;
import io.discovery.entity.RoomEntity;
import io.discovery.entity.UserEntity;
import io.discovery.form.CommentForm;
import io.discovery.form.CreateOrderForm;
import io.discovery.service.HotelService;
import io.discovery.util.BookingOrderNoCreator;
import io.discovery.util.CalculateDays;
import io.discovery.util.Pinyin;
import io.discovery.vo.CityVO;
import io.discovery.vo.CommentVO;
import io.discovery.vo.HotelVO;
import io.discovery.vo.RoomVO;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author fzx
 * @date 2018/10/16
 */
@Service
public class HotelServiceImpl extends ServiceImpl<HotelDao, HotelEntity> implements HotelService {
  @Autowired
  private HotelDao hotelDao;
  @Autowired
  private WalletDao walletDao;
  @Autowired
  private RedisTemplate redisTemplate;

  @Override
  public List<Map<String, Object>> getCities() {
    List<CityVO> cityVOList = hotelDao.getCities();
    for (int i = 0; i < cityVOList.size(); i++) {
      CityVO cityVO = cityVOList.get(i);
      cityVO.setCityNamePinyin(Pinyin.toPinyin(cityVO.getCityName()));
    }
    return Pinyin.groupByFirstChar(cityVOList);
  }

  @Override
  public IPage<LinkedHashMap<String, Object>> getHotels(BigDecimal longitude, BigDecimal latitude, String keyword, String inDate, String outDate, Integer mode, Page p, String sort, String order) {
    if (StringUtils.isNotBlank(keyword)) {
      keyword = "%" + keyword.trim() + "%";
    }
    IPage<LinkedHashMap<String, Object>> resultPage = hotelDao.getHotels(p, keyword, sort, order, longitude, latitude);
    List<LinkedHashMap<String, Object>> records = resultPage.getRecords();
    BigDecimal cnyToSzc = new BigDecimal(1).divide(walletDao.getProportion(CommonConstant.SZCID, CommonConstant.CNYID).getAmount(), 2, RoundingMode.HALF_UP);
    for (int i = 0; i < records.size(); i++) {
      Map<String, Object> m = records.get(i);
      //获取酒店评价平均分
      Long id = Long.parseLong(m.get("id").toString());
      Integer score = hotelDao.getAvgScore(id);
      if (score == null) {
        m.put("score", 0);
      } else {
        m.put("score", score);
      }
      //获取酒店设施数组
      List<Map<String, Object>> facility = hotelDao.getFacilityForP(id);
      List<Map<String, Object>> facility2 = new ArrayList<>();
      for (int j = 0; j < facility.size(); j++) {
        if (j <= 1) {
          facility2.add(facility.get(j));
        }
      }
      m.put("facilities", facility2);
      //奖励最低闪住币数量。根据酒店的reward_ratio乘以最低房间乘以汇率
      Map<String, Object> resultMap = hotelDao.getRatioAndPrice(id);
      m.put("rewardAmount", cnyToSzc.multiply(new BigDecimal(resultMap.get("rewardRatio").toString())).multiply(new BigDecimal(resultMap.get("price").toString())));
      String photos = m.get("photos").toString();
      JSONArray jsonArray = JSONArray.fromObject(photos);
      m.remove("photos");
      m.put("photo", jsonArray.get(0));
      m.put("inDate", inDate);
      m.put("outDate", outDate);
      m.put("mode", mode);
    }
    return resultPage;
  }

  @Override
  public HotelVO getHotelDetails(Long id) {
    HotelEntity hotelEntity = hotelDao.getHotelDetails(id);
    HotelVO hotelVO = new HotelVO();
    hotelVO.setId(hotelEntity.getId());
    hotelVO.setName(hotelEntity.getName());
    hotelVO.setAddress(hotelEntity.getAddress());
    hotelVO.setIntroduction(hotelEntity.getIntroduction());
    hotelVO.setOrderAttention(hotelEntity.getOrderAttention());
    hotelVO.setState(hotelEntity.getState());
    hotelVO.setRewardRatio(hotelEntity.getRewardRatio());
    hotelVO.setLongitude(hotelEntity.getLongitude());
    hotelVO.setLatitude(hotelEntity.getLatitude());
    List<HotelFacilityEntity> facility = hotelDao.getFacility(id);
    List<HotelFacilityEntity> facility2 = new ArrayList<>();
    for (int j = 0; j < facility.size(); j++) {
      if (j <= 1) {
        facility2.add(facility.get(j));
      }
    }
    hotelVO.setFacilities(facility2);
    Integer score = hotelDao.getAvgScore(id);
    if (score == null) {
      hotelVO.setScore(0);
    } else {
      hotelVO.setScore(score);
    }
    String photos = hotelEntity.getPhotos();
    JSONArray jsonArray = JSONArray.fromObject(photos);
    Object[] photoArray = jsonArray.toArray();
    hotelVO.setPhotos(photoArray);

    return hotelVO;
  }

  @Override
  public IPage<LinkedHashMap<String, Object>> specialRecommend(Page p, String sort, String order, BigDecimal longitude, BigDecimal latitude) {
    IPage<LinkedHashMap<String, Object>> resultPage = hotelDao.specialRecommend(p, sort, order);
    List<LinkedHashMap<String, Object>> records = resultPage.getRecords();
    BigDecimal cnyToSzc = new BigDecimal(1).divide(walletDao.getProportion(CommonConstant.SZCID, CommonConstant.CNYID).getAmount(), 2, RoundingMode.HALF_UP);
    for (int i = 0; i < records.size(); i++) {
      Map<String, Object> m = records.get(i);
      HotelEntity hotel = new HotelEntity();
      hotel.setLongitude(new BigDecimal(m.get("longitude").toString()));
      hotel.setLatitude(new BigDecimal(m.get("latitude").toString()));
      BigDecimal distance = hotel.distance(longitude, latitude).setScale(2, RoundingMode.HALF_UP);
      m.put("distance", distance);
      m.remove("longitude");
      m.remove("latitude");
      String photos = m.get("photos").toString();
      JSONArray jsonArray = JSONArray.fromObject(photos);
      m.remove("photos");
      m.put("photo", jsonArray.get(0));
      //获取酒店评价平均分
      Long id = Long.parseLong(m.get("id").toString());
      Integer score = hotelDao.getAvgScore(id);
      if (score == null) {
        m.put("score", 0);
      } else {
        m.put("score", score);
      }
      //获取酒店设施数组
      List<Map<String, Object>> facility = hotelDao.getFacilityForP(id);
      List<Map<String, Object>> facility2 = new ArrayList<>();
      for (int j = 0; j < facility.size(); j++) {
        if (j <= 1) {
          facility2.add(facility.get(j));
        }
      }
      m.put("facilities", facility2);
      //奖励最低闪住币数量。根据酒店的reward_ratio乘以最低房间乘以汇率
      Map<String, Object> resultMap = hotelDao.getRatioAndPrice(id);
      m.put("rewardAmount", cnyToSzc.multiply(new BigDecimal(resultMap.get("rewardRatio").toString())).multiply(new BigDecimal(resultMap.get("price").toString())));
      m.put("mode", 1);
    }
    return resultPage;
  }

  @Override
  public List<Map<String, Object>> popularRecommend(Integer adcode) {
    //从数据库中直接查出指定距离范围的酒店
    List<Map<String, Object>> resultList = hotelDao.popularRecommend(adcode, CommonConstant.MAXDISTANCE);
    for (int i = 0; i < resultList.size(); i++) {
      //获取酒店评价平均分
      Long id = Long.parseLong(resultList.get(i).get("id").toString());
      Integer score = hotelDao.getAvgScore(id);
      if (score == null) {
        resultList.get(i).put("score", 0);
      } else {
        resultList.get(i).put("score", score);
      }
      String photos = resultList.get(i).get("photos").toString();
      JSONArray jsonArray = JSONArray.fromObject(photos);
      resultList.get(i).remove("photos");
      resultList.get(i).put("photo", jsonArray.get(0));
      resultList.get(i).remove("distance");
    }
    return resultList;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Map<String, Object> createOrder(UserEntity user, CreateOrderForm createOrderForm) {
    Map<String, Object> map = new LinkedHashMap<>(3);
    //计算住几晚
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date inDate = null;
    Date outDate = null;
    try {
      inDate = simpleDateFormat.parse(createOrderForm.getInDate());
      outDate = simpleDateFormat.parse(createOrderForm.getOutDate());
    } catch (ParseException e) {
      e.printStackTrace();
    }
    BigDecimal days = new BigDecimal(CalculateDays.daysBetween(inDate, outDate));
    //生成订单号
    String bookingNo = BookingOrderNoCreator.createBookingOrderNo();
    //根据roomId查询出房间原价
    BigDecimal originalPrice = hotelDao.getOriginalPrice(createOrderForm.getRoomId());
    //判断是否使用闪住币抵扣
    BigDecimal payPrice;
    //查询得出当日闪住币兑换人民币比率
    BigDecimal szcToCny = walletDao.getProportion(CommonConstant.SZCID, CommonConstant.CNYID).getAmount();
    if (createOrderForm.getDiscount()) {
      //判断该用户闪住币余额
      if (user.getBalanceSzc().compareTo(createOrderForm.getDiscountAmount()) < 0) {
        throw new SzException("山竹币余额不足");
      }
      //计算用户实付价格：原价格*预定房间数-闪住币抵扣金额*当日闪住币兑换人民币比例
      payPrice = originalPrice.multiply(new BigDecimal(createOrderForm.getReserveNo())).multiply(days).
          subtract(createOrderForm.getDiscountAmount().multiply(szcToCny));
    } else {
      payPrice = originalPrice.multiply(new BigDecimal(createOrderForm.getReserveNo())).multiply(days);
    }
    //计算奖励币数量 奖励币reward_amount = 房间单价 * 预订房间数 *入住天数* hotel.reward_ratio * (人民币对闪住币汇率)
    BigDecimal rewardRatio = hotelDao.getRewardRatio(createOrderForm.getHotelId());
    BigDecimal rewardAmount = originalPrice.multiply(days).multiply(new BigDecimal(createOrderForm.getReserveNo())).multiply(rewardRatio).divide(szcToCny);
    //将订单信息插入数据库（订单状态为0：待付款 签到状态为0：未签到）
    int affectedCount = hotelDao.createOrder(createOrderForm, bookingNo, payPrice, user.getId(), rewardAmount);
    if (affectedCount == 1) {
      //根据bookingNo查询订单ID返回前端
      Long id = hotelDao.selectIdByBookingNo(bookingNo);
      //插入入住人信息
      hotelDao.insertCustomers(createOrderForm.getCustomers(), id);
      map.put("id", id);
      map.put("bookingNo", bookingNo);
      map.put("payPrice", payPrice);
    }
    return map;
  }

  @Override
  public List<RoomVO> getRooms(Long id) {
    List<RoomEntity> roomEntities = hotelDao.getRooms(id);
    BigDecimal cnyToSzc = new BigDecimal(1).divide(walletDao.getProportion(CommonConstant.SZCID, CommonConstant.CNYID).getAmount(), 2, RoundingMode.HALF_UP);
    BigDecimal rewardRatio = hotelDao.getRatio(id);
    List<RoomVO> roomVOList = new ArrayList<>();
    for (int i = 0; i < roomEntities.size(); i++) {
      RoomEntity roomEntity = roomEntities.get(i);
      RoomVO roomVO = new RoomVO();
      roomVO.setId(roomEntity.getId());
      roomVO.setHotelId(roomEntity.getHotelId());
      roomVO.setTypeStr(roomEntity.getTypeStr());
      roomVO.setFeature(roomEntity.getFeature());
      roomVO.setPrice(roomEntity.getPrice());
      roomVO.setStock(roomEntity.getStock());
      String photos = roomEntity.getPhotos();
      JSONArray jsonArray = JSONArray.fromObject(photos);
      Object[] photoArray = jsonArray.toArray();
      roomVO.setPhotos(photoArray);
      //奖励最低闪住币数量。根据酒店的reward_ratio乘以最低房间乘以汇率
      roomVO.setRewardAmount(cnyToSzc.multiply(rewardRatio).multiply(roomVO.getPrice()));
      roomVOList.add(roomVO);
    }
    return roomVOList;
  }

  @Override
  public Map<String, Object> orderComment(UserEntity user, Long id, CommentForm commentForm) {
    //先判断该订单有没有被评价
    int commentCount = hotelDao.getOrderCount(id);
    if (commentCount == 1) {
      throw new SzException("该订单已被评价");
    }
    //判断是否为本人的订单
    Long userId = hotelDao.getUserId(id);
    if (!user.getId().equals(userId)) {
      throw new SzException("非本人订单");
    }
    Map<String, Object> returnMap = new HashMap<>(2);
    //根据订单ID获取酒店ID
    Long hotelId = hotelDao.getHotelId(id);
    int referenceCount = hotelDao.orderComment(user.getId(), id, hotelId, commentForm);
    if (referenceCount == 1) {
      returnMap.put("code", 0);
      returnMap.put("msg", "评价成功");
    } else {
      returnMap.put("code", 1);
      returnMap.put("msg", "评价失败");
    }
    return returnMap;
  }

  @Override
  public IPage<CommentVO> getComments(Page page, Long id, String sort, String order) {
    return hotelDao.getComments(page, id, sort, order);
  }

}
