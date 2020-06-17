package io.discovery.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 填充配置类
 *
 * @author fzx
 * @date 2018/10/10
 */
@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {

  @Override
  public void insertFill(MetaObject metaObject) {
    setFieldValByName("testDate", new Date(), metaObject);
  }

  @Override
  public void updateFill(MetaObject metaObject) {
  }
}
