package io.discovery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.discovery.dao.TokenDao;
import io.discovery.entity.TokenEntity;
import io.discovery.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * TokenService实现类
 *
 * @author fzx
 * @date 2018/10/06
 */
@Service
public class TokenServiceImpl extends ServiceImpl<TokenDao, TokenEntity> implements TokenService {
  @Autowired
  private TokenDao tokenDao;

  /**
   * 93天后过期
   */
  private final static long EXPIRE = 3600 * 24 * 93;

  @Override
  public TokenEntity queryByToken(String token) {
    return this.getOne(new QueryWrapper<TokenEntity>().eq("token", token));
  }

  @Override
  public TokenEntity createToken(long userId) {
    //当前时间
    Date now = new Date();
    //过期时间
    Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

    //生成token
    String token = generateToken();

    //保存或更新用户token
    TokenEntity tokenEntity = new TokenEntity();
    tokenEntity.setUserId(userId);
    tokenEntity.setToken(token);
    tokenEntity.setUpdateTime(now);
    tokenEntity.setExpireTime(expireTime);
    this.saveOrUpdate(tokenEntity);

    return tokenEntity;
  }

  @Override
  public void expireToken(long userId) {
    Date now = new Date();

    TokenEntity tokenEntity = new TokenEntity();
    tokenEntity.setUserId(userId);
    tokenEntity.setUpdateTime(now);
    tokenEntity.setExpireTime(now);
    this.saveOrUpdate(tokenEntity);
  }

  private String generateToken() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
