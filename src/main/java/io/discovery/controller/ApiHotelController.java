package io.discovery.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.discovery.annotation.Login;
import io.discovery.annotation.LoginUser;
import io.discovery.common.utils.R;
import io.discovery.entity.HotelEntity;
import io.discovery.entity.UserEntity;
import io.discovery.form.CommentForm;
import io.discovery.form.CreateOrderForm;
import io.discovery.service.HotelService;
import io.discovery.vo.CommentVO;
import io.discovery.vo.HotelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 预定模块（酒店）
 *
 * @author fzx
 * @date 2018/10/16
 */
@RestController
@RequestMapping("/v1")
@Api(tags = "酒店接口")
public class ApiHotelController {
  @Autowired
  private HotelService hotelService;

  @Login
  @GetMapping("/cities")
  @ApiOperation("获取城市数据")
  public R getCities() {
    return R.data(hotelService.getCities());
  }

  @Login
  @GetMapping("/hotels")
  @ApiOperation("酒店搜索接口")
  public R getHotels(BigDecimal longitude, BigDecimal latitude, String keyword, String inDate, String outDate, Integer mode, Integer page, Integer limit, @RequestParam(value = "sort", defaultValue = "hotel.id") String sort, @RequestParam(value = "order", defaultValue = "desc") String order) {
    Page<HotelEntity> p = new Page<>(page, limit);
    IPage<LinkedHashMap<String, Object>> resultPage = hotelService.getHotels(longitude, latitude, keyword, inDate, outDate, mode, p, sort, order);
    return R.data(resultPage);
  }

  @Login
  @GetMapping("/hotels/{id}")
  @ApiOperation("获取酒店详情")
  public R getHotelDetail(@PathVariable Long id) {
    HotelVO hotelVO = hotelService.getHotelDetails(id);
    return R.data(hotelVO);
  }

  @GetMapping("/recommend/special")
  @ApiOperation("特价推荐")
  public R specialRecommend(Integer page, Integer limit, @RequestParam(value = "sort", defaultValue = "hotel.id") String sort, @RequestParam(value = "order", defaultValue = "desc") String order, BigDecimal longitude, BigDecimal latitude) {
    Page<HotelEntity> p = new Page<>(page, limit);
    IPage<LinkedHashMap<String, Object>> resultPage = hotelService.specialRecommend(p, sort, order, longitude, latitude);
    return R.data(resultPage);
  }

  @GetMapping("/recommend/popular")
  @ApiOperation("热门推荐")
  public R popularRecommend(Integer adcode) {
    List<Map<String, Object>> resultList = hotelService.popularRecommend(adcode);
    return R.data(resultList);
  }

  @Login
  @PostMapping("/orders/create")
  @ApiOperation("生成订单")
  public R createOrder(@LoginUser UserEntity user, @RequestBody @Validated CreateOrderForm createOrderForm) {
    return R.data(hotelService.createOrder(user, createOrderForm));
  }

  @Login
  @GetMapping("/hotels/{id}/rooms")
  @ApiOperation("获取酒店房源")
  public R getRooms(@PathVariable Long id) {
    return R.data(hotelService.getRooms(id));
  }

  @Login
  @PostMapping("/orders/{id}/comments/create")
  @ApiOperation("订单评分评价")
  public R orderComment(@LoginUser UserEntity user, @PathVariable("id") Long id, @RequestBody @Validated CommentForm commentForm) {
    return R.data(hotelService.orderComment(user, id, commentForm));
  }

  @Login
  @GetMapping("hotels/{id}/comments")
  @ApiOperation("获取酒店评分评价")
  public R getComments(@PathVariable("id") Long id, Integer page, Integer limit, @RequestParam(value = "sort", defaultValue = "booking_comment.id") String sort, @RequestParam(value = "order", defaultValue = "desc") String order) {
    Page<CommentVO> commentVOPage = new Page<>(page, limit);
    return R.data(hotelService.getComments(commentVOPage, id, sort, order));
  }
}
