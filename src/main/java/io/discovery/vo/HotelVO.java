package io.discovery.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.discovery.entity.HotelFacilityEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author fzx
 * @date 2018-10-26
 */
@Setter
@Getter
public class HotelVO implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @TableId
  private Long id;
  /**
   * 酒店名称
   */
  private String name;
  /**
   * 酒店经度
   */
  private BigDecimal longitude;
  /**
   * 酒店纬度
   */
  private BigDecimal latitude;
  /**
   * 地址
   */
  private String address;
  /**
   * 简介
   */
  private String introduction;
  /**
   * 预订须知
   */
  private String orderAttention;
  /**
   * 酒店相册，存成数组形式
   */
  private Object[] photos;
  /**
   * 上架1/下架0
   */
  private Integer state;
  /**
   * 奖励币兑换比例
   */
  private BigDecimal rewardRatio;
  /**
   * 酒店设施
   */
  private List<HotelFacilityEntity> facilities;
  /**
   * 评分
   */
  private Integer score;

  @Override
  public String toString() {
    return "HotelVO{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", longitude='" + longitude + '\'' +
        ", latitude='" + latitude + '\'' +
        ", address='" + address + '\'' +
        ", introduction='" + introduction + '\'' +
        ", orderAttention='" + orderAttention + '\'' +
        ", photos=" + Arrays.toString(photos) +
        ", state=" + state +
        ", rewardRatio=" + rewardRatio +
        ", facilities=" + facilities +
        ", score=" + score +
        '}';
  }
}
