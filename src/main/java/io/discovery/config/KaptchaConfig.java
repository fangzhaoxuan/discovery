package io.discovery.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;


/**
 * 生成验证码配置
 *
 * @author Mark sunlightcs@gmail.com
 * @since 2.1.0 2017-04-20
 */
@Configuration
public class KaptchaConfig {

  @Bean
  public DefaultKaptcha producer() {
    Properties properties = new Properties();
    properties.put("kaptcha.border", "no");
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, 80);
    properties.put("kaptcha.textproducer.font.color", "60,160,10");
    properties.put("kaptcha.textproducer.char.space", "10");
    properties.put("kaptcha.textproducer.char.string", "0123456789");
    properties.put("kaptcha.background.clear.from", "white");
    properties.put("kaptcha.background.clear.to", "white");
    properties.put("kaptcha.noise.impl", "io.discovery.util.NoisePoint");
    properties.put("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.WaterRipple");
    Config config = new Config(properties);
    DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
    defaultKaptcha.setConfig(config);
    return defaultKaptcha;
  }
}
