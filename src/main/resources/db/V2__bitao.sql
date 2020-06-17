
CREATE SCHEMA IF NOT EXISTS `thanz` DEFAULT CHARACTER SET utf8 ;
USE `thanz` ;

-- -----------------------------------------------------
-- Table `thanz`.`bitao_user_taobao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thanz`.`bitao_user_taobao` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` BIGINT(20) UNSIGNED NULL COMMENT '51闪住用户ID',
  `third_user_id` VARCHAR(20) NULL COMMENT '第三方平台用户ID，以字符串存储方便查询匹配后六位',
  `third_open_uid` VARCHAR(45) NULL COMMENT '第三方平台给当前appkey的唯一用户ID',
  `access_token` VARCHAR(100) NULL COMMENT '用户淘宝access_token',
  `access_token_expire_time` DATETIME NULL COMMENT '用户淘宝access_token到期时间',
  `refresh_token` VARCHAR(100) NULL COMMENT '用户刷新token（可用来刷新access_token）',
  `refresh_token_expire_time` DATETIME NULL COMMENT '用户淘宝刷新token到期时间',
  `gmt_create` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`user_id` ASC),
  INDEX `idx_third_user_id` (`third_user_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '淘宝用户表';


-- -----------------------------------------------------
-- Table `thanz`.`bitao_goods_taobao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thanz`.`bitao_goods_taobao` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `item_id` BIGINT(20) UNSIGNED NULL COMMENT '淘宝宝贝ID',
  `pic_url` VARCHAR(255) NULL COMMENT '商品主图',
  `item_url` VARCHAR(1000) NULL COMMENT '商品详情链接',
  `photos` VARCHAR(1000) NULL DEFAULT '[]' COMMENT '商品相册',
  `title` VARCHAR(255) NULL COMMENT '商品标题',
  `source` VARCHAR(20) NULL COMMENT '来源标识（taobao/tmall/jd/vip/...）',
  `zk_final_price` DECIMAL(10,2) NULL COMMENT '折扣价',
  `has_coupon` TINYINT(1) NULL COMMENT '是否有券',
  `coupon_discount` DECIMAL(10,2) NULL COMMENT '券面额',
  `couponed_price` DECIMAL(10,2) NULL COMMENT '券后价',
  `coupon_start_time` VARCHAR(20) NULL COMMENT '优惠券开始时间 yyyy-MM-dd',
  `coupon_end_time` VARCHAR(20) NULL COMMENT '优惠券结束时间 yyyy-MM-dd',
  `commission_rate` DECIMAL(8,5) NULL COMMENT '佣金比例，用于计算返币量。0.121表示12.1%',
  `volume` INT UNSIGNED NULL COMMENT '月销量',
  `provcity` VARCHAR(25) NULL COMMENT '宝贝所在地',
  `coupon_url` VARCHAR(1000) NULL COMMENT '跳转领取购买链接',
  `detail_description` TEXT NULL COMMENT '商品图文详情json数组',
  `shop_id` BIGINT(20) UNSIGNED NULL COMMENT '淘宝商品对应的店铺外键ID',
  `seller_id` BIGINT(20) UNSIGNED NULL COMMENT '卖家ID冗余，可通过store_id获取',
  `gmt_create` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  INDEX `idx_item_id` (`item_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '淘宝商品详情表';


-- -----------------------------------------------------
-- Table `thanz`.`bitao_shop`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thanz`.`bitao_shop` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `seller_id` BIGINT(20) NULL COMMENT '淘宝卖家ID',
  `seller_nick` VARCHAR(45) NULL COMMENT '卖家昵称',
  `shop_title` VARCHAR(45) NULL COMMENT '店铺名称',
  `shop_logo_url` VARCHAR(255) NULL COMMENT '店铺主图url',
  `shop_url` VARCHAR(255) NULL COMMENT '店铺地址',
  `shop_dsr` INT NULL COMMENT '店铺dsr评分，五位整数',
  `source` VARCHAR(20) NULL COMMENT '店铺来源（taobao/tmall/jd/vip/...）',
  `gmt_create` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`seller_id` ASC),
  INDEX `idx_third_user_id` (`seller_nick` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '店铺表，所有第三方来源共用一张店铺表，以source来区分';


-- -----------------------------------------------------
-- Table `thanz`.`bitao_advertisement`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thanz`.`bitao_advertisement` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` VARCHAR(100) NULL COMMENT '运营活动标题',
  `image_url` VARCHAR(255) NULL COMMENT '运营活动图片链接',
  `path` VARCHAR(255) NULL COMMENT '相对路径跳转链接',
  `is_published` TINYINT NULL DEFAULT 0 COMMENT '是否已发布',
  `publish_time` DATETIME NULL COMMENT '活动上线时间',
  `is_longterm` TINYINT NULL DEFAULT 1 COMMENT '是否长期活动',
  `expire_time` DATETIME NULL COMMENT '如果非长期活动，下架时间',
  `gmt_create` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `unique_code` VARCHAR(255) NULL COMMENT 'oms唯一编码',
  `creator_id` BIGINT(20) UNSIGNED NULL COMMENT 'oms创建者',
  `updater_id` BIGINT(20) UNSIGNED NULL COMMENT 'oms更新者',
  `is_deleted` TINYINT NULL DEFAULT 0 COMMENT '是否被软删除',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '广告营销活动表';


-- -----------------------------------------------------
-- Table `thanz`.`bitao_search_history`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thanz`.`bitao_search_history` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` BIGINT(20) UNSIGNED NULL COMMENT '用户ID',
  `keyword` VARCHAR(255) NULL COMMENT '搜索关键字',
  `gmt_create` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` TINYINT NULL DEFAULT 0 COMMENT '是否被软删除',
  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`user_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '用户搜索历史表';


-- -----------------------------------------------------
-- Table `thanz`.`bitao_favorite`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thanz`.`bitao_favorite` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` BIGINT(20) UNSIGNED NULL COMMENT '用户ID',
  `goods_id` BIGINT(20) UNSIGNED NULL COMMENT '商品ID，不同来源对应不同商品表',
  `source` VARCHAR(20) NULL COMMENT '来源（taobao/tmall/jd/vip/...）',
  `gmt_create` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` TINYINT NULL DEFAULT 0 COMMENT '是否被软删除',
  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`user_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '用户商品收藏表';


-- -----------------------------------------------------
-- Table `thanz`.`bitao_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thanz`.`bitao_order` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `booking_no` VARCHAR(100) NULL COMMENT '第三方平台订单号',
  `user_id` BIGINT(20) UNSIGNED NULL COMMENT '用户ID',
  `goods_id` BIGINT(20) UNSIGNED NULL COMMENT '商品ID，不同来源对应不同商品表',
  `source` VARCHAR(20) NULL COMMENT '来源（taobao/tmall/jd/vip/...）',
  `state` INT NULL COMMENT '状态（跟踪中1、待放币2、已到账3、已退货4）',
  `gmt_create` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_deleted` TINYINT NULL DEFAULT 0 COMMENT '是否被软删除',
  `json_record` TEXT NULL COMMENT '第三方平台返回的订单记录json',
  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`user_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '挖矿订单表';


-- -----------------------------------------------------
-- Table `thanz`.`bitao_goods_track`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `thanz`.`bitao_goods_track` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` BIGINT(20) UNSIGNED NULL COMMENT '用户ID',
  `goods_id` BIGINT(20) UNSIGNED NULL COMMENT '商品ID，不同来源对应不同商品表',
  `source` VARCHAR(20) NULL COMMENT '来源（taobao/tmall/jd/vip/...）',
  `gmt_create` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`user_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '用户足迹记录表';
