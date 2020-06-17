package io.discovery.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.discovery.entity.BookingOrderEntity;
import io.discovery.entity.CheckinEntity;
import io.discovery.entity.HotelEntity;
import io.discovery.vo.BookingOrderVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author fzx
 * @date 2018-10-06
 */
@Service
public interface CheckinDao extends BaseMapper<CheckinEntity> {
  /**
   * 获取酒店信息
   *
   * @param bookingOrderId Long bookingOrderId
   * @return 酒店信息
   */
  HotelEntity queryHotel(@Param("bookingOrderId") Long bookingOrderId);

  /**
   * 获取奖励金额
   *
   * @param bookingOrderId Long bookingOrderId
   * @return 订单信息
   */
  BookingOrderEntity queryRewardAmount(@Param("bookingOrderId") Long bookingOrderId);


  /**
   * 签到记录
   *
   * @param checkIn CheckinEntity checkIn
   * @return
   */
  void checkInRecord(CheckinEntity checkIn);

  /**
   * 查询得币记录
   *
   * @param
   * @return List<CheckinEntity>
   */
  List<CheckinEntity> queryCheckInRecord();

  /**
   * 刷新用户余额
   *
   * @param rewardCoinId Long rewardCoinId
   * @param rewardAmount BigDecimal rewardAmount
   * @param userId       Long userId
   * @return
   */
  void refreshUserCoin(@Param("rewardCoinId") Long rewardCoinId, @Param("rewardAmount") BigDecimal rewardAmount, @Param("userId") Long userId);

  /**
   * 刷新签到记录
   *
   * @param checkIn CheckinEntity checkIn
   * @return
   */
  void refreshChechInRecord(CheckinEntity checkIn);

  /**
   * 分页查询订单
   *
   * @param page  Page page
   * @param id    Long id
   * @param sort  String sort
   * @param order String order
   * @return List<BookingOrderVO>
   */
  IPage<BookingOrderVO> queryList(Page page, @Param("id") Long id, @Param("sort") String sort, @Param("order") String order);


  /**
   * fetch checkin record by bookingOrderId
   *
   * @param bookingOrderId Long bookingOrderId
   * @return int
   */
  int queryCheckIn(@Param("bookingOrderId") Long bookingOrderId);

  /**
   * 修改订单表的签到状态
   *
   * @param bookingOrderId Long bookingOrderId
   * @return int
   */
  void updateOrderCheck(@Param("bookingOrderId") Long bookingOrderId);
}
