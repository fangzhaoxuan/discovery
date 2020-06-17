package io.discovery.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 淘宝配置信息
 *
 * @author fzx
 * @date 2019/1/3
 */
@Component
@Setter
@Getter
@ConfigurationProperties(prefix = "taobao")
public class TaobaoConfig {
  /**
   * adzongId
   */
  @Value("${taobao.adzongId}")
  private String adzongId;

  /**
   * appKey
   */
  @Value("${taobao.appKey}")
  private String appKey;

  /**
   * appSecret
   */
  @Value("${taobao.appSecret}")
  private String appSecret;

  /**
   * 淘宝客请求通用地址
   */
  private String tbkUrl = "http://gw.api.taobao.com/router/rest";

  /**
   * 获取店铺详情地址
   */
  private String shopUrl = "https://acs.m.taobao.com/h5/mtop.taobao.detail.getdetail/6.0/?data=";

  /**
   * 使用code获取token地址及刷新token地址
   */
  private String tokenUrl = "https://oauth.taobao.com/token";

  /**
   * 使用code获取token地址回调地址
   */
  private String redirectUri = "https://www.51shanzhu.com/bitao/orders";

  /**
   * 授权地址
   */
  private String authorizationUri = "https://oauth.taobao.com/authorize?response_type=code&view=wap&redirect_uri=REDIRECTURI&state=STATE&client_id=APPKEY";
}
