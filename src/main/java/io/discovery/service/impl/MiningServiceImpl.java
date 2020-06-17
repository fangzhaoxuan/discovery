package io.discovery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.discovery.constant.CommonConstant;
import io.discovery.dao.MiningDao;
import io.discovery.entity.UserDailyCheckinEntity;
import io.discovery.entity.UserEntity;
import io.discovery.service.MiningService;
import io.discovery.util.Desensitization;
import io.discovery.vo.InvitationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 挖矿模块
 *
 * @author fzx
 * @date 2018/10/12
 */
@Service
public class MiningServiceImpl extends ServiceImpl<MiningDao, UserDailyCheckinEntity> implements MiningService {
  @Autowired
  private MiningDao miningDao;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Map<String, Object> userDailyCheckIn(UserEntity user) {
    Map<String, Object> map = null;
    try {
      map = new HashMap<>(2);
      //判断今日是否已签到
      int isCheckedin = miningDao.isCheckInToday(user.getId());
      if (isCheckedin == 1) {
        map.put("code", CommonConstant.ERROR);
        map.put("msg", "今日已签到");
        return map;
      }
      Long rewardCoinId = 1L;
      BigDecimal rewardAmount = new BigDecimal(100);
      //直接往用户表加上奖励金额
      int isRewardSuccess = miningDao.dailyCheckInReward(user.getId());
      //记录签到
      int isRecordSuccess = miningDao.userDailyCheckIn(user.getId(), rewardCoinId, rewardAmount);
      if (isRewardSuccess == 1 && isRecordSuccess == 1) {
        map.put("code", CommonConstant.SUCCESS);
        map.put("msg", "用户签到成功");
      } else {
        map.put("code", CommonConstant.ERROR);
        map.put("msg", "用户签到失败");
      }
    } catch (Exception e) {
      map.put("code", CommonConstant.ERROR);
      map.put("msg", "用户签到失败");
      e.printStackTrace();
      return map;
    }
    return map;
  }

  @Override
  public List<InvitationVO> invitations(UserEntity user) {
    List<InvitationVO> result = miningDao.invitations(user.getId());
    try {
      for (int i = 0; i < result.size(); i++) {

        InvitationVO invitationVO = result.get(i);
        //手机号脱敏处理
        invitationVO.setInvitedPhone(Desensitization.desensitization(invitationVO.getInvitedPhone()));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public Map<String, Object> getDailyCheckInStatus(UserEntity user) {
    int checkInCount = miningDao.getDailyCheckInStatus(user.getId());
    Map<String, Object> map = new HashMap<>(1);
    if (checkInCount == 1) {
      map.put("isCheckedIn", true);
    } else {
      map.put("isCheckedIn", false);
    }
    return map;
  }
}
