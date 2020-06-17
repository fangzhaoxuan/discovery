package io.discovery.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.discovery.common.exception.SzException;
import io.discovery.common.utils.BCrypt;
import io.discovery.config.MessageConfig;
import io.discovery.constant.BookingConst;
import io.discovery.constant.CommonConstant;
import io.discovery.constant.LoginTypeConstant;
import io.discovery.dao.UserDao;
import io.discovery.entity.BookingOrderEntity;
import io.discovery.entity.TokenEntity;
import io.discovery.entity.UserEntity;
import io.discovery.form.LoginForm;
import io.discovery.form.UpdateUserForm;
import io.discovery.form.VerificationCodeForm;
import io.discovery.service.CaptchaService;
import io.discovery.service.TokenService;
import io.discovery.service.UserService;
import io.discovery.util.InvitationCode;
import io.discovery.util.SendMsgUtil;
import io.discovery.vo.LoginTokenVO;
import io.discovery.vo.OrderVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author fzx
 * @date 2018/10/6
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
  @Autowired
  private TokenService tokenService;
  @Autowired
  private CaptchaService captchaService;
  @Autowired
  private UserDao userDao;
  @Autowired
  private StringRedisTemplate redisTemplate;
  @Autowired
  private MessageConfig messageConfig;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public LoginTokenVO login(LoginForm form) {
    LoginTokenVO tokenVO = null;
    //登录模式 VERIFICATION_CODE_LOGIN：手机验证码登录 PASSWORD_LOGIN：密码登录
    if (form.getMode() == LoginTypeConstant.VERIFICATION_CODE_LOGIN) {
      tokenVO = codeLogin(form);
    } else if (form.getMode() == LoginTypeConstant.PASSWORD_LOGIN) {
      boolean captchaFlag = captchaService.validate(form.getUuid(), form.getCaptcha());
      if (!captchaFlag) {
        throw new SzException("图形验证码错误");
      }

      tokenVO = passwordLogin(form);
    }
    return tokenVO;
  }

  /**
   * 短信验证码登录
   */
  public LoginTokenVO codeLogin(LoginForm form) {
    Map<String, Object> map = new HashMap<>(2);
    // 短信验证码验证  从Redis中拿verificationCode和前端传过来的对比
    String verificationCode = form.getVerificationCode();
    String verificationCodeInRedis = redisTemplate.opsForValue().get(form.getMobile());
    if (!Objects.equals(verificationCode, verificationCodeInRedis)) {
      throw new SzException("手机验证码错误或已过期");
    }
    redisTemplate.delete(form.getMobile());

    LoginTokenVO tokenVO = new LoginTokenVO();
    // 查数据库有无此用户 若有则直接登录 若无则创建此用户并登录
    UserEntity user = userDao.selectUserByPhone(form.getMobile());
    String invitedMobile = InvitationCode.getInstance().reverse(form.getInvitationCode());
    Long inviteId = null;
    Long loginId = null;
    if (invitedMobile != null) {
      inviteId = userDao.selectId(invitedMobile);
    }
    if (user == null) {
      UserEntity userEntity = new UserEntity();
      userEntity.setMobile(form.getMobile());
      userEntity.setBalanceEth(new BigDecimal(0));
      userEntity.setPassword("");
      userEntity.setName("");
      userEntity.setState(1);
      // 根据反编译后的手机号获取的用户ID判断此邀请码是否有效，若为null则为非邀请用户，若不为null则为邀请用户,
      // 奖励邀请者和被邀请者闪住币,新登录用户均奖励闪住币
      if (inviteId != null) {
        userEntity.setInvited(true);
        userEntity.setBalanceSzc(new BigDecimal(LoginTypeConstant.NEW_USER_REWARD + LoginTypeConstant.INVITE_REWARD));
        //判断该邀请者是否成功邀请超过30位用户
        int inviteCount = userDao.queryInviteCount(inviteId);
        if (inviteCount < LoginTypeConstant.INVITE_SUCCESS_COUNT) {
          //邀请者奖励100闪住币
          userDao.rewardInvitation(inviteId);
        }
      } else {
        userEntity.setInvited(false);
        userEntity.setBalanceSzc(new BigDecimal(LoginTypeConstant.NEW_USER_REWARD));
      }
      userDao.insertUser(userEntity);
      //新用户是被邀请用户则在插入新用户后再获取新用户ID（否则取不到新用户的ID）并和邀请者的ID一起存入邀请记录表
      loginId = userDao.selectId(form.getMobile());
      // 若为被邀请用户 则插入邀请记录表
      if (inviteId != null) {
        userDao.invitationRecord(loginId, inviteId);
      }
      tokenVO.setIsFresh(true);
    } else {
      tokenVO.setIsFresh(false);
      loginId = user.getId();
    }


    // 获取登录token
    TokenEntity tokenEntity = tokenService.createToken(loginId);

    // VO fills
    tokenVO.setToken(tokenEntity.getToken());
    tokenVO.setExpire(tokenEntity.getExpireTime().getTime() - System.currentTimeMillis());

    // 登录成功 插入登录记录表
    userDao.loginRecord(loginId, LoginTypeConstant.VERIFICATION_CODE_LOGIN);
    return tokenVO;

  }


  /**
   * 手机号密码登录
   */
  public LoginTokenVO passwordLogin(LoginForm form) {
    Map<String, Object> map = new HashMap<>(2);
    UserEntity user = userDao.selectUserByPhone(form.getMobile());
    // 查无此人
    if (user == null) {
      throw new SzException("用户不存在");
    } else if (!BCrypt.checkpw(form.getPassword(), user.getPassword())) {
      // 有此人但密码错误
      throw new SzException("手机号或密码错误");
    }
    // 获取登录token
    TokenEntity tokenEntity = tokenService.createToken(user.getId());
    // VO fills
    LoginTokenVO tokenVO = new LoginTokenVO();
    tokenVO.setIsFresh(false);
    tokenVO.setToken(tokenEntity.getToken());
    tokenVO.setExpire(tokenEntity.getExpireTime().getTime() - System.currentTimeMillis());

    userDao.loginRecord(user.getId(), LoginTypeConstant.PASSWORD_LOGIN);
    return tokenVO;
  }

  @Override
  public int setPassword(UserEntity user, String password) {
    String salt = BCrypt.gensalt(12);
    String pwd = BCrypt.hashpw(password, salt);
    return userDao.setPassword(user.getId(), pwd);
  }

  @Override
  public Map<String, Object> sendVerificationCode(VerificationCodeForm verificationCodeForm) {
    Map<String, Object> map = new HashMap<>(2);
    //校验图形验证码
    boolean captchaFlag = captchaService.validate(verificationCodeForm.getUuid(), verificationCodeForm.getCaptcha());
    if (!captchaFlag) {
      map.put("code", LoginTypeConstant.ERROR);
      map.put("msg", "图形验证码错误");
      return map;
    }
    //校验手机号
    String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
    Pattern p = Pattern.compile(regExp);
    Matcher m = p.matcher(verificationCodeForm.getMobile());
    if (!m.matches()) {
      map.put("code", LoginTypeConstant.ERROR);
      map.put("msg", "请输入正确的手机号");
      return map;
    }
    //配置文件中获取短信平台地址及apikey
    String url = messageConfig.getUrl();
    String apikey = messageConfig.getApikey();
    Boolean msgSwitch = messageConfig.getMsgSwitch();
    Map<String, String> resultMap = new HashMap<>(2);
    if (msgSwitch) {
      //正式环境发送短信
      resultMap = SendMsgUtil.msg(verificationCodeForm.getMobile(), url, apikey);
    } else {
      //非正式环境 验证码写死为123456
      resultMap.put("verificationCode", "123456");
      resultMap.put("returnMsg", "{\"code\":1,\"msg\":\"短信发送成功\",\"result\":null}");
    }
    JSONObject msgObj = JSONObject.fromObject(resultMap.get("returnMsg"));

    String returnCode = "code";
    if (!LoginTypeConstant.MESSAGE_SUCCESS.equals(msgObj.get(returnCode).toString())) {
      map.put("code", LoginTypeConstant.ERROR);
      map.put("msg", "发送短信失败");
      return map;
    } else {
      String verificationCode = resultMap.get("verificationCode");
      redisTemplate.opsForValue().set(verificationCodeForm.getMobile(), verificationCode, 5, TimeUnit.MINUTES);
      map.put("code", LoginTypeConstant.SUCCESS);
      map.put("msg", "发送短信成功");
      return map;
    }

  }

  @Override
  public Map<String, Object> updateUser(UserEntity user, UpdateUserForm updateUserForm) {
    //传入手机号不为null 则为修改账号
    if (updateUserForm.getMobile() != null) {
      String verificationCode = updateUserForm.getVerificationCode();
      String verificationCodeInRedis = redisTemplate.opsForValue().get(updateUserForm.getMobile());
      if (!Objects.equals(verificationCode, verificationCodeInRedis)) {
        throw new SzException("手机验证码错误或已过期");
      }
    }
    Map<String, Object> map = new HashMap<>(2);
    try {
      String salt = BCrypt.gensalt(12);
      String pwd = BCrypt.hashpw(updateUserForm.getPassword(), salt);
      updateUserForm.setPassword(pwd);
      int isSuccess = userDao.updateUser(user.getId(), updateUserForm.getName(), updateUserForm.getPassword(), updateUserForm.getMobile());
      if (isSuccess == 1) {
        map.put("code", LoginTypeConstant.SUCCESS);
        map.put("msg", "个人信息修改成功");
      } else {
        map.put("code", LoginTypeConstant.ERROR);
        map.put("msg", "个人信息修改失败");
      }

    } catch (DuplicateKeyException e) {
      throw new SzException("数据库已存在该手机号");
    } catch (Exception e) {
      e.printStackTrace();
      throw new SzException("个人信息修改失败");
    }
    return map;
  }

  @Override
  public IPage<OrderVO> getOrders(UserEntity user, Page p, String sort, String order, String state) {
    IPage<OrderVO> resultPage = userDao.getOrders(p, user.getId(), sort, order, state);
    List<OrderVO> records = resultPage.getRecords();
    for (int i = 0; i < records.size(); i++) {
      OrderVO orderVO = records.get(i);
      //取相册第一张图
      JSONArray jsonArray = JSONArray.fromObject(orderVO.getPhoto());
      orderVO.setPhoto(jsonArray.get(0).toString());
      //设置订单状态对应的说明文字
      orderVO.setStateStr(BookingConst.getBookingState(orderVO.getState()).getState());
    }
    return resultPage;
  }

  @Override
  public BookingOrderEntity getOrderDetail(UserEntity userEntity, Long id) {
    //判断当前订单是否为本人订单
    BookingOrderEntity bookingOrderEntity = userDao.getOrderDetail(userEntity.getId(), id);
    if (bookingOrderEntity == null) {
      throw new SzException("订单未查到");
    }
    return bookingOrderEntity;
  }

  @Override
  public Map<String, Object> deleteOrder(Long id) {
    Map<String, Object> map = new HashMap<>(2);
    //查询判断该订单是否已被删除
    Boolean deleteFlag = userDao.getOrderDelete(id);
    if (deleteFlag) {
      throw new SzException("该订单已被软删除!");
    }
    int influenceCount = userDao.deleteOrder(id);
    if (influenceCount == 1) {
      map.put("code", CommonConstant.SUCCESS);
      map.put("msg", "订单软删除成功！");
    } else {
      map.put("code", CommonConstant.ERROR);
      map.put("msg", "订单软删除失败！");
    }
    return map;
  }
}
