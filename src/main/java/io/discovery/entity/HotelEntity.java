package io.discovery.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.discovery.util.Geo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-10-05 16:29:12
 */
@TableName("hotel")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HotelEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @TableId
  private Long id;
  /**
   * 酒店编码
   */
  private String uniqueCode;
  /**
   * 酒店名称
   */
  private String name;
  /**
   * 地址
   */
  private String address;
  /**
   * 简介
   */
  private String introduction;
  /**
   * 经度
   */
  private BigDecimal longitude;
  /**
   * 纬度
   */
  private BigDecimal latitude;
  /**
   * 预订须知
   */
  private String orderAttention;
  /**
   * 酒店相册，存成数组形式
   */
  private String photos;
  /**
   * 上架1/下架0
   */
  private Integer state;
  /**
   * 创建者
   */
  private Long creatorId;
  /**
   * 编辑者
   */
  private Long updaterId;
  /**
   * 是否被软删除
   */
  private Integer isDeleted;
  /**
   * 创建时间
   */
  private Date gmtCreate;
  /**
   * 修改时间
   */
  private Date gmtModified;
  /**
   * 距离
   */
  private BigDecimal distance;
  /**
   * 奖励比例
   */
  private BigDecimal rewardRatio;


  public void setRewardRatio(BigDecimal rewardRatio) {
    this.rewardRatio = rewardRatio;
  }

  public BigDecimal getRewardRatio() {
    return rewardRatio;
  }

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
   * 设置：酒店编码
   */
  public void setUniqueCode(String uniqueCode) {
    this.uniqueCode = uniqueCode;
  }

  /**
   * 获取：酒店编码
   */
  public String getUniqueCode() {
    return uniqueCode;
  }

  /**
   * 设置：酒店名称
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * 获取：酒店名称
   */
  public String getName() {
    return name;
  }

  /**
   * 设置：地址
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * 获取：地址
   */
  public String getAddress() {
    return address;
  }

  /**
   * 设置：简介
   */
  public void setIntroduction(String introduction) {
    this.introduction = introduction;
  }

  /**
   * 获取：简介
   */
  public String getIntroduction() {
    return introduction;
  }

  /**
   * 设置：经度
   */
  public void setLongitude(BigDecimal longitude) {
    this.longitude = longitude;
  }

  /**
   * 获取：经度
   */
  public BigDecimal getLongitude() {
    return longitude;
  }

  /**
   * 设置：纬度
   */
  public void setLatitude(BigDecimal latitude) {
    this.latitude = latitude;
  }

  /**
   * 获取：纬度
   */
  public BigDecimal getLatitude() {
    return latitude;
  }

  /**
   * 设置：预订须知
   */
  public void setOrderAttention(String orderAttention) {
    this.orderAttention = orderAttention;
  }

  /**
   * 获取：预订须知
   */
  public String getOrderAttention() {
    return orderAttention;
  }

  /**
   * 设置：酒店相册，存成数组形式
   */
  public void setPhotos(String photos) {
    this.photos = photos;
  }

  /**
   * 获取：酒店相册，存成数组形式
   */
  public String getPhotos() {
    return photos;
  }

  /**
   * 设置：上架1/下架0
   */
  public void setState(Integer state) {
    this.state = state;
  }

  /**
   * 获取：上架1/下架0
   */
  public Integer getState() {
    return state;
  }

  /**
   * 设置：创建者
   */
  public void setCreatorId(Long creatorId) {
    this.creatorId = creatorId;
  }

  /**
   * 获取：创建者
   */
  public Long getCreatorId() {
    return creatorId;
  }

  /**
   * 设置：编辑者
   */
  public void setUpdaterId(Long updaterId) {
    this.updaterId = updaterId;
  }

  /**
   * 获取：编辑者
   */
  public Long getUpdaterId() {
    return updaterId;
  }

  /**
   * 设置：是否被软删除
   */
  public void setIsDeleted(Integer isDeleted) {
    this.isDeleted = isDeleted;
  }

  /**
   * 获取：是否被软删除
   */
  public Integer getIsDeleted() {
    return isDeleted;
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

  public void setDistance(BigDecimal distance) {
    this.distance = distance;
  }

  public BigDecimal getDistance() {
    return distance;
  }

  public BigDecimal distance(BigDecimal longitude, BigDecimal latitude) {
    return new Geo(this.longitude, this.latitude).distance(longitude, latitude);
  }

  public static void main(String[] args) {
    System.out.println(new HotelEntity().distance(new BigDecimal(100.11), new BigDecimal(120.11)));
  }
}
