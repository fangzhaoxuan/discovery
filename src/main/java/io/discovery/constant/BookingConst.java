package io.discovery.constant;

/**
 * Created on 2018/9/20
 *
 * @author chenwei <acee06.weichen@gmail.com>
 */
public enum BookingConst {
  /**
   * 待付款
   */
  STATE_WAITING_FOR_PAY("待付款", 0),
  /**
   * 预定中（支付成功）
   */
  STATE_BOOKING("预定中", 1),
  /**
   * 预订成功
   */
  STATE_BOOKING_OK("预订成功", 2),
  /**
   * 预定失败
   */
  STATE_BOOKING_FAIL("预定失败", 3),
  /**
   * 订单完成
   */
  STATE_ORDER_FINISH("订单完成", 4),
  /**
   * 订单关闭
   */
  STATE_ORDER_CLOSE("订单关闭", 5),
  /**
   * 支付完成
   */
  STATE_ORDER_PAY_FINISH("支付完成", 6),
  /**
   * 支付失败
   */
  STATE_ORDER_PAY_FAIL("支付失败", 7);

  private String state;
  private int code;

  BookingConst(String state, int code) {
    this.state = state;
    this.code = code;
  }

  public String getState() {
    return state;
  }

  public int getCode() {
    return code;
  }

  public static BookingConst getBookingState(int code) {
    for (BookingConst c : BookingConst.values()) {
      if (c.code == code) {
        return c;
      }
    }
    return null;
  }
}
