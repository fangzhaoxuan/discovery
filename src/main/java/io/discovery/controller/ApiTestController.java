package io.discovery.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.discovery.common.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:47
 */
@RestController
@RequestMapping("/v1")
@Api(tags = "测试接口")
public class ApiTestController {

  @GetMapping("test/probe")
  @ApiOperation("忽略Token验证测试")
  public R notToken() {
    return R.ok().put("msg", "test probe");
  }

}
