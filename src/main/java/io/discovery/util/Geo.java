package io.discovery.util;

import java.math.BigDecimal;

/**
 * This class
 *
 * @author chenwei <acee06.weichen@gmail.com>
 * @date 2018/10/11
 */

public class Geo {
  /**
   * earth radius in km
   */
  private static final double EARTH_RADIUS = 6378.137;

  private BigDecimal longitude;
  private BigDecimal latitude;

  public Geo(BigDecimal longitude, BigDecimal latitude) {
    this.longitude = longitude;
    this.latitude = latitude;
  }


  /**
   * 基于余弦定理求两经纬度距离
   *
   * @param lon2 第二点的精度
   * @param lat2 第二点的纬度
   * @return 返回的距离，单位km
   */
  public BigDecimal distance(BigDecimal lon2, BigDecimal lat2) {
    double radLat1 = rad(latitude.doubleValue());
    double radLat2 = rad(lat2.doubleValue());

    double radLon1 = rad(longitude.doubleValue());
    double radLon2 = rad(lon2.doubleValue());

    if (radLat1 < 0) {
      // south
      radLat1 = Math.PI / 2 + Math.abs(radLat1);
    }
    if (radLat1 > 0) {
      // north
      radLat1 = Math.PI / 2 - Math.abs(radLat1);
    }
    if (radLon1 < 0) {
      // west
      radLon1 = Math.PI * 2 - Math.abs(radLon1);
    }
    if (radLat2 < 0) {
      // south
      radLat2 = Math.PI / 2 + Math.abs(radLat2);
    }
    if (radLat2 > 0) {
      // north
      radLat2 = Math.PI / 2 - Math.abs(radLat2);
    }
    if (radLon2 < 0) {
      // west
      radLon2 = Math.PI * 2 - Math.abs(radLon2);
    }
    double x1 = EARTH_RADIUS * Math.cos(radLon1) * Math.sin(radLat1);
    double y1 = EARTH_RADIUS * Math.sin(radLon1) * Math.sin(radLat1);
    double z1 = EARTH_RADIUS * Math.cos(radLat1);

    double x2 = EARTH_RADIUS * Math.cos(radLon2) * Math.sin(radLat2);
    double y2 = EARTH_RADIUS * Math.sin(radLon2) * Math.sin(radLat2);
    double z2 = EARTH_RADIUS * Math.cos(radLat2);

    double d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));
    //余弦定理求夹角
    double theta = Math.acos((EARTH_RADIUS * EARTH_RADIUS + EARTH_RADIUS * EARTH_RADIUS - d * d) / (2 * EARTH_RADIUS * EARTH_RADIUS));
    double dist = theta * EARTH_RADIUS;
    return new BigDecimal(dist * 1000);
  }

  private static double rad(double d) {
    return d * Math.PI / 180.0;
  }
}
