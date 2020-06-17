package io.discovery.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
/**
 * 轮播图实体类
 *
 * @author fzx
 * @date 2019/1/2
 */
public class RotationChartVO {
  /**
   * 图片url
   */
  private String imageUrl;

  /**
   * 跳转链接
   */
  private String path;

  @Override
  public String toString() {
    return "RotationChartVO{" +
        "imageUrl='" + imageUrl + '\'' +
        ", path='" + path + '\'' +
        '}';
  }
}
