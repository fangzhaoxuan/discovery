package io.discovery.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fzx
 * @date 2019-1-3
 */
@Getter
@Setter
@TableName("bitao_shop")
public class TaobaoShopEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  @TableId
  private Long id;

  /**
   * 卖家ID
   */
  private Long sellerId;

  /**
   * 卖家昵称
   */
  private String sellerNick;

  /**
   * 店铺名称
   */
  private String shopTitle;

  /**
   * 店铺主图URL
   */
  private String shopLogoUrl;

  /**
   * 店铺地址
   */
  private String shopUrl;

  /**
   * 店铺DSR评分
   */
  private Integer shopDsr;

  /**
   * 商品来源
   */
  private String source;

  /**
   * 创建时间
   */
  @JsonIgnore
  private Date gmtCreate;

  /**
   * 修改时间
   */
  @JsonIgnore
  private Date gmtModified;

}
