package io.discovery.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.discovery.entity.RotationChartEntity;
import io.discovery.entity.UserEntity;
import io.discovery.form.AuthorizationTaobaoForm;
import io.discovery.form.FavoriteForm;
import io.discovery.form.TpwdForm;
import io.discovery.vo.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 币淘模块Service层
 *
 * @author fzx
 * @date 2019/1/2
 */
public interface BitaoService extends IService<RotationChartEntity> {

  /**
   * 获取广告轮播图及跳转链接
   *
   * @return 广告轮播图及跳转链接
   */
  Map<String, Object> getAdvertisementPhoto();

  /**
   * 获取热销产品
   *
   * @param page  当前页数
   * @param limit 每页显示多少条数据
   * @return 热销产品
   */
  IPage<HotGoodsVO> getHotGoods(Integer page, Integer limit);

  /**
   * 搜索淘宝产品
   *
   * @param userEntity 登录用户
   * @param page       当前页数
   * @param limit      每页显示条数
   * @param keyword    搜索关键字
   * @param sort       排序字段
   * @param order      正序逆序
   * @param request    HttpServletRequest
   * @return 淘宝产品集合
   */
  IPage<SearchGoodsVO> searchGoods(UserEntity userEntity, Integer page, Integer limit, String keyword, String sort, String order, HttpServletRequest request);

  /**
   * 获取搜索历史
   *
   * @param userEntity 登录用户
   * @return 历史搜索数据
   */
  Map<String, List<String>> getSearchHistory(UserEntity userEntity);

  /**
   * 删除搜索历史
   *
   * @param userEntity 登录用户
   * @return 返回信息
   */
  Map<String, Object> deleteSearchHistory(UserEntity userEntity);

  /**
   * 获取宝贝详情
   *
   * @param userEntity 登录用户
   * @param itemId     宝贝ID
   * @param source     来源
   * @return 返回信息
   */
  ItemDetailVO getItemDetail(UserEntity userEntity, Long itemId, String source);

  /**
   * 获取宝贝图文详情
   *
   * @param itemId 宝贝ID
   * @param source 宝贝来源
   * @return 返回信息
   */
  Object[] getDescription(String itemId, String source);

  /**
   * 获取店铺信息
   *
   * @param itemId 宝贝ID
   * @return 返回信息
   */
  ShopVO getShopInfo(Long itemId);

  /**
   * 获取淘口令
   *
   * @param tpwdForm 淘口令表单
   * @return 淘口令
   */
  Map<String, Object> createTpwd(TpwdForm tpwdForm);

  /**
   * 猜你喜欢
   *
   * @param userEntity 登录用户
   * @param page       当前页数
   * @param limit      每页显示条数
   * @return 淘口令
   */
  IPage<GuessLikeVO> getGuessLikeGoods(UserEntity userEntity, Integer page, Integer limit);

  /**
   * 收藏宝贝
   *
   * @param userEntity   当前登录用户
   * @param favoriteForm 收藏宝贝表单
   * @return 淘口令
   */
  Map<String, Object> addFavorite(UserEntity userEntity, FavoriteForm favoriteForm);

  /**
   * 删除收藏宝贝
   *
   * @param ids 需要删除的id数组
   * @return 淘口令
   */
  Map<String, Object> deleteFavorites(List<Long> ids);

  /**
   * 获取收藏宝贝
   *
   * @param p          页面对象
   * @param userEntity 登录用户
   * @param sort       排序字段
   * @param orderp     正序逆序
   * @return 淘口令
   */
  IPage<FavoriteVO> getFavorites(Page p, UserEntity userEntity, String sort, String order);

  /**
   * 淘宝授权
   *
   * @param userEntity              登录用户
   * @param authorizationTaobaoForm 授权表单
   * @return 返回信息
   */
  Map<String, Object> authorizationTaobao(UserEntity userEntity, AuthorizationTaobaoForm authorizationTaobaoForm);

  /**
   * 获取订单
   *
   * @param userEntity 登录用户
   * @param page       当前页数
   * @param limit      每页显示条数
   * @param state      订单状态
   * @return 返回信息
   */
  TaobaoOrdersVO getOrders(UserEntity userEntity, Integer page, Integer limit, String state);

  /**
   * 删除订单
   *
   * @param userEntity 登录用户
   * @param id         订单ID
   * @return 返回信息
   */
  Map<String, Object> deleteOrder(UserEntity userEntity, Long id);
}
