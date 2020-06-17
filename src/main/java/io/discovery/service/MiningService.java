package io.discovery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.discovery.entity.UserDailyCheckinEntity;
import io.discovery.entity.UserEntity;
import io.discovery.vo.InvitationVO;

import java.util.List;
import java.util.Map;

/**
 * @author fzx
 * @date 2018-10-12
 */
public interface MiningService extends IService<UserDailyCheckinEntity> {
  /**
   * 用户每日签到
   *
   * @param user 当前登陆用户
   * @return 每日签到是否成功
   */
  Map<String, Object> userDailyCheckIn(UserEntity user);

  /**
   * 获取历史邀请信息
   *
   * @param user 当前登陆用户
   * @return 当前用户历史邀请记录
   */
  List<InvitationVO> invitations(UserEntity user);

  /**
   * 获取当日签到状态
   *
   * @param user 当前登陆用户
   * @return 签到状态
   */
  Map<String, Object> getDailyCheckInStatus(UserEntity user);
}

