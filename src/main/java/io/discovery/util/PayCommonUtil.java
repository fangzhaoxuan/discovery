package io.discovery.util;

import java.util.*;

/**
 * 微信支付工具类
 *
 * @author fzx
 * @date 2018/11/15
 */
public class PayCommonUtil {
  /**
   * 是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
   *
   * @return boolean
   */
  public static boolean isTenpaySign(String characterEncoding, SortedMap<Object, Object> packageParams, String apiKey) {
    StringBuffer sb = new StringBuffer();
    Set es = packageParams.entrySet();
    Iterator it = es.iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry) it.next();
      String k = (String) entry.getKey();
      String v = (String) entry.getValue();
      if (!"sign".equals(k) && null != v && !"".equals(v)) {
        sb.append(k + "=" + v + "&");
      }
    }

    sb.append("key=" + apiKey);

    // 算出摘要
    String mysign = Md5.calc(sb.toString()).toLowerCase();
    String tenpaySign = ((String) packageParams.get("sign")).toLowerCase();

    return tenpaySign.equals(mysign);
  }


  public static String createSign(String characterEncoding, SortedMap<Object, Object> packageParams, String apiKey) {
    StringBuffer sb = new StringBuffer();
    Set es = packageParams.entrySet();
    Iterator it = es.iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry) it.next();
      String k = (String) entry.getKey();
      String v = (String) entry.getValue();
      if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
        sb.append(k + "=" + v + "&");
      }
    }
    sb.append("key=" + apiKey);
    String sign = Md5.calc(sb.toString()).toUpperCase();
    return sign;
  }

  public static void main(String[] args) {
    SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
    packageParams.put("appid", "wx399ce4c35a00290f");
    packageParams.put("attach", "支付测试");
    packageParams.put("body", "H5支付测试");
    packageParams.put("mch_id", "1503803601");
    packageParams.put("nonce_str", "ibuaiVcKdpRxkhJA");
    packageParams.put("notify_url", "http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php");
    packageParams.put("out_trade_no", "1415659990");
    packageParams.put("scene_info", "{\"h5_info\": {\"type\":\"IOS\",\"app_name\": \"王者荣耀\",\"package_name\": \"com.tencent.tmgp.sgame\"}}");
    packageParams.put("spbill_create_ip", "125.118.106.114");
    packageParams.put("total_fee", "1");
    packageParams.put("trade_type", "MWEB");

    System.out.println(createSign("utf-8", packageParams, "981BF84C66A78E328FDE7469F697B4DA"));
  }

  /**
   * @param parameters 请求参数
   * @return
   * @author
   * @date 2016-4-22
   * @Description：将请求参数转换为xml格式的string
   */
  public static String getRequestXml(SortedMap<Object, Object> parameters) {
    StringBuffer sb = new StringBuffer();
    sb.append("<xml>");
    Set es = parameters.entrySet();
    Iterator it = es.iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry) it.next();
      String k = (String) entry.getKey();
      String v = (String) entry.getValue();
      if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k) || "return_code".equalsIgnoreCase(k) || "return_msg".equalsIgnoreCase(k)) {
        sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
      } else {
        if ("total_fee".equalsIgnoreCase(k)) {
          sb.append("<" + k + ">" + Integer.parseInt(v) + "</" + k + ">");
        } else {
          sb.append("<" + k + ">" + v + "</" + k + ">");
        }
      }
    }
    sb.append("</xml>");
    return sb.toString();
  }

  /**
   * 验证回调签名
   *
   * @return
   */
  public static boolean isTenpaySign(Map<String, String> map, String appKey) {
    String charset = "utf-8";
    String signFromAPIResponse = map.get("sign");
    if (signFromAPIResponse == null || "".equals(signFromAPIResponse)) {
      return false;
    }
    //过滤空 设置 TreeMap
    SortedMap<String, String> packageParams = new TreeMap();

    for (String parameter : map.keySet()) {
      String parameterValue = map.get(parameter);
      String v = "";
      if (null != parameterValue) {
        v = parameterValue.trim();
      }
      packageParams.put(parameter, v);
    }

    StringBuffer sb = new StringBuffer();
    Set es = packageParams.entrySet();
    Iterator it = es.iterator();

    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry) it.next();
      String k = (String) entry.getKey();
      String v = (String) entry.getValue();
      if (!"sign".equals(k) && null != v && !"".equals(v)) {
        sb.append(k + "=" + v + "&");
      }
    }
    sb.append("key=" + appKey);

    //将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
    //算出签名
    String resultSign = "";
    String tobesign = sb.toString();

    if (null == charset || "".equals(charset)) {
      resultSign = Md5.calc(tobesign).toUpperCase();
    } else {
      try {
        resultSign = Md5.calc(tobesign).toUpperCase();
      } catch (Exception e) {
        resultSign = Md5.calc(tobesign).toUpperCase();
      }
    }

    String tenpaySign = (packageParams.get("sign")).toUpperCase();
    return tenpaySign.equals(resultSign);
  }
}