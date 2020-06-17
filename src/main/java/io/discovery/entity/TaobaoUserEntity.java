package io.discovery.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


/**
 * 淘宝用户
 *
 * @author fzx
 * @date 2019-1-10
 */
@TableName("bitao_user_taobao")
@Setter
@Getter
public class TaobaoUserEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 主键ID
   */
  @TableId(type = IdType.INPUT)
  private Long id;

  /**
   * 51闪住用户ID
   */
  private Long userId;

  /**
   * 第三方平台用户ID，以字符串存储方便查询匹配后六位
   */
  private String thirdUserId;

  /**
   * 第三方平台给当前appkey的唯一用户ID
   */
  private String thirdOpenUid;

  /**
   * 用户淘宝access_token
   */
  private String accessToken;

  /**
   * 用户淘宝access_token到期时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date accessTokenExpireTime;

  /**
   * 用户淘宝refresh_token
   */
  private String refreshToken;

  /**
   * 用户淘宝refreshToken到期时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date refreshTokenExpireTime;

  /**
   * 创建时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date gmtCreate;

  /**
   * 修改时间
   */
  @JsonIgnore
  private Date gmtModified;
}
