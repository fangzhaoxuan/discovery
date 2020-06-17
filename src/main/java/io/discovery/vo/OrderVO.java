package io.discovery.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fzx
 * @date 2018-10-26
 */
@Setter
@Getter
public class OrderVO implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * id
   */
  private Long id;

  /**
   * 酒店ID
   */
  private Long hotelId;

  /**
   * 订单编号
   */
  private String bookingNo;

  /**
   * 酒店名称
   */
  private String hotelName;

  /**
   * 房间类型
   */
  private String roomType;

  /**
   * 价格
   */
  private BigDecimal price;

  /**
   * 主图
   */
  private String photo;

  /**
   * 奖励币数量
   */
  private BigDecimal reward;

  /**
   * 订单状态
   */
  private Integer state;

  /**
   * 订单状态对应的说明文字
   */
  private String stateStr;

  /**
   * 入住日期
   */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date inDate;

  /**
   * 入住日期
   */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date outDate;

  @Override
  public String toString() {
    return "OrderVO{" +
        "id=" + id +
        ", bookingNo='" + bookingNo + '\'' +
        ", hotelName='" + hotelName + '\'' +
        ", roomType='" + roomType + '\'' +
        ", price=" + price +
        ", photo='" + photo + '\'' +
        ", reward=" + reward +
        ", state=" + state +
        ", stateStr=" + stateStr +
        ", inDate=" + inDate +
        ", outDate=" + outDate +
        '}';
  }
}
