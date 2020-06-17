package io.discovery.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @author fzx
 * @date 2019-1-4
 */
@Getter
@Setter
public class ItemDetailVO implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 宝贝ID（本系统）
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
   * 商品详情地址
   */
  private String itemUrl;

  /**
   * 商品相册（接收）
   */
  @JsonIgnore
  private String pictures;

  /**
   * 商品相册（VO）
   */
  private Object[] photos;

  /**
   * 商品标题
   */
  private String title;

  /**
   * 折扣价
   */
  private BigDecimal zkFinalPrice;

  /**
   * 是否有券
   */
  private Boolean hasCoupon;

  /**
   * 券面额
   */
  private BigDecimal couponDiscount;

  /**
   * 券后价
   */
  private BigDecimal couponedPrice;

  /**
   * 优惠券开始时间
   */
  private String couponStartTime;

  /**
   * 优惠券结束时间
   */
  private String couponEndTime;

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
   * 月销量
   */
  private Integer volume;

  /**
   * 宝贝所在地
   */
  private String provcity;

  /**
   * 来源
   */
  private String source;

  /**
   * 卖家ID
   */
  private Long sellerId;

  /**
   * 是否已收藏
   */
  @JsonProperty("isFavorite")
  private Boolean favorite;

  /**
   * 跳转链接
   */
  private String couponUrl;

  @Override
  public String toString() {
    return "ItemDetailVO{" +
        "id=" + id +
        ", itemId=" + itemId +
        ", picUrl='" + picUrl + '\'' +
        ", itemUrl='" + itemUrl + '\'' +
        ", pictures='" + pictures + '\'' +
        ", photos=" + Arrays.toString(photos) +
        ", title='" + title + '\'' +
        ", zkFinalPrice=" + zkFinalPrice +
        ", hasCoupon=" + hasCoupon +
        ", couponDiscount=" + couponDiscount +
        ", couponedPrice=" + couponedPrice +
        ", couponStartTime='" + couponStartTime + '\'' +
        ", couponEndTime='" + couponEndTime + '\'' +
        ", commissionRate=" + commissionRate +
        ", commissionAmount=" + commissionAmount +
        ", volume=" + volume +
        ", provcity='" + provcity + '\'' +
        ", source='" + source + '\'' +
        ", sellerId=" + sellerId +
        ", favorite=" + favorite +
        ", couponUrl='" + couponUrl + '\'' +
        '}';
  }
}
