package io.discovery.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 淘宝生成淘口令表单
 *
 * @author FZX
 * @since 2018-11-2
 */
@ApiModel(value = "淘宝生成淘口令表单")
@Getter
@Setter
public class TpwdForm {

  /**
   * 口令弹框内容
   */
  @ApiModelProperty(value = "口令弹框内容", example = "你好", required = true)
  @NotNull(message = "text:不能为空")
  private String text;

  /**
   * 口令跳转目标页，一般为返回的couponUrl
   */
  @ApiModelProperty(value = "口令跳转目标页", example = "https://xxxx", required = true)
  @NotNull(message = "url:不能为空")
  private String url;

  /**
   * 口令弹框logoURL，一般为商品主图
   */
  @ApiModelProperty(value = "口令弹框logoURL", example = "//xxxxx", required = true)
  @NotNull(message = "logo:不能为空")
  private String logo;
}
