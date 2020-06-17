package io.discovery.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 支付宝支付表单
 *
 * @author FZX
 * @since 2018-11-2
 */
@ApiModel(value = "支付宝支付表单")
@Getter
@Setter
public class AlipayForm {

  /**
   * 订单ID
   */
  @ApiModelProperty(value = "订单ID", example = "13211355435", required = true)
  @NotNull(message = "id:不能为空")
  private Long id;

}
