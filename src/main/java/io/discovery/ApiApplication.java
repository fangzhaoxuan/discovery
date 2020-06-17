package io.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
 * 启动类
 *
 * @author keriezhang
 * @date 2016/10/31
 */
@SpringBootApplication
public class ApiApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(ApiApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(ApiApplication.class);
  }
}
