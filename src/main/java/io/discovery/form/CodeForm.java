package io.discovery.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 获取openid表单
 *
 * @author FZX
 * @since 2018-11-15
 */
@ApiModel(value = "获取openid表单")
@Getter
@Setter
public class CodeForm {

  /**
   * 公众号code
   */
  @ApiModelProperty(value = "公众号code", example = "61365454635436316", required = true)
  @NotNull(message = "code:不能为空")
  private String code;

}
