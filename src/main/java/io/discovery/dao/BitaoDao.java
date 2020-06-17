package io.discovery.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.discovery.entity.RotationChartEntity;
import io.discovery.entity.TaobaoGoodsEntity;
import io.discovery.entity.TaobaoShopEntity;
import io.discovery.entity.TaobaoUserEntity;
import io.discovery.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 币淘模块dao层
 *
 * @author fzx
 * @date 2019/1/2
 */
@Service
public interface BitaoDao extends BaseMapper<RotationChartEntity> {
  /**
   * 获取广告轮播图及其跳转路径
   *
   * @return 轮播图实体类
   */
  List<RotationChartVO> getAdvertisementPhoto();

  /**
   * 查询有无此条数据
   *
   * @param itemId 宝贝ID
   * @return 条数
   */
  Integer selectCount(Long itemId);

  /**
   * 入库商品信息
   *
   * @param t 需要入库的数据
   * @return
   */
  void saveGoods(@Param("t") TaobaoGoodsEntity t);

  /**
   * 修改商品信息
   *
   * @param t 需要修改的数据
   * @return
   */
  void updateGoods(@Param("t") TaobaoGoodsEntity t);

  /**
   * 获取详情是补充商品信息
   *
   * @param t 需要补充的数据
   * @return
   */
  void updateGoodsPart(@Param("t") TaobaoGoodsEntity t);

  /**
   * 根据卖家ID查询店铺数量
   *
   * @param sellerId 卖家ID
   * @return
   */
  Integer getShopCountBySellerId(@Param("sellerId") Long sellerId);

  /**
   * 存入部分店铺信息
   *
   * @param t 店铺部分信息
   * @return
   */
  void savePartShop(@Param("t") TaobaoShopEntity t);

  /**
   * 修改店铺信息
   *
   * @param t 店铺部分信息
   * @return
   */
  void updateShop(@Param("t") TaobaoShopEntity t);

  /**
   * 根据卖家ID查询店铺ID
   *
   * @param sellerId 卖家ID
   * @return
   */
  Long getShopIdBySellerId(@Param("sellerId") Long sellerId);

  /**
   * 入库搜索历史
   *
   * @param userId  用户ID
   * @param keyword 搜索关键字
   * @return
   */
  void saveKeyword(@Param("userId") Long userId, @Param("keyword") String keyword);

  /**
   * 获取搜索历史
   *
   * @param userId 登录用户ID
   * @return 历史搜索数据
   */
  List<String> getSearchHistory(Long userId);

  /**
   * 获取搜索历史(未登录) 去userId为null的记录最多的五条数据
   *
   * @return 历史搜索数据
   */
  List<SearchHistoryVO> getSearchHistoryUnLogin();

  /**
   * 删除搜索历史
   *
   * @param userId 登录用户ID
   * @return 数据库受影响条数
   */
  Integer deleteSearchHistory(Long userId);

  /**
   * 判断数据库对应itemId的宝贝店铺logo是否为空
   *
   * @param itemId 宝贝ID
   * @return 数据库受影响条数
   */
  Integer getShopCount(Long itemId);

  /**
   * 获取店铺信息
   *
   * @param itemId 宝贝ID
   * @return 店铺信息
   */
  ShopVO getShopInfo(Long itemId);

  /**
   * 获取宝贝详情
   *
   * @param itemId 宝贝ID
   * @param source 来源
   * @return 店铺信息
   */
  ItemDetailVO getItemDetail(@Param("itemId") Long itemId, @Param("source") String source);

  /**
   * 获取宝贝更新时间
   *
   * @param itemId 宝贝ID
   * @param source 来源
   * @return 店铺信息
   */
  Date getItemGmtModify(@Param("itemId") Long itemId, @Param("source") String source);

  /**
   * 判断是否收藏
   *
   * @param userId  宝贝ID
   * @param goodsId 本系统商品ID
   * @param source  来源
   * @return 店铺信息
   */
  Integer getFavoriteCount(@Param("userId") Long userId, @Param("goodsId") Long goodsId, @Param("source") String source);

  /**
   * 更新店铺信息
   *
   * @param taobaoShopEntity 店铺信息
   * @return 店铺信息
   */
  void updateShopInfo(@Param("t") TaobaoShopEntity taobaoShopEntity);

  /**
   * 用户足迹
   *
   * @param userId  用户ID
   * @param goodsId 宝贝ID
   * @param source  宝贝来源
   * @return 店铺信息
   */
  void userTrack(@Param("userId") Long userId, @Param("goodsId") Long goodsId, @Param("source") String source);

  /**
   * 判断店铺图文详情是否为空
   *
   * @param itemId 商品ID
   * @return 店铺信息
   */
  Integer getDetailDescriptionCount(@Param("itemId") Long itemId);

  /**
   * 补入店铺详情
   *
   * @param itemId     商品ID
   * @param detailDesc 图文详情
   * @return 店铺信息
   */
  void addDetailDescription(@Param("itemId") Long itemId, @Param("detailDesc") String detailDesc);

  /**
   * 获取宝贝图文详情
   *
   * @param itemId 宝贝ID
   * @param source 宝贝来源
   * @return 返回信息
   */
  String getDescription(@Param("itemId") String itemId, @Param("source") String source);

  /**
   * 判断店铺表是否已存在精准值
   *
   * @param sellerId 卖家ID
   * @return 店铺信息
   */
  Integer getDsr(@Param("sellerId") Long sellerId);

  /**
   * 更新店铺信息
   *
   * @param userId  用户ID
   * @param goodsId 本系统宝贝ID
   * @param source  宝贝来源
   * @return 店铺信息
   */
  Integer addFavorite(@Param("userId") Long userId, @Param("goodsId") Long goodsId, @Param("source") String source);

  /**
   * 根据本系统ID获取宝贝数
   *
   * @param goodsId 本系统宝贝ID
   * @param source  宝贝来源
   * @return 店铺信息
   */
  Integer getCountByGoodsId(@Param("goodsId") Long goodsId, @Param("source") String source);

  /**
   * 删除收藏宝贝
   *
   * @param ids ID集合
   * @return 店铺信息
   */
  Integer deleteFavorites(List<Long> ids);

  /**
   * 获取收藏宝贝
   *
   * @param p      页面对象
   * @param userId 登录用户ID
   * @param sort   排序字段
   * @param orderp 正序逆序
   * @return 淘口令
   */
  IPage<FavoriteVO> getFavorites(Page p, @Param("userId") Long userId, @Param("sort") String sort, @Param("order") String order);

  /**
   * 入库淘宝用户信息
   *
   * @param taobaoUserEntity 淘宝用户
   * @return
   */
  void saveTaobaoUser(@Param("u") TaobaoUserEntity taobaoUserEntity);

  /**
   * 更新淘宝用户信息
   *
   * @param taobaoUserEntity 淘宝用户
   * @return
   */
  void updateTaobaoUser(@Param("u") TaobaoUserEntity taobaoUserEntity);

  /**
   * 根据thirdUserId查询数据库条数
   *
   * @param thirdUserId 淘宝用户
   * @return
   */
  Integer getThirdUserIdCount(@Param("thirdUserId") String thirdUserId);

  /**
   * 根据userId获取淘宝userId
   *
   * @param userId 淘宝用户
   * @return
   */
  String getTaobaoUserId(@Param("userId") Long userId);

  /**
   * 获取淘宝用户信息
   *
   * @param userId 本系统用户ID
   * @return
   */
  TaobaoUserEntity getTaobaoUser(@Param("userId") Long userId);

  /**
   * 获取订单
   *
   * @param page   页面对象
   * @param userId 登录用户ID
   * @param state  订单状态
   * @return 返回信息
   */
  IPage<TaobaoOrderPageVO> getOrders(Page page, @Param("userId") Long userId, @Param("state") String state);

  /**
   * 获取订单数量
   *
   * @param userId 本系统用户ID
   * @param id     主键ID
   * @return
   */
  Integer getOrderCount(@Param("userId") Long userId, @Param("id") Long id);

  /**
   * 删除订单
   *
   * @param userId 本系统用户ID
   * @param id     主键ID
   * @return
   */
  Integer deleteOrder(@Param("userId") Long userId, @Param("id") Long id);
}
