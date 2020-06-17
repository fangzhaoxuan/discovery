package io.discovery.controller;


import io.discovery.annotation.Login;
import io.discovery.annotation.LoginUser;
import io.discovery.common.utils.R;
import io.discovery.entity.UserEntity;
import io.discovery.service.MiningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * @author fzx
 * @date 2018-10-12
 */
@RestController
@RequestMapping("/v1")
public class ApiMiningController {
  @Autowired
  private MiningService miningService;

  /**
   * 用户每日签到
   */
  @Login
  @PostMapping("/mining/checkin")
  public R userDailyCheckIn(@LoginUser UserEntity user) {
    Map<String, Object> map = miningService.userDailyCheckIn(user);
    return R.ok(map);
  }

  /**
   * 获取历史邀请信息
   */
  @Login
  @GetMapping("/mining/invitations")
  public R invitations(@LoginUser UserEntity user) {
    return R.data(miningService.invitations(user));
  }

  /**
   * 获取当日签到状态
   */
  @Login
  @GetMapping("/mining/checkin-status")
  public R getDailyCheckInStatus(@LoginUser UserEntity user) {
    return R.data(miningService.getDailyCheckInStatus(user));
  }
}
