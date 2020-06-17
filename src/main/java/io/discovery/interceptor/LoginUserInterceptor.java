package io.discovery.interceptor;


import io.discovery.annotation.User;
import io.discovery.entity.TokenEntity;
import io.discovery.service.TokenService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取登录用户（不要求登录但需要获取登录用户的接口使用）
 *
 * @author fzx
 * @date 2019-1-3
 */
@Component
public class LoginUserInterceptor extends HandlerInterceptorAdapter {
  @Autowired
  private TokenService tokenService;

  public static final String USER_KEY = "userId";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    User annotation;
    if (handler instanceof HandlerMethod) {
      annotation = ((HandlerMethod) handler).getMethodAnnotation(User.class);
    } else {
      return true;
    }

    if (annotation == null) {
      return true;
    }

    //从header中获取token
    String token = request.getHeader("token");
    //如果header中不存在token，则从参数中获取token
    if (StringUtils.isBlank(token)) {
      token = request.getParameter("token");
    }


    //查询token信息
    TokenEntity tokenEntity = tokenService.queryByToken(token);

    //设置userId到request里，后续根据userId，获取用户信息
    if (tokenEntity != null) {
      request.setAttribute(USER_KEY, tokenEntity.getUserId());
    }
    return true;
  }
}
