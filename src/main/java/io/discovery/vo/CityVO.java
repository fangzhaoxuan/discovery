package io.discovery.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author fzx
 * @date 2018-10-26
 */
@Setter
@Getter
public class CityVO implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 城市adcode
   */
  private String adcode;

  /**
   * 城市名称
   */
  private String cityName;

  /**
   * 城市名称中文拼音
   */
  @JsonIgnore
  private String cityNamePinyin;

  /**
   * 市中心经度
   */
  private BigDecimal longitude;

  /**
   * 市中心纬度
   */
  private BigDecimal latitude;

  @Override
  public String toString() {
    return "CityVO{" +
        "adcode='" + adcode + '\'' +
        ", cityName='" + cityName + '\'' +
        ", cityNamePinyin='" + cityNamePinyin + '\'' +
        ", longitude=" + longitude +
        ", latitude=" + latitude +
        '}';
  }
}
