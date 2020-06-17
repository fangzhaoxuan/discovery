package io.discovery.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.discovery.constant.TravelsConstants;
import io.discovery.dao.CheckinDao;
import io.discovery.entity.BookingOrderEntity;
import io.discovery.entity.CheckinEntity;
import io.discovery.entity.HotelEntity;
import io.discovery.entity.UserEntity;
import io.discovery.service.CheckinService;
import io.discovery.vo.BookingOrderVO;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 入住模块实现类
 *
 * @author fzx
 * @date 2018/10/06
 */
@Service
public class CheckinServiceImpl extends ServiceImpl<CheckinDao, CheckinEntity> implements CheckinService {
  @Autowired
  private CheckinDao checkinDao;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Map<String, Object> checkIn(Long bookingOrderId, BigDecimal longitude, BigDecimal latitude) {
    Map<String, Object> map = new HashMap<String, Object>(2);
    try {
      //判断此订单有没有签到过
      int checkCount = checkinDao.queryCheckIn(bookingOrderId);
      if (checkCount > 0) {
        map.put("code", TravelsConstants.ERROR);
        map.put("msg", "此订单已签到");
        return map;
      }
      //获取酒店的坐标
      HotelEntity hotel = checkinDao.queryHotel(bookingOrderId);
      //计算用户与酒店的距离
      BigDecimal distance = hotel.distance(longitude, latitude);
      if (distance.compareTo(new BigDecimal(TravelsConstants.DISTANCE)) > 0) {
        map.put("code", TravelsConstants.ERROR);
        map.put("msg", "签到失败，未在指定酒店范围内");
      } else {
        //从订单表中查出奖励币种及奖励金额
        BookingOrderEntity order = checkinDao.queryRewardAmount(bookingOrderId);
        //往签到表中插入数据
        CheckinEntity checkIn = new CheckinEntity();
        checkIn.setBookingOrderId(bookingOrderId);
        checkIn.setRewardCoinId(order.getRewardCoinId());
        checkIn.setRewardAmount(order.getRewardAmount());
        checkIn.setState(0);
        checkinDao.checkInRecord(checkIn);
        //将订单表的签到状态改为1 已签到
        checkinDao.updateOrderCheck(bookingOrderId);
        map.put("code", TravelsConstants.SUCCESS);
        map.put("msg", "签到成功");
      }
    } catch (Exception e) {
      map.put("code", TravelsConstants.ERROR);
      map.put("msg", "签到失败");
      e.printStackTrace();
    }
    return map;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Map<String, Object> checkCoin(UserEntity user) {
    Map<String, Object> map = new HashMap<String, Object>(2);
    try {
      //查询出所有状态为未得币并且订单状态为STATE_ORDER_FINISH的记录
      List<CheckinEntity> checkinEntityList = checkinDao.queryCheckInRecord();
      //遍历修改用户表的余额并且将签到表的状态改为1
      for (int i = 0; i < checkinEntityList.size(); i++) {
        CheckinEntity checkinEntity = checkinEntityList.get(i);
        checkinDao.refreshUserCoin(checkinEntity.getRewardCoinId(), checkinEntity.getRewardAmount(), user.getId());
        checkinDao.refreshChechInRecord(checkinEntity);
      }
      map.put("code", TravelsConstants.SUCCESS);
      map.put("msg", "刷新得币状态完成");
      return map;
    } catch (Exception e) {
      map.put("code", TravelsConstants.ERROR);
      map.put("msg", "刷新得币状态失败");
      e.printStackTrace();
      return map;
    }
  }

  @Override
  public IPage<BookingOrderVO> queryList(Page page, Long id, String sort, String order) {
    IPage<BookingOrderVO> iPage = checkinDao.queryList(page, id, sort, order);
    List<BookingOrderVO> bookingOrderVOS = iPage.getRecords();
    for (int i = 0; i < bookingOrderVOS.size(); i++) {
      BookingOrderVO bookingOrderVO = bookingOrderVOS.get(i);
      String photos = bookingOrderVO.getHotelMainPhoto();
      JSONArray jsonArray = JSONArray.fromObject(photos);
      bookingOrderVO.setHotelMainPhoto(jsonArray.get(0).toString());
    }
    return iPage;
  }

  @Override
  public int queryCheckIn(Long bookingOrderId) {
    return checkinDao.queryCheckIn(bookingOrderId);
  }
}
