package io.discovery.util;

/**
 * 脱敏工具类
 *
 * @author fzx
 * @date 2016/10/18
 */
public class Desensitization {
  private static final int SIZE = 4;
  private static final String SYMBOL = "*";
  private static final int MINLENGTF = 2;

  public static String desensitization(String value) {
    if (null == value || "".equals(value)) {
      return value;
    }
    int len = value.length();
    int pamaone = len / 2;
    int pamatwo = pamaone - 1;
    int pamathree = len % 2;
    StringBuilder stringBuilder = new StringBuilder();
    if (len <= MINLENGTF) {
      if (pamathree == 1) {
        return SYMBOL;
      }
      stringBuilder.append(SYMBOL);
      stringBuilder.append(value.charAt(len - 1));
    } else {
      if (pamatwo <= 0) {
        stringBuilder.append(value.substring(0, 1));
        stringBuilder.append(SYMBOL);
        stringBuilder.append(value.substring(len - 1, len));

      } else if (pamatwo >= SIZE / MINLENGTF && SIZE + 1 != len) {
        int pamafive = (len - SIZE) / 2;
        stringBuilder.append(value.substring(0, pamafive));
        for (int i = 0; i < SIZE; i++) {
          stringBuilder.append(SYMBOL);
        }
        Boolean flag = (pamathree == 0 && SIZE / 2 == 0) || (pamathree != 0 && SIZE % 2 != 0);
        if (flag) {
          stringBuilder.append(value.substring(len - pamafive, len));
        } else {
          stringBuilder.append(value.substring(len - (pamafive + 1), len));
        }
      } else {
        int pamafour = len - 2;
        stringBuilder.append(value.substring(0, 1));
        for (int i = 0; i < pamafour; i++) {
          stringBuilder.append(SYMBOL);
        }
        stringBuilder.append(value.substring(len - 1, len));
      }
    }
    return stringBuilder.toString();

  }

  public static void main(String[] args) {
    System.out.println(desensitization("18606529703"));
  }
}