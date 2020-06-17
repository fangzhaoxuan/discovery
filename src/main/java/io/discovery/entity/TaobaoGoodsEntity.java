package io.discovery.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fzx
 * @date 2019-1-2
 */
@Getter
@Setter
@TableName("bitao_goods_taobao")
public class TaobaoGoodsEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @TableId
  private Long id;

  /**
   * 淘宝宝贝ID
   */
  private Long itemId;

  /**
   * 闪住数据库shop表ID
   */
  private Long shopId;

  /**
   * 卖家ID
   */
  private Long sellerId;

  /**
   * 商品主图
   */
  private String picUrl;

  /**
   * 宝贝详情链接
   */
  private String itemUrl;

  /**
   * 商品数组
   */
  private String photos;

  /**
   * 商品标题
   */
  private String title;

  /**
   * 商品来源
   */
  private String source;

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
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date couponStartTime;

  /**
   * 优惠券结束时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date couponEndTime;

  /**
   * 佣金比例，用于计算返币量
   */
  private BigDecimal commissionRate;

  /**
   * 月销量
   */
  private Integer volume;

  /**
   * 宝贝所在地
   */
  private String provcity;

  /**
   * 跳转领取购买链接
   */
  private String couponUrl;

  /**
   * 店铺标题
   */
  private String shopTitle;

  /**
   * 店铺DSR评分
   */
  private Integer shopDsr;

  /**
   * 创建时间
   */
  @JsonIgnore
  private Date gmtCreate;

  /**
   * 修改时间
   */
  @JsonIgnore
  private Date gmtModified;

}
