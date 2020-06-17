package io.discovery.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 短信发送接口
 *
 * @author fzx
 * @date 2019/1/18
 */
public class SendMsgUtil {

  /**
   * 1
   * <p>
   * 发送验证码短信
   *
   * @param mobile 手机号码(唯一，不许多个)
   * @param url    短信接口地址
   * @param apikey apikey
   * @return json格式字符串
   */
  public static Map<String, String> msg(String mobile, String url, String apikey) {
    String ENCODING = "UTF-8";
    Random random = new Random();
    int randomNumber = (random.nextInt(899999) + 100000);
    //随机的六位验证码code
    String code = Integer.toString(randomNumber);
    //短信格式，可以修改
    String yzmContent = "【闪住】您的验证码是：" + code + "，请于5分钟内输入，过期无效！";
    if (StringUtils.isNotBlank(yzmContent)) {
      try {
        yzmContent = URLEncoder.encode(yzmContent, ENCODING);
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }

    NameValuePair[] data = {new NameValuePair("apikey", apikey),

        new NameValuePair("mobile", mobile),

        new NameValuePair("content", yzmContent)};

    String msg = doPost(url, data);
    Map<String, String> map = new HashMap<>(2);
    map.put("verificationCode", code);
    map.put("returnMsg", msg);
    return map;
  }

  /**
   * 基于HttpClient的post函数
   *
   * @param url  提交的URL
   * @param data 提交NameValuePair参数
   * @return 提交响应
   */
  private static String doPost(String url, NameValuePair[] data) {

    HttpClient client = new HttpClient();
    PostMethod method = new PostMethod(url);
    method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
    method.setRequestBody(data);
    client.getParams().setContentCharset("UTF-8");
    client.getParams().setConnectionManagerTimeout(10000);
    try {
      client.executeMethod(method);
      return method.getResponseBodyAsString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void main(String[] args) {
    //String url = "https://api.dingdongcloud.com/v1/sms/captcha/send";
    String url = "https://api.dingdongcloud.com/v2/sms/single_send";
    String apikey = "2437b2a2a045c916fe31d978310eaf19 ";
    System.out.println(msg("18606529703", url, apikey));
  }
}







