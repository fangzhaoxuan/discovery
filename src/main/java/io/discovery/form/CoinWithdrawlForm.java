package io.discovery.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 提币申请表单
 *
 * @author fzx
 * @since 2018-11-9
 */
@ApiModel(value = "提币申请表单")
public class CoinWithdrawlForm {
  /**
   * 币种ID
   */
  @ApiModelProperty(value = "币种ID", example = "1", required = true)
  @NotNull(message = "coinId:不能为空")
  private Long coinId;

  /**
   * 提币金额
   */
  @ApiModelProperty(value = "提币金额", example = "100.00", required = true)
  @NotNull(message = "amount:不能为空")
  private BigDecimal amount;

  public void setCoinId(Long coinId) {
    this.coinId = coinId;
  }

  public Long getCoinId() {
    return coinId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
