package io.discovery.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.discovery.entity.BookingOrderEntity;
import io.discovery.entity.UserEntity;
import io.discovery.form.LoginForm;
import io.discovery.form.UpdateUserForm;
import io.discovery.form.VerificationCodeForm;
import io.discovery.vo.LoginTokenVO;
import io.discovery.vo.OrderVO;

import java.util.Map;

/**
 * 用户
 *
 * @author fzx
 * @date 2018-10-18
 */
public interface UserService extends IService<UserEntity> {

  /**
   * 用户登录
   *
   * @param form 登录表单
   * @return 返回登录信息
   */
  LoginTokenVO login(LoginForm form);

  /**
   * 用户首次登陆设置密码
   *
   * @param user     当前登陆用户
   * @param password 首次设置的密码
   * @return 返回数据库修改条数
   */
  int setPassword(UserEntity user, String password);

  /**
   * 短信验证码发送
   *
   * @param verificationCodeForm 短信表单
   * @return 是否发送成功
   */
  Map<String, Object> sendVerificationCode(VerificationCodeForm verificationCodeForm);

  /**
   * 修改用户信息
   *
   * @param user           UserEntity user
   * @param updateUserForm UpdateUserForm   updateUserForm
   * @return 是否修改成功
   */
  Map<String, Object> updateUser(UserEntity user, UpdateUserForm updateUserForm);

  /**
   * 获取当前用户的订单列表
   *
   * @param user  登录用户
   * @param p     页面对象
   * @param sort  排序字段
   * @param order 正序或逆序
   * @param state 状态
   * @return 存储返回字段的页面对象
   */
  IPage<OrderVO> getOrders(UserEntity user, Page p, String sort, String order, String state);

  /**
   * 根据订单ID获取订单详情
   *
   * @param userEntity 当前登录用户
   * @param id         订单ID
   * @return 订单
   */
  BookingOrderEntity getOrderDetail(UserEntity userEntity, Long id);

  /**
   * 订单软删除
   *
   * @param id 订单ID
   * @return 订单
   */
  Map<String, Object> deleteOrder(Long id);
}
