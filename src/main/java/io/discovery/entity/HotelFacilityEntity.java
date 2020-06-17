package io.discovery.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author fzx
 * @date 2018-10-26
 */
@TableName("hotel_facility")
public class HotelFacilityEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @TableId
  @JsonIgnore
  private Long id;
  /**
   * 服务类型
   */
  private String type;
  /**
   * 服务项目
   */
  private String content;

  public void setId(Long id) {
    this.id = id;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Long getId() {
    return id;
  }

  public String getType() {
    return type;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HotelFacilityEntity that = (HotelFacilityEntity) o;
    return id.equals(that.id) &&
        type.equals(that.type) &&
        content.equals(that.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, content);
  }
}
