package io.discovery.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.discovery.util.InvitationCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户
 *
 * @author fzx
 * @date 2018-10-06
 */
@TableName("user")
public class UserEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 用户ID
   */
  @TableId
  private Long id;
  /**
   * 用户名
   */
  private String name;
  /**
   * 手机号
   */
  private String mobile;
  /**
   * 密码
   */
  @JsonIgnore
  private String password;
  /**
   * 闪住币余额
   */
  private BigDecimal balanceSzc;
  /**
   * 以太币余额
   */
  private BigDecimal balanceEth;
  /**
   * 是否被邀请用户 true表示被邀请的用户 false表示未输入邀请码的用户
   */
  @TableField("is_invited")
  @JsonProperty("isInvited")
  private Boolean invited;
  /**
   * 状态：0：待认证；1：正常；2：禁用
   */
  private Integer state;
  /**
   * openid
   */
  private String openId;
  /**
   * 创建时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date gmtCreate;
  /**
   * 修改时间
   */
  @JsonIgnore
  private Date gmtModified;

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setBalanceSzc(BigDecimal balanceSzc) {
    this.balanceSzc = balanceSzc;
  }

  public void setBalanceEth(BigDecimal balanceEth) {
    this.balanceEth = balanceEth;
  }

  public String getOpenId() {
    return openId;
  }

  public void setInvited(Boolean invited) {
    this.invited = invited;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public void setGmtCreate(Date gmtCreate) {
    this.gmtCreate = gmtCreate;
  }

  public void setGmtModified(Date gmtModified) {
    this.gmtModified = gmtModified;
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getMobile() {
    return mobile;
  }

  public String getPassword() {
    return password;
  }

  public BigDecimal getBalanceSzc() {
    return balanceSzc;
  }

  public BigDecimal getBalanceEth() {
    return balanceEth;
  }

  public Boolean getInvited() {
    return invited;
  }

  public Integer getState() {
    return state;
  }

  public Date getGmtCreate() {
    return gmtCreate;
  }

  public Date getGmtModified() {
    return gmtModified;
  }

  // computed members

  /**
   * @return
   */
  public String getInvitationCode() {
    return InvitationCode.getInstance().gen(this.mobile);
  }
}
