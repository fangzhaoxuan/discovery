package io.discovery.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.discovery.annotation.Login;
import io.discovery.annotation.LoginUser;
import io.discovery.common.utils.R;
import io.discovery.entity.UserEntity;
import io.discovery.form.LoginForm;
import io.discovery.form.PasswordForm;
import io.discovery.form.VerificationCodeForm;
import io.discovery.service.CaptchaService;
import io.discovery.service.TokenService;
import io.discovery.service.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录接口
 *
 * @author fzx
 * @date 2018/10/06
 */
@RestController
@RequestMapping("/v1")
@Api(tags = "登录接口")
public class ApiLoginController {
  @Autowired
  private UserService userService;
  @Autowired
  private TokenService tokenService;
  @Autowired
  private CaptchaService captchaService;

  @GetMapping("/captcha")
  public void captcha(HttpServletResponse response, String uuid) throws IOException {
    response.setHeader("Cache-Control", "no-store, no-cache");
    response.setContentType("image/jpeg");

    //获取图片验证码
    BufferedImage image = captchaService.getCaptcha(uuid);

    ServletOutputStream out = response.getOutputStream();
    ImageIO.write(image, "jpg", out);
    IOUtils.closeQuietly(out);
  }

  @PostMapping("sms/send")
  @ApiOperation("发送短信")
  public R verificationCode(@RequestBody @Validated VerificationCodeForm verificationCodeForm) {
    Map<String, Object> map = userService.sendVerificationCode(verificationCodeForm);
    return R.ok(map);
  }

  @PostMapping("login")
  @ApiOperation("登录")
  public R login(@RequestBody @Validated LoginForm form) {
    // 用户登录，返回token
    return R.data(userService.login(form));
  }

  @Login
  @PostMapping("setpassword")
  @ApiOperation("首次设置密码")
  public R setPassword(@LoginUser UserEntity user, @RequestBody @Validated PasswordForm passwordForm) {
    int i = userService.setPassword(user, passwordForm.getPassword());
    Map<String, Object> map = new HashMap<>(2);
    if (i > 0) {
      map.put("code", 0);
      map.put("msg", "密码设置成功");
    } else {
      map.put("code", 1);
      map.put("msg", "密码设置失败");
    }
    return R.ok(map);
  }


  @Login
  @PostMapping("logout")
  @ApiOperation("退出")
  public R logout(@ApiIgnore @RequestAttribute("userId") long userId) {
    tokenService.expireToken(userId);
    return R.ok();
  }


}
