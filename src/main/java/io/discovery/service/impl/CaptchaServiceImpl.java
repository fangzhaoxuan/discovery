package io.discovery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import io.discovery.common.exception.SzException;
import io.discovery.common.utils.DateUtils;
import io.discovery.dao.CaptchaDao;
import io.discovery.entity.CaptchaEntity;
import io.discovery.service.CaptchaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Date;


/**
 * 验证码
 *
 * @author Mark sunlightcs@gmail.com
 * @since 2.0.0 2018-02-10
 */
@Service
public class CaptchaServiceImpl extends ServiceImpl<CaptchaDao, CaptchaEntity> implements CaptchaService {
  @Autowired
  private Producer producer;

  @Override
  public BufferedImage getCaptcha(String uuid) {
    if (StringUtils.isBlank(uuid)) {
      throw new SzException("uuid不能为空");
    }
    //生成文字验证码
    String code = producer.createText();

    CaptchaEntity captchaEntity = new CaptchaEntity();
    captchaEntity.setUuid(uuid);
    captchaEntity.setCode(code);
    //5分钟后过期
    captchaEntity.setExpireTime(DateUtils.addDateMinutes(new Date(), 5));
    this.save(captchaEntity);
    return producer.createImage(code);
  }

  @Override
  public boolean validate(String uuid, String code) {
    CaptchaEntity captchaEntity = this.getOne(new QueryWrapper<CaptchaEntity>().eq("uuid", uuid));
    if (captchaEntity == null) {
      return false;
    }

    //删除验证码
    this.removeById(uuid);

    if (captchaEntity.getCode().equalsIgnoreCase(code) && captchaEntity.getExpireTime().getTime() >= System.currentTimeMillis()) {
      return true;
    }

    return false;
  }
}
