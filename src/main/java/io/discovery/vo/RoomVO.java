package io.discovery.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author fzx
 * @date 2018-10-30
 */
@Setter
@Getter
public class RoomVO implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @TableId
  private Long id;
  /**
   * 酒店ID
   */
  private String hotelId;
  /**
   * 房间类型
   */
  private String typeStr;
  /**
   * 房间特点
   */
  private String feature;
  /**
   * 房间相册，存成数组形式
   */
  private Object[] photos;
  /**
   * 单价
   */
  private BigDecimal price;
  /**
   * 库存
   */
  private BigDecimal stock;
  /**
   * 奖励闪住币数量
   */
  private BigDecimal rewardAmount;

  @Override
  public String toString() {
    return "RoomVO{" +
        "id=" + id +
        ", hotelId='" + hotelId + '\'' +
        ", typeStr='" + typeStr + '\'' +
        ", feature='" + feature + '\'' +
        ", photos='" + photos + '\'' +
        ", price=" + price +
        ", stock=" + stock +
        ", rewardAmount=" + rewardAmount +
        '}';
  }
}
