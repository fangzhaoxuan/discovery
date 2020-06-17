package io.discovery.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 分页插件配置类
 * <p>
 * Annotation @MapperScan - 这个注解，作用相当于下面
 * 的@Bean MapperScannerConfigurer，2者配置1份即可
 *
 * @author fzx
 * @date 2018/10/10
 */
@Configuration
@MapperScan("io.discovery.dao")
public class MybatisPlusConfig {

  /**
   * mybatis-plus分页插件<br>
   * 文档：http://mp.baomidou.com<br>
   */
  @Bean
  public PaginationInterceptor paginationInterceptor() {
    return new PaginationInterceptor();
  }

  /**
   * 性能分析拦截器，不建议生产使用
   */
  @Bean
  @Profile({"dev", "test"})
  public PerformanceInterceptor performanceInterceptor() {
    return new PerformanceInterceptor();
  }
}
