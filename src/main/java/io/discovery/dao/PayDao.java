package io.discovery.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.discovery.entity.BookingOrderEntity;
import io.discovery.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 用户
 *
 * @author fzx
 * @date 2018/10/08
 */
@Service
public interface PayDao extends BaseMapper<UserEntity> {
  /**
   * 查询订单信息
   *
   * @param id 订单ID
   * @return 订单信息
   */
  BookingOrderEntity getOrder(@Param("id") Long id);

  /**
   * 扣除用户余额
   *
   * @param userId 登陆用户ID
   * @param amount 需要扣除闪住币数额
   * @return 数据库受影响条数
   */
  int payBySzc(@Param("userId") Long userId, @Param("amount") BigDecimal amount);

  /**
   * 修改订单状态
   *
   * @param id    订单ID
   * @param state 状态码
   * @return
   */
  void updateOrderState(@Param("id") Long id, @Param("state") Integer state);

  /**
   * 支付回调用订单号修改订单状态
   *
   * @param bookingNo  订单ID
   * @param state      状态码
   * @param payType    支付方式
   * @param paySuccess 是否支付成功
   * @return
   */
  void updateOrderStateByNo(@Param("bookingNo") String bookingNo, @Param("state") Integer state, @Param("payType") String payType, @Param("paySuccess") Boolean paySuccess);

  /**
   * 根据返回的订单号查询支付金额用于支付金额验证
   *
   * @param bookingNo 订单ID
   * @return 用户实付价格
   */
  BigDecimal getPayPrice(@Param("bookingNo") String bookingNo);

  /**
   * 根据返回的订单号查询订单支付状态
   *
   * @param bookingNo 订单ID
   * @return 付款状态
   */
  Boolean getPayStatus(@Param("bookingNo") String bookingNo);
}
