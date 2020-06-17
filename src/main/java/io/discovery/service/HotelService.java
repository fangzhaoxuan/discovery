package io.discovery.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.discovery.entity.HotelEntity;
import io.discovery.entity.UserEntity;
import io.discovery.form.CommentForm;
import io.discovery.form.CreateOrderForm;
import io.discovery.vo.CommentVO;
import io.discovery.vo.HotelVO;
import io.discovery.vo.RoomVO;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 酒店
 *
 * @author fzx
 * @date 2018/10/16
 */
public interface HotelService extends IService<HotelEntity> {
  /**
   * 获取城市数据
   *
   * @return 城市数据
   */
  List<Map<String, Object>> getCities();

  /**
   * 获取酒店列表
   *
   * @param longitude 经度
   * @param latitude  纬度
   * @param keyword   关键字
   * @param inDate    入住时间
   * @param outDate   离店时间
   * @param mode      附近： 1表示距市中心距离 2表示距您距离
   * @param page      当前页面
   * @param limit     一个页面返回的结果条数
   * @param sort      排序字段
   * @param order     正序还是逆序
   * @return 酒店列表
   */
  IPage<LinkedHashMap<String, Object>> getHotels(BigDecimal longitude, BigDecimal latitude, String keyword, String inDate, String outDate, Integer mode, Page p, String sort, String order);

  /**
   * 获取酒店详情
   *
   * @param id 酒店ID
   * @return 酒店详情
   */
  HotelVO getHotelDetails(Long id);

  /**
   * 特价推荐
   *
   * @param page      当前页面
   * @param limit     一个页面返回的结果条数
   * @param sort      排序字段
   * @param order     正序还是逆序
   * @param longitude 市中心经度
   * @param latitude  市中心纬度
   * @return 特价推荐酒店列表
   */
  IPage<LinkedHashMap<String, Object>> specialRecommend(Page p, String sort, String order, BigDecimal longitude, BigDecimal latitude);

  /**
   * 热门推荐
   *
   * @param adcode 城市编码
   * @return 热门推荐酒店列表
   */
  List<Map<String, Object>> popularRecommend(Integer adcode);

  /**
   * 生成订单
   *
   * @param user            当前登陆用户
   * @param createOrderForm 生成订单信息表单
   * @return 订单信息
   */
  Map<String, Object> createOrder(UserEntity user, CreateOrderForm createOrderForm);

  /**
   * 获取酒店房源
   *
   * @param id 酒店ID
   * @return 酒店房源列表
   */
  List<RoomVO> getRooms(Long id);

  /**
   * 订单评分评价接口
   *
   * @param user        当前登陆用户
   * @param id          订单ID
   * @param commentForm 评分评价表单
   * @return 是否评价成功
   */
  Map<String, Object> orderComment(UserEntity user, Long id, CommentForm commentForm);

  /**
   * 获取酒店评分评价
   *
   * @param page  页面对象
   * @param id    酒店ID
   * @param sort  排序字段
   * @param order 正序逆序
   * @return 评分评价
   */
  IPage<CommentVO> getComments(Page page, Long id, String sort, String order);
}
