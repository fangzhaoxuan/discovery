package io.discovery.constant;

/**
 * 常量
 *
 * @author fzx
 * @date 2018/10/12
 */
public class CommonConstant {
  /**
   * 成功标识
   */
  public static final int SUCCESS = 0;

  /**
   * 失败标识
   */
  public static final int ERROR = 1;

  /**
   * 热门推荐范围
   */
  public static final int MAXDISTANCE = 20000;

  /**
   * 定时任务状态：正常
   */
  public static final int NORMAL = 0;

  /**
   * 定时任务状态：暂停
   */
  public static final int PAUSE = 1;

  /**
   * 闪住币ID
   */
  public static final Long SZCID = 1L;

  /**
   * 以太币ID
   */
  public static final Long ETHID = 2L;

  /**
   * 人民币ID
   */
  public static final Long CNYID = 3L;

  /**
   * 订单支付期限15分钟（毫秒）
   */
  public static final long PAYMENTLIMIT = 900000;

  /**
   * Alipay notify返回成功标志
   */
  public static final String ALIPAYSUCCESS = "true";

  /**
   * Alipay notify返回失败标志
   */
  public static final String ALIPAYFAIL = "false";

  /**
   * 订单支付方式支付宝
   */
  public static final String ALIPAY = "alipay";

  /**
   * 订单支付方式微信
   */
  public static final String WECHATPAY = "wechatpay";

  /**
   * 支付成功（对应数据库is_paysuccess字段 bit类型）
   */
  public static final Boolean PAYSUCCESS = true;

  /**
   * 支付失败（对应数据库is_paysuccess字段 bit类型）
   */
  public static final Boolean PAYFAIL = false;

  /**
   * 淘宝商品闪住佣金
   */
  public static final String SHANZHURATE = "0.5";

  /**
   * 淘宝获取商品详情过期刷新时间（2小时）
   */
  public static final Integer REFLASHLIMIT = 7200000;
}
