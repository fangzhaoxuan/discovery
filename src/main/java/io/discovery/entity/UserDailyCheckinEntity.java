package io.discovery.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-10-12 09:48:06
 */
@TableName("user_daily_checkin")
public class UserDailyCheckinEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @TableId
  private Long id;
  /**
   * 用户ID
   */
  private Long userId;
  /**
   * 奖励币ID
   */
  private Long rewardCoinId;
  /**
   * 签到奖励数
   */
  private BigDecimal rewardAmount;
  /**
   * 得币状态 0未得币 1已得币
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
   * 设置：用户ID
   */
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   * 获取：用户ID
   */
  public Long getUserId() {
    return userId;
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
   * 设置：签到奖励数
   */
  public void setRewardAmount(BigDecimal rewardAmount) {
    this.rewardAmount = rewardAmount;
  }

  /**
   * 获取：签到奖励数
   */
  public BigDecimal getRewardAmount() {
    return rewardAmount;
  }

  /**
   * 设置：得币状态 0未得币 1已得币
   */
  public void setState(Integer state) {
    this.state = state;
  }

  /**
   * 获取：得币状态 0未得币 1已得币
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
