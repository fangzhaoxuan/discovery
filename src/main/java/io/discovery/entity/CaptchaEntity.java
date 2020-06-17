package io.discovery.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 系统验证码
 *
 * @author Mark sunlightcs@gmail.com
 * @since 2.0.0 2018-02-10
 */
@TableName("tb_captcha")
public class CaptchaEntity {
  @TableId(type = IdType.INPUT)
  private String uuid;
  /**
   * 验证码
   */
  private String code;
  /**
   * 过期时间
   */
  private Date expireTime;

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Date getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(Date expireTime) {
    this.expireTime = expireTime;
  }
}
