package io.discovery.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fzx
 * @date 2018-10-30
 */
@Setter
@Getter
public class CommentVO implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @TableId
  private Long id;
  /**
   * 订单ID
   */
  private Long bookingOrderId;
  /**
   * 用户名
   */
  private String userName;
  /**
   * 评价
   */
  private String comment;
  /**
   * 评分
   */
  private Integer score;
  /**
   * 创建时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date gmtCreate;
  /**
   * 修改时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date gmtModified;

  @Override
  public String toString() {
    return "CommentVO{" +
        "id=" + id +
        ", bookingOrderId=" + bookingOrderId +
        ", userName='" + userName + '\'' +
        ", comment='" + comment + '\'' +
        ", score=" + score +
        ", gmtCreate=" + gmtCreate +
        ", gmtModified=" + gmtModified +
        '}';
  }
}
