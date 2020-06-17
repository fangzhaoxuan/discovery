package io.discovery.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.discovery.annotation.Login;
import io.discovery.annotation.LoginUser;
import io.discovery.common.utils.R;
import io.discovery.entity.UserEntity;
import io.discovery.form.CheckinForm;
import io.discovery.service.CheckinService;
import io.discovery.vo.BookingOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @author fzx
 * @date 2018-10-06
 */
@RestController
@RequestMapping("/v1")
public class ApiTravelController {
  @Autowired
  private CheckinService checkinService;

  /**
   * 行程签到拿币
   */
  @Login
  @PostMapping("/travels/{bookingOrderId}/checkin")
  public R checkIn(@PathVariable("bookingOrderId") Long bookingOrderId, @RequestBody @Validated CheckinForm checkinForm) {
    Map<String, Object> map = checkinService.checkIn(bookingOrderId, checkinForm.getLongitude(), checkinForm.getLatitude());
    return R.ok(map);
  }

  /**
   * 刷新得币接口
   */
  @Login
  @PostMapping("/travels/checkcoin")
  public R checkcoin(@LoginUser UserEntity user) {
    Map<String, Object> map = checkinService.checkCoin(user);
    return R.ok(map);
  }

  /**
   * 获取行程列表
   */
  @Login
  @GetMapping("/travels")
  public R list(@LoginUser UserEntity user, Integer page, Integer limit, @RequestParam(value = "sort", defaultValue = "booking_order.id") String sort, @RequestParam(value = "order", defaultValue = "desc") String order) {
    Page<BookingOrderVO> p = new Page<>(page, limit);
    IPage<BookingOrderVO> bookingOrderVOIPage = checkinService.queryList(p, user.getId(), sort, order);
    return R.data(bookingOrderVOIPage);
  }
}
