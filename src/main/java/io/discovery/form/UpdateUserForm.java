package io.discovery.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 修改用户信息表单
 *
 * @author fzx
 * @since 2018-10-11
 */
@ApiModel(value = "修改用户信息表单")
public class UpdateUserForm {
  /**
   * 用户姓名
   */
  @ApiModelProperty(value = "用户姓名", example = "fzx", required = true)
  private String name;

  /**
   * 密码
   */
  @ApiModelProperty(value = "密码", example = "10086250", required = true)
  private String password;

  /**
   * 手机号
   */
  @ApiModelProperty(value = "手机号", example = "10086250", required = true)
  private String mobile;

  /**
   * 验证码
   */
  @ApiModelProperty(value = "验证码", example = "653214", required = true)
  private String verificationCode;

  public void setName(String name) {
    this.name = name;
  }

  public String getVerificationCode() {
    return verificationCode;
  }

  public void setVerificationCode(String verificationCode) {
    this.verificationCode = verificationCode;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  public String getMobile() {
    return mobile;
  }
}
