package io.discovery.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.discovery.entity.BookingCustomerEntity;
import io.discovery.entity.HotelEntity;
import io.discovery.entity.HotelFacilityEntity;
import io.discovery.entity.RoomEntity;
import io.discovery.form.CommentForm;
import io.discovery.form.CreateOrderForm;
import io.discovery.vo.CityVO;
import io.discovery.vo.CommentVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户
 *
 * @author fzx
 * @date 2018/10/16
 */
public interface HotelDao extends BaseMapper<HotelEntity> {
  /**
   * 获取城市数据
   *
   * @return 城市数据
   */
  List<CityVO> getCities();

  /**
   * 获取酒店列表
   *
   * @param p         页面对象
   * @param keyword   关键字
   * @param sort      排序字段
   * @param order     正序还是逆序
   * @param longitude 经度
   * @param latitude  纬度
   * @return 酒店列表
   */
  Page<LinkedHashMap<String, Object>> getHotels(Page p, @Param("keyword") String keyword, @Param("sort") String sort, @Param("order") String order, @Param("longitude") BigDecimal longitude, @Param("latitude") BigDecimal latitude);

  /**
   * 获取酒店详情
   *
   * @param id 酒店ID
   * @return 酒店详情
   */
  HotelEntity getHotelDetails(@Param("id") Long id);

  /**
   * 获取酒店设施
   *
   * @param id 酒店ID
   * @return 当前酒店的设施
   */
  List<HotelFacilityEntity> getFacility(@Param("id") Long id);

  /**
   * 获取酒店设施(不包含type)
   *
   * @param id 酒店ID
   * @return 当前酒店的设施
   */
  List<Map<String, Object>> getFacilityForP(@Param("id") Long id);

  /**
   * 特价推荐
   *
   * @param p     页面对象
   * @param sort  排序字段
   * @param order 正序还是逆序
   * @return 特价推荐酒店列表
   */
  Page<LinkedHashMap<String, Object>> specialRecommend(Page p, @Param("sort") String sort, @Param("order") String order);

  /**
   * 获取城市中心经纬度
   *
   * @param adcode 城市编码
   * @return 城市中心经纬度
   */
  Map<String, Object> getCenterLocation(@Param("adcode") Integer adcode);

  /**
   * 热门推荐
   *
   * @param adcode 城市编码
   * @param dis    最大范围
   * @return 热门推荐列表
   */
  List<Map<String, Object>> popularRecommend(@Param("adcode") Integer adcode, @Param("dis") Integer dis);

  /**
   * 根据roomId查询出房间原价
   *
   * @param roomId 房间ID
   * @return 房间原价
   */
  BigDecimal getOriginalPrice(@Param("roomId") Long roomId);

  /**
   * 根据hotelId查询出奖励币比例
   *
   * @param hotelId 酒店ID
   * @return 奖励币比例
   */
  BigDecimal getRewardRatio(@Param("hotelId") Long hotelId);

  /**
   * 生成表单入库
   *
   * @param createOrderForm 订单信息表单
   * @param bookingNo       订单号
   * @param payPrice        用户实付价格
   * @param userId          登录用户ID
   * @param rewardAmount    奖励币数量
   * @return 数据库受影响条数
   */
  int createOrder(@Param("c") CreateOrderForm createOrderForm, @Param("bookingNo") String bookingNo, @Param("payPrice") BigDecimal payPrice, @Param("userId") Long userId, @Param("rewardAmount") BigDecimal rewardAmount);

  /**
   * 入住人入库
   *
   * @param customers      入住人
   * @param bookingOrderId 订单ID
   * @return ID
   */
  Long insertCustomers(@Param("customers") List<BookingCustomerEntity> customers, @Param("bookingOrderId") Long bookingOrderId);

  /**
   * 根据bookingNo返回ID
   *
   * @param bookingNo 订单编码
   * @return ID
   */
  Long selectIdByBookingNo(@Param("bookingNo") String bookingNo);

  /**
   * 获取酒店房源
   *
   * @param id 酒店ID
   * @return 酒店房源列表
   */
  List<RoomEntity> getRooms(@Param("id") Long id);

  /**
   * 获取酒店ID
   *
   * @param id 订单ID
   * @return 酒店ID
   */
  Long getHotelId(@Param("id") Long id);

  /**
   * 判断该订单有没有被评价
   *
   * @param id 订单ID
   * @return 查询条数
   */
  Integer getOrderCount(@Param("id") Long id);

  /**
   * 根据订单ID获取用户ID
   *
   * @param id 订单ID
   * @return 查询条数
   */
  Long getUserId(@Param("id") Long id);

  /**
   * 酒店评分评价
   *
   * @param userId         登陆用户ID
   * @param bookingOrderId 订单ID
   * @param hotelId        酒店ID
   * @param commentForm    评分评价ID
   * @return 数据库受影响条数
   */
  int orderComment(@Param("userId") Long userId, @Param("bookingOrderId") Long bookingOrderId, @Param("hotelId") Long hotelId, @Param("c") CommentForm commentForm);

  /**
   * 获取酒店评分评价
   *
   * @param page  页面对象
   * @param id    酒店ID
   * @param sort  排序字段
   * @param order 正序逆序
   * @return 评分评价列表
   */
  IPage<CommentVO> getComments(Page page, @Param("id") Long id, @Param("sort") String sort, @Param("order") String order);

  /**
   * 获取酒店评分平均分
   *
   * @param id 酒店ID
   * @return 平均分
   */
  Integer getAvgScore(@Param("id") Long id);

  /**
   * 获取酒店奖励比例和最低房价
   *
   * @param id 酒店ID
   * @return 酒店奖励比例和最低房价
   */
  Map<String, Object> getRatioAndPrice(@Param("id") Long id);

  /**
   * 获取酒店奖励比例
   *
   * @param id 酒店ID
   * @return 酒店奖励比例和最低房价
   */
  BigDecimal getRatio(@Param("id") Long id);
}
