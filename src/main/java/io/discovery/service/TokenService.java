package io.discovery.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.discovery.entity.TokenEntity;

/**
 * 用户Token
 *
 * @author fzx
 * @date 2018-10-18
 */
public interface TokenService extends IService<TokenEntity> {

  /**
   * 查询Token
   *
   * @param token String token
   * @return 用户Token
   */
  TokenEntity queryByToken(String token);

  /**
   * 生成token
   *
   * @param userId 用户ID
   * @return 返回token信息
   */
  TokenEntity createToken(long userId);

  /**
   * 设置token过期
   *
   * @param userId 用户ID
   */
  void expireToken(long userId);

}
