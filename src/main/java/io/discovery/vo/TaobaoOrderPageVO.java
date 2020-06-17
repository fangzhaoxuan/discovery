package io.discovery.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author fzx
 * @date 2019-1-11
 */
@Getter
@Setter
public class TaobaoOrderPageVO implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 订单表ID
   */
  private Long id;

  /**
   * 淘宝宝贝ID
   */
  private Long itemId;

  /**
   * 商品主图
   */
  private String picUrl;

  /**
   * 商品标题
   */
  private String title;

  /**
   * 折扣价
   */
  private BigDecimal zkFinalPrice;

  /**
   * 券后价
   */
  private BigDecimal couponedPrice;

  /**
   * 佣金率
   */
  @JsonIgnore
  private BigDecimal commissionRate;

  /**
   * 返币量
   */
  private BigDecimal commissionAmount;

  /**
   * 状态
   */
  private Integer state;

  /**
   * 状态码中文释义
   */
  private String stateStr;

  /**
   * 跳转链接
   */
  private String couponUrl;

  /**
   * 订单号
   */
  private String bizOrderId;

  @Override
  public String toString() {
    return "TaobaoOrderPageVO{" +
        "id=" + id +
        ", itemId=" + itemId +
        ", picUrl='" + picUrl + '\'' +
        ", title='" + title + '\'' +
        ", zkFinalPrice=" + zkFinalPrice +
        ", couponedPrice=" + couponedPrice +
        ", commissionRate=" + commissionRate +
        ", commissionAmount=" + commissionAmount +
        ", state=" + state +
        ", stateStr='" + stateStr + '\'' +
        ", couponUrl='" + couponUrl + '\'' +
        ", bizOrderId='" + bizOrderId + '\'' +
        '}';
  }
}
