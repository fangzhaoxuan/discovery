package io.discovery.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.discovery.entity.CoinBalanceEntity;
import io.discovery.entity.CoinProportionEntity;
import io.discovery.entity.UserEntity;
import io.discovery.form.CoinExchangeForm;
import io.discovery.vo.CoinProportionlVO;
import io.discovery.vo.CoinWithdrawalVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户
 *
 * @author fzx
 * @date 2018/10/08
 */
@Service
public interface WalletDao extends BaseMapper<CoinBalanceEntity> {
  /**
   * 获取当天的兑换比例
   *
   * @param fromCoinId 币种ID
   * @param toCoinId   币种ID
   * @return CoinProportionEntity
   */
  CoinProportionEntity getProportion(@Param("fromCoinId") Long fromCoinId, @Param("toCoinId") Long toCoinId);

  /**
   * 获取币种ID
   *
   * @param coinSymbol 币种符号
   * @return 币种ID
   */
  Long getCoinId(@Param("coinSymbol") String coinSymbol);

  /**
   * 获取兑换比例
   *
   * @param fromCoinId 基础币金额  用什么币去兑换
   * @param toCoinId   兑换币金额  兑换成什么币
   * @param fromDate   起始日期
   * @param toDate     结束日期
   * @return
   */
  List<CoinProportionlVO> getProportionHistory(@Param("fromCoinId") Long fromCoinId, @Param("toCoinId") Long toCoinId, @Param("fromDate") String fromDate, @Param("toDate") String toDate);

  /**
   * 获取历史提币信息
   *
   * @param userId 当前登陆ID
   * @return 历史提币信息
   */
  List<CoinWithdrawalVO> getWithdrawalHistory(@Param("userID") Long userId);

  /**
   * 提币申请入库
   *
   * @param userId 当前登陆ID
   * @param coinId 提币币种ID
   * @param amount 提币金额
   * @return 历史提币信息
   */
  Integer withdrawCoin(@Param("userId") Long userId, @Param("coinId") Long coinId, @Param("amount") BigDecimal amount);

  /**
   * 兑币申请入库
   *
   * @param user             当前登陆用户
   * @param coinExchangeForm 兑币信息
   * @return 历史提币信息
   */
  Integer exchangeCoin(@Param("user") UserEntity user, @Param("c") CoinExchangeForm coinExchangeForm);

  /**
   * 根据币种获取余额
   *
   * @param userId 当前登陆用户ID
   * @param coinId 币种ID
   * @return 历史提币信息
   */
  BigDecimal queryBalanceById(@Param("userId") Long userId, @Param("coinId") Long coinId);

  /**
   * 根据提取金额修改账户余额
   *
   * @param userId 当前登陆用户ID
   * @param coinId 币种ID
   * @param amount 数额
   * @return 历史提币信息
   */
  void updateBalance(@Param("userId") Long userId, @Param("coinId") Long coinId, @Param("amount") BigDecimal amount);
}
