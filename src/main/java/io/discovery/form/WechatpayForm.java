package io.discovery.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 微信支付表单
 *
 * @author FZX
 * @since 2018-11-13
 */
@ApiModel(value = "微信支付表单")
@Getter
@Setter
public class WechatpayForm {

  /**
   * 订单ID
   */
  @ApiModelProperty(value = "订单ID", example = "16352165463", required = true)
  @NotNull(message = "id:不能为空")
  private Long id;

}
