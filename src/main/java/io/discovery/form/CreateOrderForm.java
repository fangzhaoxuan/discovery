package io.discovery.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.discovery.entity.BookingCustomerEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 生成订单表单
 *
 * @author fzx
 * @since 2018-10-24
 */
@Setter
@Getter
@ApiModel(value = "生成订单表单")
public class CreateOrderForm {
  /**
   * 酒店ID
   */
  @ApiModelProperty(value = "酒店ID", example = "1", allowableValues = "range[0, infinity]")
  @NotNull(message = "hotelId:不能为空")
  private Long hotelId;

  /**
   * 房间ID
   */
  @ApiModelProperty(value = "房间ID", example = "10001", allowableValues = "range[0, infinity]")
  @NotNull(message = "roomId:不能为空")
  private Long roomId;

  /**
   * 入住时间
   */
  @ApiModelProperty(value = "入住时间", example = "2018-10-11", required = true)
  @NotNull(message = "inDate:不能为空")
  private String inDate;

  /**
   * 离店时间
   */
  @ApiModelProperty(value = "离店时间", example = "2018-10-11", required = true)
  @NotNull(message = "outDate:不能为空")
  private String outDate;

  /**
   * 预定房间数
   */
  @ApiModelProperty(value = "预定房间数", example = "2", required = true)
  @NotNull(message = "reserveNo:不能为空")
  private Integer reserveNo;

  /**
   * 酒店名称快照
   */
  @ApiModelProperty(value = "酒店名称快照", example = "千山大酒店", required = true)
  @NotNull(message = "snapshotHotelName:不能为空")
  private String snapshotHotelName;

  /**
   * 房间类型快照
   */
  @ApiModelProperty(value = "房间类型快照", example = "大床房", required = true)
  @NotNull(message = "snapshotRoomType:不能为空")
  private String snapshotRoomType;

  /**
   * 房间特点快照
   */
  @ApiModelProperty(value = "房间特点快照", example = "床很大", required = true)
  @NotNull(message = "snapshotRoomFeature:不能为空")
  private String snapshotRoomFeature;

  /**
   * 是否使用闪住币优惠 1表示闪住币抵扣 0表示未抵扣
   */
  @ApiModelProperty(value = "是否使用闪住币优惠 1表示闪住币抵扣 0表示未抵扣", example = "1", required = true)
  @NotNull(message = "discount:不能为空")
  private Boolean discount;

  /**
   * 抵扣币种id
   */
  @ApiModelProperty(value = "抵扣币种id", example = "1", required = true)
  @NotNull(message = "discountCoinId:不能为空")
  private Long discountCoinId;

  /**
   * 折扣数量，单位币最小单位
   */
  @ApiModelProperty(value = "折扣数量，单位币最小单位", example = "100.00", required = true)
  @NotNull(message = "discountAmount:不能为空")
  private BigDecimal discountAmount;

  /**
   * 房间原价格，单位人民币
   */
  @ApiModelProperty(value = "房间原价格，单位人民币", example = "256.00", required = true)
  @NotNull(message = "originalPrice:不能为空")
  private BigDecimal originalPrice;

  /**
   * 入住人
   */
  @ApiModelProperty(value = "入住人", example = "[{\"name\":\"张三\",\"mobile\":\"18611112222\"},{\"name\":\"李四\",\"mobile\":\"18622221111\"}]", required = true)
  @NotNull(message = "customers:不能为空")
  private List<BookingCustomerEntity> customers;

}
