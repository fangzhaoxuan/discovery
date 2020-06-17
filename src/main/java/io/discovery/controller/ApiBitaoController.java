package io.discovery.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.discovery.annotation.Login;
import io.discovery.annotation.LoginUser;
import io.discovery.annotation.UnauthorizationUser;
import io.discovery.annotation.User;
import io.discovery.common.utils.R;
import io.discovery.entity.UserEntity;
import io.discovery.form.AuthorizationTaobaoForm;
import io.discovery.form.DeleteFavoriteForm;
import io.discovery.form.FavoriteForm;
import io.discovery.form.TpwdForm;
import io.discovery.service.BitaoService;
import io.discovery.vo.FavoriteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @author fzx
 * @date 2019-1-2
 */
@RestController
@RequestMapping("/v1")
public class ApiBitaoController {
  @Autowired
  private BitaoService bitaoService;

  @GetMapping("/bitao/carousels")
  @ApiOperation("获取轮播图")
  public R getAdvertisementPhoto() {
    return R.data(bitaoService.getAdvertisementPhoto());
  }

  @GetMapping("/bitao/goods/hot")
  @ApiOperation("获取热销商品")
  public R getHotGoods(Integer page, Integer limit) {
    return R.data(bitaoService.getHotGoods(page, limit));
  }


  @User
  @GetMapping("/bitao/goods")
  @ApiOperation("搜索商品")
  public R searchGoods(HttpServletRequest request, @UnauthorizationUser UserEntity userEntity, String keyword, Integer page, Integer limit, @RequestParam(value = "sort", defaultValue = "") String sort, @RequestParam(value = "order", defaultValue = "des") String order) {
    return R.data(bitaoService.searchGoods(userEntity, page, limit, keyword, sort, order, request));
  }

  @User
  @GetMapping("/bitao/search-history/list")
  @ApiOperation("获取搜索历史")
  public R getSearchHistory(@UnauthorizationUser UserEntity userEntity) {
    return R.data(bitaoService.getSearchHistory(userEntity));
  }

  @Login
  @PostMapping("/bitao/search-history/delete")
  @ApiOperation("清空搜索历史")
  public R deleteSearchHistory(@LoginUser UserEntity userEntity) {
    return R.ok(bitaoService.deleteSearchHistory(userEntity));
  }

  @User
  @GetMapping("/bitao/goods-detail")
  @ApiOperation("获取宝贝详情")
  public R getItemDetail(@UnauthorizationUser UserEntity userEntity, Long itemId, String source) {
    return R.data(bitaoService.getItemDetail(userEntity, itemId, source));
  }

  @GetMapping("/bitao/goods-detail-description")
  @ApiOperation("获取宝贝图文详情")
  public R getItemDetail(String itemId, String source) {
    return R.data(bitaoService.getDescription(itemId, source));
  }

  @GetMapping("/bitao/shop")
  @ApiOperation("获取店铺信息")
  public R getStoreInfo(Long itemId) {
    return R.data(bitaoService.getShopInfo(itemId));
  }

  @PostMapping("/bitao/goods/tpwd")
  @ApiOperation("获取淘口令")
  public R createTpwd(@RequestBody @Validated TpwdForm tpwdForm) {
    return R.data(bitaoService.createTpwd(tpwdForm));
  }

  @User
  @GetMapping("/bitao/goods/guess-like")
  @ApiOperation("猜你喜欢")
  public R getGuessLikeGoods(@UnauthorizationUser UserEntity userEntity, Integer page, Integer limit) {
    return R.data(bitaoService.getGuessLikeGoods(userEntity, page, limit));
  }

  @Login
  @PostMapping("/bitao/favorites/add")
  @ApiOperation("收藏宝贝")
  public R addFavorite(@LoginUser UserEntity userEntity, @RequestBody @Validated FavoriteForm favoriteForm) {
    return R.ok(bitaoService.addFavorite(userEntity, favoriteForm));
  }

  @Login
  @PostMapping("/bitao/favorites/delete")
  @ApiOperation("删除收藏宝贝")
  public R deleteFavorites(@RequestBody @Validated DeleteFavoriteForm deleteFavoriteForm) {
    return R.ok(bitaoService.deleteFavorites(deleteFavoriteForm.getIds()));
  }

  @Login
  @GetMapping("/bitao/favorites")
  @ApiOperation("获取收藏宝贝")
  public R getFavorites(@LoginUser UserEntity userEntity, Integer page, Integer limit, @RequestParam(value = "sort", defaultValue = "f.id") String sort, @RequestParam(value = "order", defaultValue = "desc") String order) {
    Page<FavoriteVO> favoriteVOPage = new Page<>(page, limit);
    return R.data(bitaoService.getFavorites(favoriteVOPage, userEntity, sort, order));
  }

  @Login
  @GetMapping("/bitao/orders")
  @ApiOperation("获取所有订单")
  public R getOrders(@LoginUser UserEntity userEntity, Integer page, Integer limit, String state) {
    return R.data(bitaoService.getOrders(userEntity, page, limit, state));
  }

  @Login
  @PostMapping("/bitao/orders/{id}/delete")
  @ApiOperation("删除订单")
  public R deleteOrder(@LoginUser UserEntity userEntity, @PathVariable Long id) {
    return R.ok(bitaoService.deleteOrder(userEntity, id));
  }

  @Login
  @PostMapping("/bitao/authorize/taobao")
  @ApiOperation("淘宝授权")
  public R authorizationTaobao(@LoginUser UserEntity userEntity, @RequestBody AuthorizationTaobaoForm authorizationTaobaoForm) {
    return R.ok(bitaoService.authorizationTaobao(userEntity, authorizationTaobaoForm));
  }
}
