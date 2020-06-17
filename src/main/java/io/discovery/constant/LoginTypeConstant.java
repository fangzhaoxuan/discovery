package io.discovery.constant;

/**
 * 常量
 *
 * @author fzx
 * @date 2018/10/06
 */
public class LoginTypeConstant {
  /**
   * 成功标识
   */
  public static final int SUCCESS = 0;

  /**
   * 失败标识
   */
  public static final int ERROR = 1;

  /**
   * 登录方式：手机验证码登录
   */
  public static final int VERIFICATION_CODE_LOGIN = 1;

  /**
   * 登录方式：手机号密码登录
   */
  public static final int PASSWORD_LOGIN = 2;

  /**
   * 短信发送成功
   */
  public static final String MESSAGE_SUCCESS = "1";

  /**
   * 邀请得币次数上限
   */
  public static final int INVITE_SUCCESS_COUNT = 30;

  /**
   * 邀请双方均送金币
   */
  public static final int INVITE_REWARD = 100;

  /**
   * 新用户登录送金币
   */
  public static final int NEW_USER_REWARD = 200;


}
