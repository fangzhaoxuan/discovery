package io.discovery.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.discovery.entity.UserEntity;
import io.discovery.vo.WxconfigVO;

/**
 * 钱包
 *
 * @author fzx
 * @date 2018/12/14
 */
public interface SettingService extends IService<UserEntity> {
  /**
   * 获取微信公众号配置
   *
   * @param url 前端传入的url
   * @return 微信配置信息
   */
  WxconfigVO wxconfig(String url);

}
