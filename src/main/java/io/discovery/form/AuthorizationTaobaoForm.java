package io.discovery.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 淘宝授权表单
 *
 * @author FZX
 * @since 2019-1-10
 */
@ApiModel(value = "淘宝授权表单")
@Getter
@Setter
public class AuthorizationTaobaoForm {

  /**
   * code
   */
  @ApiModelProperty(value = "code", example = "xxx", required = true)
  @NotNull(message = "code:不能为空")
  private String code;

}
