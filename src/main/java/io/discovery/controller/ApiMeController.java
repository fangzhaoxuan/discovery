package io.discovery.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.discovery.annotation.Login;
import io.discovery.annotation.LoginUser;
import io.discovery.common.utils.R;
import io.discovery.entity.UserEntity;
import io.discovery.form.UpdateUserForm;
import io.discovery.service.UserService;
import io.discovery.vo.OrderVO;
import io.discovery.vo.UserVO;
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
public class ApiMeController {
  @Autowired
  private UserService userService;

  /**
   * 获取个人信息
   */
  @Login
  @GetMapping("me")
  public R getUserInfo(@LoginUser UserEntity user) {
    UserVO userVO = new UserVO();
    userVO.setName(user.getName());
    userVO.setMobile(user.getMobile());
    userVO.setBalanceSzc(user.getBalanceSzc());
    userVO.setBalanceEth(user.getBalanceEth());
    userVO.setInvited(user.getInvited());
    userVO.setGmtCreate(user.getGmtCreate());
    if (user.getOpenId() != null && !"".equals(user.getOpenId())) {
      userVO.setOpenId(user.getOpenId());
    }
    userVO.setInvitationCode(user.getInvitationCode());
    userVO.setPasswordSet((user.getPassword() == null || "".equals(user.getPassword())) ? false : true);
    return R.data(userVO);
  }

  /**
   * 修改个人信息
   */
  @Login
  @PostMapping("me/update")
  public R updateUser(@LoginUser UserEntity user, @RequestBody @Validated UpdateUserForm updateUserForm) {
    Map<String, Object> map = userService.updateUser(user, updateUserForm);
    return R.ok(map);
  }

  /**
   * 登录用户的订单获取
   */
  @Login
  @GetMapping("orders")
  public R getOrders(@LoginUser UserEntity user, Integer page, Integer limit, @RequestParam(value = "sort", defaultValue = "booking_order.id") String sort, @RequestParam(value = "order", defaultValue = "desc") String order, String state) {
    Page<OrderVO> p = new Page<>(page, limit);
    IPage<OrderVO> returnPage = userService.getOrders(user, p, sort, order, state);
    return R.data(returnPage);
  }

  /**
   * 获取订单详情
   */
  @Login
  @GetMapping("orders/{id}")
  public R getOrderDetail(@LoginUser UserEntity userEntity, @PathVariable("id") Long id) {
    return R.data(userService.getOrderDetail(userEntity, id));
  }

  /**
   * 订单软删除
   */
  @Login
  @PostMapping("orders/{id}/delete")
  public R deleteOrder(@PathVariable("id") Long id) {
    return R.data(userService.deleteOrder(id));
  }

}
