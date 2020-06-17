package io.discovery.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 登录表单
 *
 * @author fzx
 * @since 2018-10-06
 */
@ApiModel(value = "行程签到表单")
public class CheckinForm {
  /**
   * 经度
   */
  @ApiModelProperty(value = "经度", example = "100.121651", required = true)
  @NotNull(message = "longitude:不能为空")
  private BigDecimal longitude;

  /**
   * 纬度
   */
  @ApiModelProperty(value = "纬度", example = "30.2221", required = true)
  @NotNull(message = "latitude:不能为空")
  private BigDecimal latitude;

  public void setLongitude(BigDecimal longitude) {
    this.longitude = longitude;
  }

  public void setLatitude(BigDecimal latitude) {
    this.latitude = latitude;
  }

  public BigDecimal getLongitude() {
    return longitude;
  }

  public BigDecimal getLatitude() {
    return latitude;
  }
}
