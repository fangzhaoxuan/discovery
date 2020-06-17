package io.discovery.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.discovery.annotation.Login;
import io.discovery.annotation.LoginUser;
import io.discovery.common.utils.R;
import io.discovery.entity.UserEntity;
import io.discovery.form.AlipayForm;
import io.discovery.form.CodeForm;
import io.discovery.form.PayBySzcForm;
import io.discovery.form.WechatpayForm;
import io.discovery.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

/**
 * 支付接口
 *
 * @author fzx
 * @date 2018/10/30
 */

@RestController
@RequestMapping("/v1")
@Api(tags = "支付接口")
public class ApiPayController {
  @Autowired
  PayService payService;

  @Login
  @PostMapping("orders/{id}/pay")
  @ApiOperation("闪住币支付")
  public R payBySzc(@LoginUser UserEntity user, @PathVariable Long id, @RequestBody @Validated PayBySzcForm payBySzcForm) {
    Map<String, Object> resultMap = payService.payBySzc(user, id, payBySzcForm.getAmount());
    String code = resultMap.get("code").toString();
    String successCode = "1";
    if (Objects.equals(code, successCode)) {
      return R.error(1, resultMap.get("msg").toString());
    } else {
      return R.ok(resultMap.get("msg").toString());
    }
  }

  @Login
  @PostMapping("alipay")
  @ApiOperation("支付宝支付")
  public void alipay(@LoginUser UserEntity userEntity, HttpServletResponse response, @RequestBody @Validated AlipayForm alipayForm) {
    payService.alipay(userEntity, response, alipayForm.getId());
  }

  @PostMapping("notify")
  @ApiOperation("支付宝支付完成回调------异步返回商家")
  public void notify(HttpServletRequest request, HttpServletResponse response) {
    payService.notify(request, response);
  }

  @Login
  @PostMapping("openid")
  @ApiOperation("入库openid")
  public R setOpenid(@LoginUser UserEntity user, @RequestBody @Validated CodeForm codeForm) {
    return R.data(payService.setOpenid(user.getId(), codeForm.getCode()));
  }

  @Login
  @PostMapping("wechatPay")
  @ApiOperation("微信支付")
  public R wechatPay(HttpServletRequest request, HttpServletResponse response, @LoginUser UserEntity user, @RequestBody @Validated WechatpayForm wechatpayForm) {
    return R.data(payService.wechatPay(request, response, user.getId(), wechatpayForm.getId()));
  }

  @PostMapping("wechatPaynotify")
  @ApiOperation("微信支付完成回调------异步返回商家")
  public void wechatPayNotify(HttpServletRequest request, HttpServletResponse response) {
    payService.wechatPayNotify(request, response);
  }

}
