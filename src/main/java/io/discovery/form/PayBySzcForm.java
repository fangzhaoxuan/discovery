package io.discovery.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 闪住币支付表单
 *
 * @author FZX
 * @since 2018-10-31
 */
@ApiModel(value = "闪住币支付表单")
@Getter
@Setter
public class PayBySzcForm {

  /**
   * 支付金额
   */
  @ApiModelProperty(value = "支付金额", example = "251.00", required = true)
  @NotNull(message = "amount:不能为空")
  private BigDecimal amount;

}
