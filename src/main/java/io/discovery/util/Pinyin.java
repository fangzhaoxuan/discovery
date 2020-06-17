package io.discovery.util;


import io.discovery.vo.CityVO;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 重新封装城市数据
 *
 * @author fzx
 * @date 2018/12/5
 */
public class Pinyin {
  public static String toPinyin(String chinese) {
    String pinyinStr = "";
    char[] newChar = chinese.toCharArray();
    HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
    defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
    defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    for (int i = 0; i < newChar.length; i++) {
      if (newChar[i] > 128) {
        try {
          pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0];
        } catch (BadHanyuPinyinOutputFormatCombination e) {
          e.printStackTrace();
        }
      } else {
        pinyinStr += newChar[i];
      }
    }
    return pinyinStr;
  }


  public static List<Map<String, Object>> groupByFirstChar(List<CityVO> list) {
    List<List<CityVO>> resultList = new ArrayList<>(26);
    int circleCount = 26;
    for (int i = 0; i < circleCount; i++) {
      List<CityVO> cityVOS = new ArrayList<>();
      resultList.add(cityVOS);
    }

    for (int i = 0; i < list.size(); i++) {
      char fc = list.get(i).getCityNamePinyin().charAt(0);
      resultList.get(fc - 'a').add(list.get(i));
    }

    List<Map<String, Object>> returnList = new ArrayList<>();
    for (int i = 0; i < resultList.size(); i++) {
      Map<String, Object> resultMap = new HashMap<>(26);
      if (resultList.get(i).size() == 0) {
        continue;
      }
      resultMap.put("key", String.valueOf((char) (i + 'A')));
      resultMap.put("list", resultList.get(i));
      returnList.add(resultMap);
    }
    return returnList;
  }

}


