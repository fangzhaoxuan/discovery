package io.discovery.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 支付宝支付基础配置类（设置帐户有关信息及返回路径）
 *
 * @author fzx
 * @date 2018/10/31
 */

@Component
@ConfigurationProperties(prefix = "alipay")
public class AlipayConfig {

  /**
   * 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
   */
  @Value("${alipay.appId}")
  private String appId;

  /**
   * 商户私钥，您的PKCS8格式RSA2私钥
   */
  @Value("${alipay.merchantPrivateKey}")
  private String merchantPrivateKey;

  /**
   * 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
   */
  @Value("${alipay.alipayPublicKey}")
  private String alipayPublicKey;

  /**
   * 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
   */
  @Value("${alipay.notifyUrl}")
  private String notifyUrl;

  /**
   * 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
   */
  @Value("${alipay.return_url}")
  private String returnUrl;

  /**
   * 支付宝网关
   */
  @Value("${alipay.gatewayUrl}")
  private String gatewayUrl;

  /**
   * 签名方式
   */
  public static String signType = "RSA2";

  /**
   * 字符编码格式
   */
  public static String charset = "utf-8";


  public String getAppId() {
    return appId;
  }

  public String getMerchantPrivateKey() {
    return merchantPrivateKey;
  }

  public String getAlipayPublicKey() {
    return alipayPublicKey;
  }

  public String getNotifyUrl() {
    return notifyUrl;
  }

  public String getReturnUrl() {
    return returnUrl;
  }

  public String getGatewayUrl() {
    return gatewayUrl;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public void setMerchantPrivateKey(String merchantPrivateKey) {
    this.merchantPrivateKey = merchantPrivateKey;
  }

  public void setAlipayPublicKey(String alipayPublicKey) {
    this.alipayPublicKey = alipayPublicKey;
  }

  public void setNotifyUrl(String notifyUrl) {
    this.notifyUrl = notifyUrl;
  }

  public void setReturnUrl(String returnUrl) {
    this.returnUrl = returnUrl;
  }

  public void setGatewayUrl(String gatewayUrl) {
    this.gatewayUrl = gatewayUrl;
  }
}

