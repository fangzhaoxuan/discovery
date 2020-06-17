package io.discovery.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 支付宝支付表单
 *
 * @author FZX
 * @since 2018-11-2
 */
@ApiModel(value = "支付宝支付表单")
@Getter
@Setter
public class FavoriteForm {

  /**
   * 宝贝ID（本系统）
   */
  @ApiModelProperty(value = "宝贝ID（本系统）", example = "1", required = true)
  @NotNull(message = "goodsId:不能为空")
  private Long goodsId;

  /**
   * 宝贝来源
   */
  @ApiModelProperty(value = "宝贝来源", example = "tmall", required = true)
  @NotNull(message = "source:不能为空")
  private String source;
}
