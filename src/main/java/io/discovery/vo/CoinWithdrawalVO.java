package io.discovery.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author fzx
 * @date 2018-11-7
 */
@Setter
@Getter
public class CoinWithdrawalVO implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 币种ID
   */
  private Long coinId;
  /**
   * 币种中文名
   */
  private String coinCname;
  /**
   * 提币金额
   */
  private BigDecimal amount;
  /**
   * 手续费
   */
  private BigDecimal chargeFee;
  /**
   * 提币时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private String gmtCreate;

  @Override
  public String toString() {
    return "CoinWithdrawalVO{" +
        "coinId=" + coinId +
        ", coinCname='" + coinCname + '\'' +
        ", amount=" + amount +
        ", chargeFee=" + chargeFee +
        ", gmtCreate='" + gmtCreate + '\'' +
        '}';
  }
}
