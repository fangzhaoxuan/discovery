package io.discovery.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.discovery.entity.CheckinEntity;
import io.discovery.entity.UserEntity;
import io.discovery.vo.BookingOrderVO;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author fzx
 * @date 2018-10-18
 */
public interface CheckinService extends IService<CheckinEntity> {
  /**
   * 每日签到
   *
   * @param bookingOrderId Long bookingOrderId
   * @param longitude      String longitude
   * @param latitude       String latitude
   * @return check in success or fail
   */
  Map<String, Object> checkIn(Long bookingOrderId, BigDecimal longitude, BigDecimal latitude);

  /**
   * 刷新得币接口
   *
   * @param user UserEntity user
   * @return refresh coin success or fail
   */
  Map<String, Object> checkCoin(UserEntity user);

  /**
   * f获取订单列表
   *
   * @param page  Page page
   * @param id    Long id
   * @param sort  String sort
   * @param order String order
   * @return 订单列表
   */
  IPage<BookingOrderVO> queryList(Page page, Long id, String sort, String order);

  /**
   * 获取签到次数
   *
   * @param bookingOrderId Long bookingOrderId
   * @return 当日签到次数
   */
  int queryCheckIn(Long bookingOrderId);
}

