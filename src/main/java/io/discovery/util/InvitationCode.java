package io.discovery.util;

import org.apache.commons.lang.StringUtils;

/**
 * This class
 *
 * @author chenwei <acee06.weichen@gmail.com>
 * @date 2018/10/17
 */
public class InvitationCode {
  private static InvitationCode ourInstance = new InvitationCode();

  private String table = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public static InvitationCode getInstance() {
    return ourInstance;
  }

  private InvitationCode() {
  }

  public String gen(String mobile) {
    Long m = Long.parseUnsignedLong(mobile.trim().substring(1));
    int length = table.length();
    StringBuilder sb = new StringBuilder();
    Long index;
    while (m > 0) {
      index = m % length;
      sb.append(table.charAt(index.intValue()));
      m = m / length;
    }
    return sb.toString();
  }

  public String reverse(String code) {
    if (StringUtils.isNotBlank(code)) {
      code = code.trim().toUpperCase();
      long length = table.length();
      long sum = 0;
      for (int i = code.length(); i > 0; i--) {
        sum = sum * length;
        sum = sum + table.indexOf(code.charAt(i - 1));
      }
      return "1" + sum;
    }
    return null;
  }
}
