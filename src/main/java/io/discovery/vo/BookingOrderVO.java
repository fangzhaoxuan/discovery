package io.discovery.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fzx
 * @date 2018-11-20
 */

@Setter
@Getter
public class BookingOrderVO implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @TableId
  private Long id;

  /**
   * 订单号
   */
  private String bookingNo;

  /**
   * 酒店ID
   */
  private Long hotelId;

  /**
   * 酒店名称快照
   */
  private String snapshotHotelName;

  /**
   * 房间类型快照
   */
  private String snapshotRoomType;

  /**
   * 入住时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date inDate;

  /**
   * 离店时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date outDate;

  /**
   * 是否已签到
   */
  @TableField("is_checked_in")
  @JsonProperty("isCheckedIn")
  private Boolean checkedIn;

  /**
   * 订单状态 0表示待付款 1表示预定中 2表示预订成功 3表示预定失败 4表示订单完成 5表示订单关闭
   */
  private Integer state;

  /**
   * 酒店主图
   */
  private String hotelMainPhoto;

  /**
   * 签到成功且离店后奖励的SZC数量
   */
  private String rewardAmount;

  @Override
  public String toString() {
    return "BookingOrderVO{" +
        "id=" + id +
        ", bookingNo='" + bookingNo + '\'' +
        ", hotelId=" + hotelId +
        ", snapshotHotelName='" + snapshotHotelName + '\'' +
        ", snapshotRoomType='" + snapshotRoomType + '\'' +
        ", inDate=" + inDate +
        ", outDate=" + outDate +
        ", checkedIn=" + checkedIn +
        ", state=" + state +
        ", hotelMainPhoto='" + hotelMainPhoto + '\'' +
        ", rewardAmount='" + rewardAmount + '\'' +
        '}';
  }
}
