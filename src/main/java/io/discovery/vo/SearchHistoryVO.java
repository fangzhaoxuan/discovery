package io.discovery.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author fzx
 * @date 2019-1-21
 */
@Getter
@Setter
public class SearchHistoryVO implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 关键字
   */
  private String keyword;


}
