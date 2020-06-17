package io.discovery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.discovery.entity.CaptchaEntity;

import java.awt.image.BufferedImage;

/**
 * 验证码
 *
 * @author Mark sunlightcs@gmail.com
 * @since 2.0.0 2018-02-10
 */
public interface CaptchaService extends IService<CaptchaEntity> {


  /**
   * 获取图形验证码
   *
   * @param uuid String uuid
   * @return 图片流
   */
  BufferedImage getCaptcha(String uuid);

  /**
   * 验证码效验
   *
   * @param uuid uuid
   * @param code 验证码
   * @return true：成功  false：失败
   */
  boolean validate(String uuid, String code);
}
