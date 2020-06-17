package io.discovery.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.discovery.entity.CoinBalanceEntity;
import io.discovery.entity.UserEntity;
import io.discovery.form.CoinExchangeForm;
import io.discovery.form.CoinWithdrawlForm;
import io.discovery.vo.CoinProportionlVO;
import io.discovery.vo.CoinWithdrawalVO;

import java.util.List;
import java.util.Map;

/**
 * 钱包
 *
 * @author fzx
 * @date 2018/10/08
 */
public interface WalletService extends IService<CoinBalanceEntity> {
  /**
   * 获取登陆用户的余额信息
   *
   * @param user UserEntity user
   * @return 用户余额信息
   */
  CoinBalanceEntity getCoinInfo(UserEntity user);

  /**
   * 获取币价比率
   *
   * @param fromCoinId 基础币ID
   * @param toCoinId   兑换币ID
   * @param fromDate   起始日期
   * @param toDate     结束日期
   * @return 兑换比例列表
   */
  List<CoinProportionlVO> getProportion(Long fromCoinId, Long toCoinId, String fromDate, String toDate);

  /**
   * 历史提币信息
   *
   * @param user 当前登陆用户
   * @return
   */
  List<CoinWithdrawalVO> getWithdrawalHistory(UserEntity user);

  /**
   * 提币申请
   *
   * @param user              当前登陆用户
   * @param coinWithdrawlForm 提币信息表单
   * @return
   */
  Map<String, Object> withdrawCoin(UserEntity user, CoinWithdrawlForm coinWithdrawlForm);

  /**
   * 兑币申请
   *
   * @param user             当前登陆用户
   * @param coinExchangeForm 提币信息表单
   * @return
   */
  Map<String, Object> exchangeCoin(UserEntity user, CoinExchangeForm coinExchangeForm);
}
