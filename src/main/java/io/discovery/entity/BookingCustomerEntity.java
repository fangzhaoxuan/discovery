package io.discovery.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author fzx
 * @date 2018-10-23
 */
@TableName("booking_customer")
public class BookingCustomerEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @TableId
  @JsonIgnore
  private Long id;
  /**
   * 订单号
   */
  @JsonIgnore
  private Long bookingOrderId;
  /**
   * 入住者姓名
   */
  private String name;
  /**
   * 入住者手机号
   */
  private String mobile;
  /**
   * 创建时间
   */
  @JsonIgnore
  private Date gmtCreate;
  /**
   * 修改时间
   */
  @JsonIgnore
  private Date gmtModified;


  public void setId(Long id) {
    this.id = id;
  }

  public void setBookingOrderId(Long bookingOrderId) {
    this.bookingOrderId = bookingOrderId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public void setGmtCreate(Date gmtCreate) {
    this.gmtCreate = gmtCreate;
  }

  public void setGmtModified(Date gmtModified) {
    this.gmtModified = gmtModified;
  }

  public Long getId() {
    return id;
  }

  public Long getBookingOrderId() {
    return bookingOrderId;
  }

  public String getName() {
    return name;
  }

  public String getMobile() {
    return mobile;
  }

  public Date getGmtCreate() {
    return gmtCreate;
  }

  public Date getGmtModified() {
    return gmtModified;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BookingCustomerEntity that = (BookingCustomerEntity) o;
    return id.equals(that.id) &&
        bookingOrderId.equals(that.bookingOrderId) &&
        name.equals(that.name) &&
        mobile.equals(that.mobile) &&
        gmtCreate.equals(that.gmtCreate) &&
        gmtModified.equals(that.gmtModified);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, bookingOrderId, name, mobile, gmtCreate, gmtModified);
  }

}
