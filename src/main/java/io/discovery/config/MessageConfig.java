package io.discovery.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取配置文件中短信配置信息
 *
 * @author fzx
 * @date 2018/10/08
 */
@Component
@ConfigurationProperties(prefix = "message")
@Setter
@Getter
public class MessageConfig {

  /**
   * 短信发送开关
   */
  @Value("${message.msgSwitch}")
  private Boolean msgSwitch;

  /**
   * 短信平台地址
   */
  @Value("${message.url}")
  private String url;

  /**
   * apikey
   */
  @Value("${message.apikey}")
  private String apikey;
}
