package io.discovery.annotation;

import java.lang.annotation.*;

/**
 * 登录用户信息(不要求登录但需要获取登录用户的接口使用)
 *
 * @author fzx
 * @date 2019-1-3
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface User {

}
