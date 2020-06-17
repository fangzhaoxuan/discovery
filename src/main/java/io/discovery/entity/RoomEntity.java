package io.discovery.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author fzx
 * @date 2018-12-07
 */
@TableName("hotel_room")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RoomEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @TableId
  private Long id;
  /**
   * 酒店ID
   */
  private String hotelId;
  /**
   * 房间类型
   */
  private String typeStr;
  /**
   * 房间特点
   */
  private String feature;
  /**
   * 房间相册数组
   */
  private String photos;
  /**
   * 单价
   */
  private BigDecimal price;
  /**
   * 库存
   */
  private BigDecimal stock;

  public Long getId() {
    return id;
  }

  public String getHotelId() {
    return hotelId;
  }

  public String getTypeStr() {
    return typeStr;
  }

  public String getFeature() {
    return feature;
  }

  public String getPhotos() {
    return photos;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getStock() {
    return stock;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setHotelId(String hotelId) {
    this.hotelId = hotelId;
  }

  public void setTypeStr(String typeStr) {
    this.typeStr = typeStr;
  }

  public void setFeature(String feature) {
    this.feature = feature;
  }

  public void setPhotos(String photos) {
    this.photos = photos;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public void setStock(BigDecimal stock) {
    this.stock = stock;
  }
}
