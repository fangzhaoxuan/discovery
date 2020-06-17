package io.discovery.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author fzx
 * @date 2018-10-30
 */
@Setter
@Getter
public class WxconfigVO implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * appid
   */
  private String appId;
  /**
   * 时间戳
   */
  private String timestamp;
  /**
   * 随机字符串
   */
  private String nonceStr;
  /**
   * 签名
   */
  private String signature;

  @Override
  public String toString() {
    return "WxconfigVO{" +
        "appId=" + appId +
        ", timeStamp=" + timestamp +
        ", nonceStr='" + nonceStr + '\'' +
        ", signature='" + signature + '\'' +
        '}';
  }
}
