package io.discovery.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author fzx
 * @date 2019-1-9
 */
@Getter
@Setter
public class GuessLikeVO implements Serializable {
  private static final long serialVersionUID = 1L;

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
   * 二合一链接
   */
  private String couponUrl;

  @Override
  public String toString() {
    return "GuessLikeVO{" +
        "itemId=" + itemId +
        ", picUrl='" + picUrl + '\'' +
        ", title='" + title + '\'' +
        ", source='" + source + '\'' +
        ", couponUrl='" + couponUrl + '\'' +
        '}';
  }
}
