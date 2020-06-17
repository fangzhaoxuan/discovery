package io.discovery.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author fzx
 * @date 2019-1-2
 */
@Getter
@Setter
public class FavoriteVO implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 本系统ID
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
   * 商品来源
   */
  private String source;

  /**
   * 跳转链接
   */
  private String couponUrl;

  /**
   * 券后价
   */
  private String couponedPrice;

  @Override
  public String toString() {
    return "FavoriteVO{" +
        "id=" + id +
        ", itemId=" + itemId +
        ", picUrl='" + picUrl + '\'' +
        ", title='" + title + '\'' +
        ", source='" + source + '\'' +
        ", couponUrl='" + couponUrl + '\'' +
        ", couponedPrice='" + couponedPrice + '\'' +
        '}';
  }
}
