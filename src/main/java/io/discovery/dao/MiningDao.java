package io.discovery.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.discovery.entity.UserDailyCheckinEntity;
import io.discovery.vo.InvitationVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author fzx
 * @date 2018-10-12 09:48:06
 */
public interface MiningDao extends BaseMapper<UserDailyCheckinEntity> {
  /**
   * 记录用户每日签到
   *
   * @param userId       当前登陆用户id
   * @param rewardCoinId 奖励币id
   * @param rewardAmount 奖励币数量
   * @return 数据库受影响行数
   */
  int userDailyCheckIn(@Param("userId") Long userId, @Param("rewardCoinId") Long rewardCoinId, @Param("rewardAmount") BigDecimal rewardAmount);

  /**
   * 查询今日是否已签到
   *
   * @param userId 当前登陆用户id
   * @return 今日签到条数
   */
  int isCheckInToday(@Param("userId") Long userId);

  /**
   * 返回历史邀请记录
   *
   * @param userId 当前登陆用户id
   * @return 历史邀请记录
   */
  List<InvitationVO> invitations(@Param("userId") Long userId);

  /**
   * 每日签到直接给用户加上奖励币
   *
   * @param userId 当前登陆用户id
   * @return 数据库受影响行数
   */
  int dailyCheckInReward(@Param("userId") Long userId);

  /**
   * 获取当日签到状态
   *
   * @param userId 当前登陆用户id
   * @return 查询条数
   */
  int getDailyCheckInStatus(@Param("userId") Long userId);
}
