package io.discovery.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.discovery.annotation.Login;
import io.discovery.common.utils.R;
import io.discovery.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置接口
 *
 * @author fzx
 * @date 2018/12/14
 */
@RestController
@RequestMapping("/v1")
@Api(tags = "配置接口")
public class ApiSettingController {
  @Autowired
  SettingService settingService;

  @Login
  @GetMapping("settings/wxconfig")
  @ApiOperation("获取用户闪住币以太币余额")
  public R getCoinInfo(String url) {
    return R.data(settingService.wxconfig(url));
  }


}
