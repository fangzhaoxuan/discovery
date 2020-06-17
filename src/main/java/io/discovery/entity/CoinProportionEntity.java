package io.discovery.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fzx
 * * @date 2018-10-08
 */
@TableName("coin_proportion")
public class CoinProportionEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @TableId
  private Long id;
  /**
   * 币种ID
   */
  private Long fromCoinId;
  /**
   * 币种ID
   */
  private Long toCoinId;
  /**
   * 兑换比例 1个from币兑换多少to币
   */
  private BigDecimal amount;
  /**
   * 创建时间
   */
  private Date gmtCreate;
  /**
   * 修改时间
   */
  private Date gmtModified;

  /**
   * 设置：主键
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * 获取：主键
   */
  public Long getId() {
    return id;
  }

  /**
   * 设置：币种ID
   */
  public void setFromCoinId(Long fromCoinId) {
    this.fromCoinId = fromCoinId;
  }

  /**
   * 获取：币种ID
   */
  public Long getFromCoinId() {
    return fromCoinId;
  }

  /**
   * 设置：币种ID
   */
  public void setToCoinId(Long toCoinId) {
    this.toCoinId = toCoinId;
  }

  /**
   * 获取：币种ID
   */
  public Long getToCoinId() {
    return toCoinId;
  }

  /**
   * 设置：兑换比例 1个from币兑换多少to币
   */
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  /**
   * 获取：兑换比例 1个from币兑换多少to币
   */
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * 设置：创建时间
   */
  public void setGmtCreate(Date gmtCreate) {
    this.gmtCreate = gmtCreate;
  }

  /**
   * 获取：创建时间
   */
  public Date getGmtCreate() {
    return gmtCreate;
  }

  /**
   * 设置：修改时间
   */
  public void setGmtModified(Date gmtModified) {
    this.gmtModified = gmtModified;
  }

  /**
   * 获取：修改时间
   */
  public Date getGmtModified() {
    return gmtModified;
  }
}
