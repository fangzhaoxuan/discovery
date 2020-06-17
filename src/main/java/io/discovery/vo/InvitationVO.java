package io.discovery.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fzx
 * @date 2018-12-17
 */
@Setter
@Getter
public class InvitationVO implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 脱敏手机号
   */
  private String invitedPhone;
  /**
   * 邀请日期
   */
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date gmtCreate;

  @Override
  public String toString() {
    return "InvitationVO{" +
        "mobile='" + invitedPhone + '\'' +
        ", gmtCreat=" + gmtCreate +
        '}';
  }
}
