package io.discovery.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信支付配置信息
 *
 * @author fzx
 * @date 2018/11/12
 */
@Component
@ConfigurationProperties(prefix = "wechatpay")
public class WechatPayConfig {
  /**
   * 微信号id
   */
  @Value("${wechatpay.appId}")
  private String appId;

  /**
   * 应用对应的凭证
   */
  @Value("${wechatpay.appSecret}")
  private String appSecret;

  /**
   * 商户密钥
   */
  @Value("${wechatpay.appKey}")
  private String appKey;

  /**
   * 商业号
   */
  @Value("${wechatpay.mchId}")
  private String mchId;

  /**
   * 回调地址
   */
  @Value("${wechatpay.notifyUrl}")
  private String notifyUrl;

  /**
   * 商品名称
   */
  private String body = "闪住平台酒店预订";

  /**
   * 交易类型:公众号支付
   */
  private String tradeType = "JSAPI";

  /**
   * 微信统一下单接口请求地址
   */
  @Value("${wechatpay.ufdorUrl}")
  private String ufdorUrl;

  /**
   * 微信支付V2账单查询接口
   */
  @Value("${wechatpay.orderQuery}")
  private String orderQuery;

  /**
   * 根据code获取openid接口
   */
  @Value("${wechatpay.clientAccessTokenUrl}")
  private String clientAccessTokenUrl;

  /**
   * 获取accessToken地址
   */
  private String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?";

  /**
   * 获取jsApiTicket地址
   */
  private String jsApiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?";

  public void setAccessTokenUrl(String accessTokenUrl) {
    this.accessTokenUrl = accessTokenUrl;
  }

  public void setJsApiTicketUrl(String jsApiTicketUrl) {
    this.jsApiTicketUrl = jsApiTicketUrl;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public void setAppSecret(String appSecret) {
    this.appSecret = appSecret;
  }

  public void setAppKey(String appKey) {
    this.appKey = appKey;
  }

  public void setMchId(String mchId) {
    this.mchId = mchId;
  }

  public void setNotifyUrl(String notifyUrl) {
    this.notifyUrl = notifyUrl;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public void setTradeType(String tradeType) {
    this.tradeType = tradeType;
  }

  public void setUfdorUrl(String ufdorUrl) {
    this.ufdorUrl = ufdorUrl;
  }

  public void setOrderQuery(String orderQuery) {
    this.orderQuery = orderQuery;
  }

  public void setClientAccessTokenUrl(String clientAccessTokenUrl) {
    this.clientAccessTokenUrl = clientAccessTokenUrl;
  }

  public String getAppId() {
    return appId;
  }

  public String getAppSecret() {
    return appSecret;
  }

  public String getAppKey() {
    return appKey;
  }

  public String getMchId() {
    return mchId;
  }

  public String getNotifyUrl() {
    return notifyUrl;
  }

  public String getBody() {
    return body;
  }

  public String getTradeType() {
    return tradeType;
  }

  public String getUfdorUrl() {
    return ufdorUrl;
  }

  public String getOrderQuery() {
    return orderQuery;
  }

  public String getClientAccessTokenUrl() {
    return clientAccessTokenUrl;
  }


  public String getAccessTokenUrl() {
    return accessTokenUrl;
  }

  public String getJsApiTicketUrl() {
    return jsApiTicketUrl;
  }
}
