package io.discovery.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fzx
 * @date 2018-12-18
 */
@Setter
@Getter
public class UserVO implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  private String name;

  /**
   * 手机号
   */
  private String mobile;

  /**
   * 闪住币余额
   */
  private BigDecimal balanceSzc;

  /**
   * 以太币余额
   */
  private BigDecimal balanceEth;

  /**
   * 邀请码
   */
  private String invitationCode;

  /**
   * 微信OPENID
   */
  private String openId;

  /**
   * 是否为被邀请用户
   */
  @JsonProperty("isInvited")
  private Boolean invited;

  /**
   * 是否已设置密码
   */
  @JsonProperty("isPasswordSet")
  private Boolean passwordSet;

  /**
   * 创建时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date gmtCreate;

  @Override
  public String toString() {
    return "UserVO{" +
        "name='" + name + '\'' +
        ", mobile='" + mobile + '\'' +
        ", balanceSzc=" + balanceSzc +
        ", balanceEth=" + balanceEth +
        ", invitationCode='" + invitationCode + '\'' +
        ", openId='" + openId + '\'' +
        ", invited=" + invited +
        ", passwordSet=" + passwordSet +
        ", gmtCreate=" + gmtCreate +
        '}';
  }
}
