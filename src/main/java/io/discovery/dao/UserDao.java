package io.discovery.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.discovery.entity.BookingOrderEntity;
import io.discovery.entity.UserEntity;
import io.discovery.vo.OrderVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * 用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:22:06
 */
@Service
public interface UserDao extends BaseMapper<UserEntity> {
  /**
   * 根据手机号查询用户
   *
   * @param mobile 手机号
   * @return UserEntity
   */
  UserEntity selectUserByPhone(@Param("mobile") String mobile);

  /**
   * 邀请奖励
   *
   * @param id 邀请者ID
   * @return
   */
  void rewardInvitation(@Param("id") Long id);

  /**
   * 新增用户
   *
   * @param userEntity UserEntity userEntity
   * @return
   */
  void insertUser(UserEntity userEntity);

  /**
   * 查询ID
   *
   * @param mobile 手机号
   * @return 用户ID
   */
  Long selectId(@Param("mobile") String mobile);

  /**
   * 设置密码
   *
   * @param id       Long id
   * @param password String password
   * @return 数据库受影响条数
   */
  int setPassword(@Param("id") Long id, @Param("password") String password);

  /**
   * 邀请记录
   *
   * @param loginId  Long loginId
   * @param inviteId Long inviteId
   * @return
   */
  void invitationRecord(@Param("loginId") Long loginId, @Param("inviteId") Long inviteId);

  /**
   * 登录记录
   *
   * @param id        Long id
   * @param loginType String loginType
   * @return
   */
  void loginRecord(@Param("id") Long id, @Param("loginType") Integer loginType);

  /**
   * 修改用户
   *
   * @param id       Long id
   * @param name     String name
   * @param password String password
   * @param mobile   String mobile
   * @return 数据库受影响条数
   */
  int updateUser(@Param("id") Long id, @Param("name") String name, @Param("password") String password, @Param("mobile") String mobile);

  /**
   * 查询该用户成功邀请次数
   *
   * @param id Long id
   * @return 邀请次数
   */
  int queryInviteCount(@Param("id") Long id);

  /**
   * 获取当前用户的订单列表
   *
   * @param p      页面对象
   * @param userId 预订者ID
   * @param sort   排序字段
   * @param order  正序或反序
   * @param state  状态
   * @return 邀请次数
   */
  IPage<OrderVO> getOrders(Page p, @Param("userId") Long userId, @Param("sort") String sort, @Param("order") String order, @Param("state") String state);

  /**
   * 查询订单详情
   *
   * @param userId 当前用户ID
   * @param id     Long id
   * @return 邀请次数
   */
  BookingOrderEntity getOrderDetail(@Param("userId") Long userId, @Param("id") Long id);

  /**
   * 入库openid
   *
   * @param id     当前用户ID
   * @param openid openid
   * @return
   */
  void setOpenid(@Param("id") Long id, @Param("openid") String openid);

  /**
   * 获取openid
   *
   * @param id 当前用户ID
   * @return
   */
  String getOpenid(@Param("id") Long id);

  /**
   * 订单软删除
   *
   * @param id 订单ID
   * @return
   */
  Integer deleteOrder(@Param("id") Long id);

  /**
   * 查询订单是否已经被删除
   *
   * @param id 订单ID
   * @return
   */
  Boolean getOrderDelete(@Param("id") Long id);
}
