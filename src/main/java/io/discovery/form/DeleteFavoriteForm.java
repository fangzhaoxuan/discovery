package io.discovery.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 删除收藏宝贝表单
 *
 * @author FZX
 * @since 2019-1-13
 */
@ApiModel(value = "删除收藏宝贝表单")
@Getter
@Setter
public class DeleteFavoriteForm {

  /**
   * 需要删除的收藏ID集合
   */
  @ApiModelProperty(value = "需要删除的收藏ID集合", example = "[1,2,3]", required = true)
  @NotNull(message = "需要删除的收藏ID集合:不能为空")
  private List ids;

}
