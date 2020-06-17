package io.discovery.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author fzx
 * @date 2019-1-11
 */
@Getter
@Setter
public class TaobaoOrdersVO implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 是否未授权
   */
  @JsonProperty("isUnauthorized")
  private Boolean unAuthorized;

  /**
   * 是否授权过期
   */
  @JsonProperty("isAuthorizeExpired")
  private Boolean authorizeExpired;

  /**
   * 授权地址
   */
  private String authorizeUrl;

  /**
   * 订单内容
   */
  private IPage<TaobaoOrderPageVO> orders;

  @Override
  public String toString() {
    return "TaobaoOrdersVO{" +
        "unauthorized=" + unAuthorized +
        ", authorizeExpired=" + authorizeExpired +
        ", authorizeUrl='" + authorizeUrl + '\'' +
        ", taobaoOrdersVOPage=" + orders +
        '}';
  }
}
