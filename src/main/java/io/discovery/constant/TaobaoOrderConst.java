package io.discovery.constant;

/**
 * 淘宝订单状态
 *
 * @author fzx
 * @date 2019/1/11
 */
public enum TaobaoOrderConst {

  /**
   * 跟踪中
   */
  TRACKING("跟踪中", 1),

  /**
   * 待放币
   */
  READY_COIN("待放币", 2),

  /**
   * 已到账
   */
  ARRIVE_AT_ACCOUNT("已到账", 3),

  /**
   * 已退货
   */
  ITEM_RETURNED("已退货", 4);


  private String state;
  private int code;

  TaobaoOrderConst(String state, int code) {
    this.state = state;
    this.code = code;
  }

  public String getState() {
    return state;
  }

  public int getCode() {
    return code;
  }

  public static TaobaoOrderConst getOrderState(int code) {
    for (TaobaoOrderConst c : TaobaoOrderConst.values()) {
      if (c.code == code) {
        return c;
      }
    }
    return null;
  }
}
