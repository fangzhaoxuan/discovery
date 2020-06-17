package io.discovery.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.discovery.entity.TokenEntity;
import org.springframework.stereotype.Service;

/**
 * 用户Token
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:22:07
 */
@Service
public interface TokenDao extends BaseMapper<TokenEntity> {

}
