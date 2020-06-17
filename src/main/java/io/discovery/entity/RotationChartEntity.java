package io.discovery.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
/**
 * 轮播图实体类
 *
 * @author fzx
 * @date 2019/1/2
 */
@TableName("bitao_advertisement")
public class RotationChartEntity {
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  private Long id;

  /**
   * 运营活动标题
   */
  private String title;

  /**
   * 图片url
   */
  private String imgUrl;

  /**
   * 跳转链接
   */
  private String path;

  /**
   * 是否已发布
   */
  @TableField("is_published")
  @JsonProperty("isPublished")
  private Boolean published;

  /**
   * 发布时间
   */
  private Date publishTime;

  /**
   * 是否长期
   */
  @TableField("is_longterm")
  @JsonProperty("isLongterm")
  private Boolean longterm;

  /**
   * 过期时间
   */
  private Date expireTime;

  /**
   * 创建时间
   */
  private Date gmtCreate;

  /**
   * 修改时间
   */
  private Date gmtModified;

  /**
   * oms修改者
   */
  private Long updaterId;

  /**
   * 是否被软删除
   */
  @TableField("is_deleted")
  @JsonProperty("isDeleted")
  private Boolean deleted;
}
