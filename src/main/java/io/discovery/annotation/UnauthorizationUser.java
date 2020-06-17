package io.discovery.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 登录用户信息(不要求登录但需要获取登录用户的接口使用)
 *
 * @author fzx
 * @date 2019-1-3
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface UnauthorizationUser {

}
