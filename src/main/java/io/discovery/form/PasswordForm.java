package io.discovery.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 设置密码表单
 *
 * @author FZX
 * @since 2018-11-22
 */
public class PasswordForm {

  /**
   * 密码
   */
  @ApiModelProperty(value = "密码", example = "10086250", required = true)
  @NotNull(message = "password:不能为空")
  private String password;

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }
}
