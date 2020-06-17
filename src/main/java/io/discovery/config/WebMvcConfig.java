package io.discovery.config;

import io.discovery.interceptor.AuthorizationInterceptor;
import io.discovery.interceptor.LoginUserInterceptor;
import io.discovery.resolver.LoginUserHandlerMethodArgumentResolver;
import io.discovery.resolver.UserHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * MVC配置
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-04-20 22:30
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
  @Autowired
  private AuthorizationInterceptor authorizationInterceptor;
  @Autowired
  private LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver;
  @Autowired
  private LoginUserInterceptor loginUserInterceptor;
  @Autowired
  private UserHandlerMethodArgumentResolver userHandlerMethodArgumentResolver;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authorizationInterceptor).addPathPatterns("/v1/**");
    //在原先的权限逻辑上加上不要求登录但要获取登录用户的Interceptor
    registry.addInterceptor(loginUserInterceptor).addPathPatterns("/v1/**");
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(loginUserHandlerMethodArgumentResolver);
    //在原先的权限逻辑上加上不要求登录但要获取登录用户的Interceptor
    argumentResolvers.add(userHandlerMethodArgumentResolver);
  }
}