package io.discovery.util;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Http工具类，发送Http请求， Get请求请将参数放在url中 Post请求请将参数放在Map中
 *
 * @author fzx
 * @date 2018/11/5
 */
public class HttpUtil {
  private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
  private static final CloseableHttpClient HTTP_CLIENT = HttpClients.createDefault();
  private static final String USERAGENT = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36";
  private final static int CONNECT_TIMEOUT = 5000;
  private final static String DEFAULT_ENCODING = "UTF-8";

  /**
   * 发送HttpGet请求 * * @param url * 请求地址 * @return 返回字符串
   */
  public static String sendGet(String url) {
    String result = null;
    CloseableHttpResponse response = null;
    try {
      HttpGet httpGet = new HttpGet(url);
      httpGet.setHeader("User-Agent", USERAGENT);
      response = HTTP_CLIENT.execute(httpGet);
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        result = EntityUtils.toString(entity);
      }
    } catch (Exception e) {
      log.error("处理失败 {}" + e);
      e.printStackTrace();
    } finally {
      if (response != null) {
        try {
          response.close();
        } catch (IOException e) {
          log.error(e.getMessage());
        }
      }
    }
    return result;
  }

  /**
   * 发送HttpPost请求，参数为map * * @param url * 请求地址 * @param map * 请求参数 * @return 返回字符串
   */
  public static String sendPost(String url, Map<String, String> map) {
    // 设置参数
    List<NameValuePair> formparams = new ArrayList<NameValuePair>();
    for (Map.Entry<String, String> entry : map.entrySet()) {
      formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
    }
    // 编码
    UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
    // 取得HttpPost对象
    HttpPost httpPost = new HttpPost(url);
    // 防止被当成攻击添加的
    httpPost.setHeader("User-Agent", USERAGENT);
    // 参数放入Entity
    httpPost.setEntity(formEntity);
    CloseableHttpResponse response = null;
    String result = null;
    try {
      // 执行post请求
      response = HTTP_CLIENT.execute(httpPost);
      // 得到entity
      HttpEntity entity = response.getEntity();
      // 得到字符串
      result = EntityUtils.toString(entity);
    } catch (IOException e) {
      log.error(e.getMessage());
    } finally {
      if (response != null) {
        try {
          response.close();
        } catch (IOException e) {
          log.error(e.getMessage());
        }
      }
    }
    return result;
  }


  public static String postData(String urlStr, String data, String contentType) {
    BufferedReader reader = null;
    try {
      URL url = new URL(urlStr);
      URLConnection conn = url.openConnection();
      conn.setDoOutput(true);
      conn.setConnectTimeout(CONNECT_TIMEOUT);
      conn.setReadTimeout(CONNECT_TIMEOUT);
      if (contentType != null) {
        conn.setRequestProperty("content-type", contentType);
      }
      OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), DEFAULT_ENCODING);
      if (data == null) {
        data = "";
      }
      writer.write(data);
      writer.flush();
      writer.close();

      reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_ENCODING));
      StringBuilder sb = new StringBuilder();
      String line = null;
      while ((line = reader.readLine()) != null) {
        sb.append(line);
        sb.append("\r\n");
      }
      return sb.toString();
    } catch (IOException e) {
    } finally {
      try {
        if (reader != null) {
          reader.close();
        }
      } catch (IOException e) {
      }
    }
    return null;
  }

  /**
   *  
   * 获取真实ip地址 通过阿帕奇代理的也能获取到真实ip 
   *
   * @param request 
   * @return   
   */
  public static String getRealIp(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    String unkonwType = "unknown";
    if (ip == null || ip.length() == 0 || unkonwType.equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || unkonwType.equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || unkonwType.equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }


}