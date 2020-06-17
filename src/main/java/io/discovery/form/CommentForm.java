package io.discovery.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 酒店评分评价表单
 *
 * @author FZX
 * @since 2018-10-31
 */
@ApiModel(value = "酒店评分评价表单")
@Getter
@Setter
public class CommentForm {

  /**
   * 评价
   */
  @ApiModelProperty(value = "评价", example = "威瑞够的", required = true)
  @NotNull(message = "comment:不能为空")
  private String comment;

  /**
   * 评分
   */
  @ApiModelProperty(value = "评分", example = "91", required = true)
  @NotNull(message = "score:不能为空")
  private Integer score;

}
