package io.discovery.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * This class
 *
 * @author chenwei <acee06.weichen@gmail.com>
 * @date 2018/10/16
 */
@Getter
@Setter
public class LoginTokenVO implements Serializable {
  private Boolean isFresh;
  private String token;
  private Long expire;

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return getClass().getName() + "{ "
        + "isFresh: " + isFresh + ", "
        + "token: " + token + ", "
        + "expire: " + expire
        + " }";
  }
}
