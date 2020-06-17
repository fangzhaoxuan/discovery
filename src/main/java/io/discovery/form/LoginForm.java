package io.discovery.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 登录表单
 *
 * @author fzx
 * @since 2018-10-06
 */
@ApiModel(value = "登录表单")
public class LoginForm {
  /**
   * 登录模式 1：手机验证码登录 2：密码登录
   */
  @ApiModelProperty(value = "登录模式 1：手机验证码登录 2：密码登录", example = "1", required = true)
  @NotNull(message = "mode:不能为空")
  private Integer mode;

  /**
   * 登录手机号，即登录账号
   */
  @ApiModelProperty(value = "登录手机号，即登录账号", example = "18611112222", required = true)
  @NotNull(message = "mobile:不能为空")
  private String mobile;

  /**
   * 密码
   */
  private String password;

  /**
   * 之前获取验证码传到后台的UUID
   */
  @ApiModelProperty(value = "之前获取验证码传到后台的UUID", example = "111122223333", required = true)
  @NotNull(message = "uuid:不能为空")
  private String uuid;

  /**
   * 图形验证码
   */
  @ApiModelProperty(value = "图形验证码", example = "65231", required = true)
  @NotNull(message = "captcha:不能为空")
  private String captcha;

  /**
   * 短信验证码
   */
  private String verificationCode;

  /**
   * 邀请码
   */
  private String invitationCode;

  public void setMode(int mode) {
    this.mode = mode;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public void setCaptcha(String captcha) {
    this.captcha = captcha;
  }

  public void setVerificationCode(String verificationCode) {
    this.verificationCode = verificationCode;
  }

  public void setInvitationCode(String invitationCode) {
    this.invitationCode = invitationCode;
  }

  public int getMode() {
    return mode;
  }

  public String getMobile() {
    return mobile;
  }

  public String getPassword() {
    return password;
  }

  public String getUuid() {
    return uuid;
  }

  public String getCaptcha() {
    return captcha;
  }

  public String getVerificationCode() {
    return verificationCode;
  }

  public String getInvitationCode() {
    return invitationCode;
  }
}
