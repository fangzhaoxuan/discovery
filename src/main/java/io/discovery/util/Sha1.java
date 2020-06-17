package io.discovery.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA1加密类
 *
 * @author fzx
 * @date 2018/12/14
 */
public class Sha1 {
  public static void main(String[] args) {
    System.out.println(Sha1.getJsSdkSign("TEwui0mCnOackIEeFR7zIO1vijZYPLot", "HoagFKDcsGMVCIY2vOjf9h87Ti6XVXY8h3zqDUlP50GjUOfQsgpMjo9vNSBQ0bjOJKFotOGzf_uyVBFbjRQ4SA", "1544786816", "http://ah.vaiwan.com:8081/demo-pay"));
  }

  public static String getJsSdkSign(String noncestr, String tsapiTicket, String timestamp, String url) {
    String content = "jsapi_ticket=" + tsapiTicket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;
    String ciphertext = null;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-1");
      byte[] digest = md.digest(content.toString().getBytes());
      ciphertext = byteToStr(digest);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return ciphertext.toLowerCase();
  }

  public static String byteToStr(byte[] byteArray) {
    String strDigest = "";
    for (int i = 0; i < byteArray.length; i++) {
      strDigest += byteToHexStr(byteArray[i]);
    }
    return strDigest;
  }

  public static String byteToHexStr(byte mByte) {
    char[] digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    char[] tempArr = new char[2];
    tempArr[0] = digit[(mByte >>> 4) & 0X0F];
    tempArr[1] = digit[mByte & 0X0F];
    String s = new String(tempArr);
    return s;
  }
}
