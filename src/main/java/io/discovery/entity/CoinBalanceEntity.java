package io.discovery.entity;


import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户币余额
 *
 * @author fzx
 * @date 2018/10/08
 */
public class CoinBalanceEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 用户ID
   */
  @TableId
  private Long id;
  /**
   * 闪住币余额
   */
  private BigDecimal balanceSzc;
  /**
   * 以太币余额
   */
  private BigDecimal balanceEth;
  /**
   * 闪住币余额对应人民币价值
   */
  private BigDecimal szcValue;
  /**
   * 以太币余额对应人民币价值
   */
  private BigDecimal ethValue;
  /**
   * 总价值（人民币）
   */
  private BigDecimal totalValue;

  public void setId(Long id) {
    this.id = id;
  }

  public void setBalanceSzc(BigDecimal balanceSzc) {
    this.balanceSzc = balanceSzc;
  }

  public void setBalanceEth(BigDecimal balanceEth) {
    this.balanceEth = balanceEth;
  }

  public void setSzcValue(BigDecimal szcValue) {
    this.szcValue = szcValue;
  }

  public void setEthValue(BigDecimal ethValue) {
    this.ethValue = ethValue;
  }

  public void setTotalValue(BigDecimal totalValue) {
    this.totalValue = totalValue;
  }

  public Long getId() {
    return id;
  }

  public BigDecimal getBalanceSzc() {
    return balanceSzc;
  }

  public BigDecimal getBalanceEth() {
    return balanceEth;
  }

  public BigDecimal getSzcValue() {
    return szcValue;
  }

  public BigDecimal getEthValue() {
    return ethValue;
  }

  public BigDecimal getTotalValue() {
    return totalValue;
  }

  @Override
  public String toString() {
    return getClass().getName() + "{ "
        + "balanceSzc: " + balanceSzc + ", "
        + "balanceEth: " + balanceEth + ", "
        + "szcValue: " + szcValue + ", "
        + "ethValue: " + ethValue + ", "
        + "totalValue: " + totalValue + " }";
  }
}
