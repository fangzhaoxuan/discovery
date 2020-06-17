package io.discovery.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.discovery.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 支付
 *
 * @author fzx
 * @date 2018/10/30
 */
public interface PayService extends IService<UserEntity> {
  /**
   * 闪住币支付
   *
   * @param user   当前登陆用户
   * @param id     订单ID
   * @param amount 支付金额
   * @return 是否支付成功
   */
  Map<String, Object> payBySzc(UserEntity user, Long id, BigDecimal amount);

  /**
   * 支付宝支付
   *
   * @param userEntity 当前登录用户
   * @param response
   * @param id         订单ID
   */
  void alipay(UserEntity userEntity, HttpServletResponse response, Long id);

  /**
   * 支付完成回调------异步返回商家，也就是notify_url
   *
   * @param response
   * @param request
   */
  void notify(HttpServletRequest request, HttpServletResponse response);

  /**
   * 获取openid并存储
   *
   * @param id   当前登陆用户id
   * @param code 用户进入公众号唯一code
   * @return openid
   */
  String setOpenid(Long id, String code);

  /**
   * 微信支付
   *
   * @param request
   * @param response
   * @param userId   登陆用户ID
   * @param id       订单ID
   * @return 支付请求数据
   */
  Map<Object, Object> wechatPay(HttpServletRequest request, HttpServletResponse response, Long userId, Long id);

  /**
   * 微信支付完成回调------异步返回商家，也就是notify_url
   *
   * @param response
   * @param request
   */
  void wechatPayNotify(HttpServletRequest request, HttpServletResponse response);
}
