package io.discovery.util;

import java.security.MessageDigest;

/**
 * MD5加密类
 *
 * @author fzx
 * @date 2018/11/12
 */
public class Md5 {
  public final static String calc(String ss) {
    //如果为空，则返回""
    String s = ss == null ? "" : ss;
    //字典
    char[] hexDigists = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
        'e', 'f'};

    try {
      //获取二进制
      byte[] strTemp = s.getBytes();
      MessageDigest mdTemp = MessageDigest.getInstance("Md5");
      //执行加密
      mdTemp.update(strTemp);
      //加密结果
      byte[] md = mdTemp.digest();
      //结果长度
      int j = md.length;
      //字符数组
      char[] str = new char[j * 2];
      int k = 0;
      //将二进制加密结果转化为字符
      for (int i = 0; i < j; i++) {
        byte byte0 = md[i];
        str[k++] = hexDigists[byte0 >>> 4 & 0xf];
        str[k++] = hexDigists[byte0 & 0xf];

      }
      //输出加密后的字符
      return new String(str);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void main(String[] args) {
    String a = "zhejiangshanzhu";
    System.out.println(Md5.calc(a).toUpperCase());
  }
}