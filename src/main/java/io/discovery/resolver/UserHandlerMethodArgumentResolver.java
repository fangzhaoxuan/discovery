package io.discovery.resolver;

import io.discovery.annotation.UnauthorizationUser;
import io.discovery.entity.UserEntity;
import io.discovery.interceptor.LoginUserInterceptor;
import io.discovery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 有@User注解的方法参数，注入当前登录用户
 *
 * @author fzx
 * @date 2019-1-3
 */
@Component
public class UserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
  @Autowired
  private UserService userService;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().isAssignableFrom(UserEntity.class) && parameter.hasParameterAnnotation(UnauthorizationUser.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
    //获取用户ID
    Object object = request.getAttribute(LoginUserInterceptor.USER_KEY, RequestAttributes.SCOPE_REQUEST);
    if (object == null) {
      return null;
    }

    //获取用户信息
    UserEntity user = userService.getById((Long) object);
    return user;
  }
}
