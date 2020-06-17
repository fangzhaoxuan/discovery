package io.discovery.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 兑币申请表单
 *
 * @author fzx
 * @since 2018-11-9
 */
@ApiModel(value = "兑币申请表单")
public class CoinExchangeForm {
  /**
   * 基础币币种ID
   */
  @ApiModelProperty(value = "基础币币种ID", example = "1", required = true)
  @NotNull(message = "fromCoinId:不能为空")
  private Long fromCoinId;

  /**
   * 提币金额
   */
  @ApiModelProperty(value = "提币金额", example = "100.00", required = true)
  @NotNull(message = "fromAmount:不能为空")
  private BigDecimal fromAmount;

  /**
   * 兑换币币种ID
   */
  @ApiModelProperty(value = "提币兑换币币种ID金额", example = "2", required = true)
  @NotNull(message = "toCoinId:不能为空")
  private Long toCoinId;

  /**
   * 提币金额
   */
  @ApiModelProperty(value = "提币金额", example = "200.00", required = true)
  @NotNull(message = "toAmount:不能为空")
  private BigDecimal toAmount;

  public void setFromCoinId(Long fromCoinId) {
    this.fromCoinId = fromCoinId;
  }

  public void setFromAmount(BigDecimal fromAmount) {
    this.fromAmount = fromAmount;
  }

  public Long getFromCoinId() {
    return fromCoinId;
  }

  public BigDecimal getFromAmount() {
    return fromAmount;
  }

  public Long getToCoinId() {
    return toCoinId;
  }

  public BigDecimal getToAmount() {
    return toAmount;
  }

  public void setToCoinId(Long toCoinId) {
    this.toCoinId = toCoinId;
  }

  public void setToAmount(BigDecimal toAmount) {
    this.toAmount = toAmount;
  }
}
