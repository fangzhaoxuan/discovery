package io.discovery.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author fzx
 * @date 2019-1-4
 */
@Getter
@Setter
public class ShopVO implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 店铺ID（本系统）
   */
  private Long id;

  /**
   * 店铺名称
   */
  private String shopTitle;

  /**
   * 店标图片
   */
  private String shopLogoUrl;

  /**
   * 店铺地址
   */
  private String shopUrl;

  /**
   * 店铺dsr评分
   */
  private BigDecimal shopDsr;

  /**
   * 店铺卖家ID
   */
  private Long sellerId;

  /**
   * 店铺来源taobao/tmall/jd...
   */
  private String source;

  @Override
  public String toString() {
    return "ShopVO{" +
        "id=" + id +
        ", shopTitle='" + shopTitle + '\'' +
        ", shopLogoUrl='" + shopLogoUrl + '\'' +
        ", shopUrl='" + shopUrl + '\'' +
        ", shopDsr=" + shopDsr +
        ", sellerId=" + sellerId +
        ", source='" + source + '\'' +
        '}';
  }
}
