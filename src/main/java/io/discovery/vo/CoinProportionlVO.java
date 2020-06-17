package io.discovery.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author fzx
 * @date 2018-11-5
 */
@Setter
@Getter
public class CoinProportionlVO implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 基础币中文名
   */
  private String fromCoinCname;
  /**
   * 兑换币中文名
   */
  private String toCoinCname;
  /**
   * 兑换比例 1个from币兑换多少to币
   */
  private BigDecimal amount;
  /**
   * 创建时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private String gmtCreate;

  @Override
  public String toString() {
    return "CoinProportionlVO{" +
        "fromCoinCname='" + fromCoinCname + '\'' +
        ", toCoinCname='" + toCoinCname + '\'' +
        ", amount=" + amount +
        ", gmtCreate='" + gmtCreate + '\'' +
        '}';
  }
}
