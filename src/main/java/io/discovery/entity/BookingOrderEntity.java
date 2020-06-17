package io.discovery.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * @author fzx
 * @date 2018-10-22
 */
@TableName("booking_order")
public class BookingOrderEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @TableId
  private Long id;
  /**
   * 订单号
   */
  private String bookingNo;
  /**
   * 酒店ID
   */
  private Long hotelId;
  /**
   * 房间ID
   */
  @JsonIgnore
  private Long roomId;
  /**
   * 用户ID
   */
  @JsonIgnore
  private Long userId;
  /**
   * 入住时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date inDate;
  /**
   * 离店时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date outDate;
  /**
   * 预定房间数
   */
  private Integer reserveNo;
  /**
   * 酒店名称快照
   */
  private String snapshotHotelName;
  /**
   * 房间类型快照
   */
  private String snapshotRoomType;
  /**
   * 房间特点快照
   */
  private String snapshotRoomFeature;
  /**
   * 是否使用闪住币优惠 1表示闪住币抵扣 0表示未抵扣
   */
  @TableField("is_discount")
  @JsonProperty("isDiscount")
  private Boolean discount;
  /**
   * 折扣数量，单位币最小单位
   */
  private BigDecimal discountAmount;
  /**
   * 抵扣币种id
   */
  @JsonIgnore
  private Long discountCoinId;
  /**
   * 抵扣币种中文名
   */
  private String discountCoinCname;
  /**
   * 房间原价格，单位人民币
   */
  private BigDecimal originalPrice;
  /**
   * 用户实付价格，单位人民币
   */
  private BigDecimal payPrice;
  /**
   * 支付方式，'alipay' | 'wechatpay'
   */
  private String payType;
  /**
   * 是否支付成功 0支付成功 1支付失败
   */
  @TableField("is_paysuccess")
  @JsonProperty("isPaysuccess")
  private Boolean paysuccess;
  /**
   * 订单状态 0表示待付款 1表示预定中 2表示预订成功 3表示预定失败 4表示订单完成 5表示订单关闭
   */
  private Integer state;
  /**
   * 奖励币id
   */
  @JsonIgnore
  private Long rewardCoinId;
  /**
   * 奖励币中文名
   */
  private String rewardCoinCname;
  /**
   * 奖励币数量
   */
  private BigDecimal rewardAmount;
  /**
   * 是否已签到
   */
  @TableField("is_checked_in")
  @JsonProperty("isCheckIn")
  private Boolean checkedIn;
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
  /**
   * 入住者集合
   */
  private Set<BookingCustomerEntity> customers;

  public String getDiscountCoinCname() {
    return discountCoinCname;
  }

  public String getRewardCoinCname() {
    return rewardCoinCname;
  }

  public void setDiscountCoinCname(String discountCoinCname) {
    this.discountCoinCname = discountCoinCname;
  }

  public void setRewardCoinCname(String rewardCoinCname) {
    this.rewardCoinCname = rewardCoinCname;
  }

  public void setCustomers(Set<BookingCustomerEntity> customers) {
    this.customers = customers;
  }

  public Set<BookingCustomerEntity> getCustomers() {
    return customers;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setBookingNo(String bookingNo) {
    this.bookingNo = bookingNo;
  }

  public void setHotelId(Long hotelId) {
    this.hotelId = hotelId;
  }

  public void setRoomId(Long roomId) {
    this.roomId = roomId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public void setInDate(Date inDate) {
    this.inDate = inDate;
  }

  public void setOutDate(Date outDate) {
    this.outDate = outDate;
  }

  public void setReserveNo(Integer reserveNo) {
    this.reserveNo = reserveNo;
  }

  public void setSnapshotHotelName(String snapshotHotelName) {
    this.snapshotHotelName = snapshotHotelName;
  }

  public void setSnapshotRoomType(String snapshotRoomType) {
    this.snapshotRoomType = snapshotRoomType;
  }

  public void setSnapshotRoomFeature(String snapshotRoomFeature) {
    this.snapshotRoomFeature = snapshotRoomFeature;
  }

  public void setDiscount(Boolean discount) {
    this.discount = discount;
  }

  public void setDiscountAmount(BigDecimal discountAmount) {
    this.discountAmount = discountAmount;
  }

  public void setDiscountCoinId(Long discountCoinId) {
    this.discountCoinId = discountCoinId;
  }

  public void setOriginalPrice(BigDecimal originalPrice) {
    this.originalPrice = originalPrice;
  }

  public void setPayPrice(BigDecimal payPrice) {
    this.payPrice = payPrice;
  }

  public void setPayType(String payType) {
    this.payType = payType;
  }

  public void setPaysuccess(Boolean paysuccess) {
    this.paysuccess = paysuccess;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public void setRewardCoinId(Long rewardCoinId) {
    this.rewardCoinId = rewardCoinId;
  }

  public void setRewardAmount(BigDecimal rewardAmount) {
    this.rewardAmount = rewardAmount;
  }

  public void setCheckedIn(Boolean checkedIn) {
    this.checkedIn = checkedIn;
  }

  public Long getId() {
    return id;
  }

  public String getBookingNo() {
    return bookingNo;
  }

  public Long getHotelId() {
    return hotelId;
  }

  public Long getRoomId() {
    return roomId;
  }

  public Long getUserId() {
    return userId;
  }

  public Date getInDate() {
    return inDate;
  }

  public Date getOutDate() {
    return outDate;
  }

  public Integer getReserveNo() {
    return reserveNo;
  }

  public String getSnapshotHotelName() {
    return snapshotHotelName;
  }

  public String getSnapshotRoomType() {
    return snapshotRoomType;
  }

  public String getSnapshotRoomFeature() {
    return snapshotRoomFeature;
  }

  public Boolean getDiscount() {
    return discount;
  }

  public BigDecimal getDiscountAmount() {
    return discountAmount;
  }

  public Long getDiscountCoinId() {
    return discountCoinId;
  }

  public BigDecimal getOriginalPrice() {
    return originalPrice;
  }

  public BigDecimal getPayPrice() {
    return payPrice;
  }

  public String getPayType() {
    return payType;
  }

  public Boolean getPaysuccess() {
    return paysuccess;
  }

  public Integer getState() {
    return state;
  }

  public Long getRewardCoinId() {
    return rewardCoinId;
  }

  public BigDecimal getRewardAmount() {
    return rewardAmount;
  }

  public Date getGmtCreate() {
    return gmtCreate;
  }

  public Date getGmtModified() {
    return gmtModified;
  }

  public void setGmtCreate(Date gmtCreate) {
    this.gmtCreate = gmtCreate;
  }

  public void setGmtModified(Date gmtModified) {
    this.gmtModified = gmtModified;
  }

  public Boolean getCheckedIn() {
    return checkedIn;
  }
}
