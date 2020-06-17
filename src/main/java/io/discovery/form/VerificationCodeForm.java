package io.discovery.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 短信发送表单
 *
 * @author FZX
 * @since 2018-9-27
 */
@ApiModel(value = "短信发送表单")
public class VerificationCodeForm {

  /**
   * 登录手机号，即登录账号
   */
  @ApiModelProperty(value = "登录手机号，即登录账号", example = "18611112222", required = true)
  @NotNull(message = "mobile:不能为空")
  private String mobile;

  /**
   * 之前获取验证码传到后台的UUID
   */
  @ApiModelProperty(value = "之前获取验证码传到后台的UUID", example = "112233445566", required = true)
  @NotNull(message = "uuid:不能为空")
  private String uuid;

  /**
   * 图形验证码
   */
  @ApiModelProperty(value = "图形验证码", example = "62354", required = true)
  @NotNull(message = "captcha:不能为空")
  private String captcha;

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public void setCaptcha(String captcha) {
    this.captcha = captcha;
  }

  public String getMobile() {
    return mobile;
  }

  public String getUuid() {
    return uuid;
  }

  public String getCaptcha() {
    return captcha;
  }
}
