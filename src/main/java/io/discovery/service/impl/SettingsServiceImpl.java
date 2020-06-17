package io.discovery.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.discovery.config.WechatPayConfig;
import io.discovery.dao.UserDao;
import io.discovery.entity.UserEntity;
import io.discovery.service.SettingService;
import io.discovery.util.HttpUtil;
import io.discovery.util.RandomUtil;
import io.discovery.util.Sha1;
import io.discovery.vo.WxconfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


/**
 * @author fzx
 * @date 2018/10/08
 */
@Service
public class SettingsServiceImpl extends ServiceImpl<UserDao, UserEntity> implements SettingService {
  @Autowired
  private WechatPayConfig wechatPayConfig;
  @Autowired
  private StringRedisTemplate redisTemplate;

  @Override
  public WxconfigVO wxconfig(String url) {
    WxconfigVO wxconfigVO = new WxconfigVO();
    //判断redis中有没有jsApiTicket 若没有 则重新计算并存入redis
    String redisKey = "jsApiTicket";
    String jsApiTicket;
    if (redisTemplate.opsForValue().get(redisKey) == null) {
      String requestUrl = wechatPayConfig.getAccessTokenUrl();
      String params = "grant_type=client_credential&appid=" + wechatPayConfig.getAppId() + "&secret=" + wechatPayConfig.getAppSecret() + "";
      String result = HttpUtil.sendGet(requestUrl + params);
      String accessToken = JSONObject.parseObject(result).getString("access_token");
      requestUrl = wechatPayConfig.getJsApiTicketUrl();
      params = "access_token=" + accessToken + "&type=jsapi";
      result = HttpUtil.sendGet(requestUrl + params);
      jsApiTicket = JSONObject.parseObject(result).getString("ticket");
      redisTemplate.opsForValue().set("jsApiTicket", jsApiTicket, 7200, TimeUnit.SECONDS);
    } else {
      jsApiTicket = redisTemplate.opsForValue().get(redisKey);
    }
    String noncestr = RandomUtil.generateStr(32);
    String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
    String sign = Sha1.getJsSdkSign(noncestr, jsApiTicket, timestamp, url);

    wxconfigVO.setAppId(wechatPayConfig.getAppId());
    wxconfigVO.setNonceStr(noncestr);
    wxconfigVO.setTimestamp(timestamp);
    wxconfigVO.setSignature(sign);


    return wxconfigVO;
  }


}
