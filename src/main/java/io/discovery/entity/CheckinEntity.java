package io.discovery.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-10-05 15:10:37
 */
@TableName("booking_checkin")
public class CheckinEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @TableId
  private Long id;
  /**
   * 订单ID
   */
  private Long bookingOrderId;
  /**
   * 奖励币ID
   */
  private Long rewardCoinId;
  /**
   * 奖励币数量
   */
  private BigDecimal rewardAmount;
  /**
   * 得币状态 0表示已得币 1表示未得币
   */
  private Integer state;
  /**
   * 创建时间
   */
  private Date gmtCreate;
  /**
   * 修改时间
   */
  private Date gmtModified;

  /**
   * 设置：主键
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * 获取：主键
   */
  public Long getId() {
    return id;
  }

  /**
   * 设置：订单ID
   */
  public void setBookingOrderId(Long bookingOrderId) {
    this.bookingOrderId = bookingOrderId;
  }

  /**
   * 获取：订单ID
   */
  public Long getBookingOrderId() {
    return bookingOrderId;
  }

  /**
   * 设置：奖励币ID
   */
  public void setRewardCoinId(Long rewardCoinId) {
    this.rewardCoinId = rewardCoinId;
  }

  /**
   * 获取：奖励币ID
   */
  public Long getRewardCoinId() {
    return rewardCoinId;
  }

  /**
   * 设置：奖励币数量
   */
  public void setRewardAmount(BigDecimal rewardAmount) {
    this.rewardAmount = rewardAmount;
  }

  /**
   * 获取：奖励币数量
   */
  public BigDecimal getRewardAmount() {
    return rewardAmount;
  }

  /**
   * 设置：得币状态 0表示已得币 1表示未得币
   */
  public void setState(Integer state) {
    this.state = state;
  }

  /**
   * 获取：得币状态 0表示已得币 1表示未得币
   */
  public Integer getState() {
    return state;
  }

  /**
   * 设置：创建时间
   */
  public void setGmtCreate(Date gmtCreate) {
    this.gmtCreate = gmtCreate;
  }

  /**
   * 获取：创建时间
   */
  public Date getGmtCreate() {
    return gmtCreate;
  }

  /**
   * 设置：修改时间
   */
  public void setGmtModified(Date gmtModified) {
    this.gmtModified = gmtModified;
  }

  /**
   * 获取：修改时间
   */
  public Date getGmtModified() {
    return gmtModified;
  }
}
