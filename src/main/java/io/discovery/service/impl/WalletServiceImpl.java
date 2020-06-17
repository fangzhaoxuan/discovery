package io.discovery.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.discovery.common.exception.SzException;
import io.discovery.constant.CommonConstant;
import io.discovery.dao.WalletDao;
import io.discovery.entity.CoinBalanceEntity;
import io.discovery.entity.UserEntity;
import io.discovery.form.CoinExchangeForm;
import io.discovery.form.CoinWithdrawlForm;
import io.discovery.service.WalletService;
import io.discovery.vo.CoinProportionlVO;
import io.discovery.vo.CoinWithdrawalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author fzx
 * @date 2018/10/08
 */
@Service
public class WalletServiceImpl extends ServiceImpl<WalletDao, CoinBalanceEntity> implements WalletService {
  @Autowired
  private WalletDao walletDao;

  @Override
  public CoinBalanceEntity getCoinInfo(UserEntity user) {
    CoinBalanceEntity coinBalanceEntity = new CoinBalanceEntity();
    // 获取当天的兑换比例
    BigDecimal szcToEth = walletDao.getProportion(CommonConstant.SZCID, CommonConstant.ETHID).getAmount();
    BigDecimal szcToCny = walletDao.getProportion(CommonConstant.SZCID, CommonConstant.CNYID).getAmount();
    BigDecimal ethToCny = walletDao.getProportion(CommonConstant.ETHID, CommonConstant.CNYID).getAmount();

    //获取账户余额并且计算人民币价值
    coinBalanceEntity.setBalanceSzc(user.getBalanceSzc());
    coinBalanceEntity.setSzcValue(user.getBalanceSzc().multiply(szcToCny));
    coinBalanceEntity.setBalanceEth(user.getBalanceEth());
    coinBalanceEntity.setEthValue(user.getBalanceEth().multiply(ethToCny));
    coinBalanceEntity.setTotalValue(user.getBalanceSzc().multiply(szcToCny).add(user.getBalanceEth().multiply(ethToCny)));
    return coinBalanceEntity;
  }

  @Override
  public List<CoinProportionlVO> getProportion(Long fromCoinId, Long toCoinId, String fromDate, String toDate) {
    return walletDao.getProportionHistory(fromCoinId, toCoinId, fromDate, toDate);
  }

  @Override
  public List<CoinWithdrawalVO> getWithdrawalHistory(UserEntity user) {
    return walletDao.getWithdrawalHistory(user.getId());
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Map<String, Object> withdrawCoin(UserEntity user, CoinWithdrawlForm coinWithdrawlForm) {
    //查询用户该币种余额
    BigDecimal balance = walletDao.queryBalanceById(user.getId(), coinWithdrawlForm.getCoinId());
    if (balance.compareTo(coinWithdrawlForm.getAmount()) < 0) {
      throw new SzException("当前币种余额不足");
    }
    Map<String, Object> map = new HashMap<>(2);
    //入库记录（状态在sql中设置为0）
    int referenceCount = walletDao.withdrawCoin(user.getId(), coinWithdrawlForm.getCoinId(), coinWithdrawlForm.getAmount());
    if (referenceCount == 1) {
      //申请提币后余额对应减少
      walletDao.updateBalance(user.getId(), coinWithdrawlForm.getCoinId(), coinWithdrawlForm.getAmount());
      map.put("code", 0);
      map.put("msg", "提币成功");
    } else {
      map.put("code", 1);
      map.put("msg", "提币失败");
    }
    return map;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Map<String, Object> exchangeCoin(UserEntity user, CoinExchangeForm coinExchangeForm) {
    //查询用户该币种余额
    BigDecimal balance = walletDao.queryBalanceById(user.getId(), coinExchangeForm.getFromCoinId());
    if (balance.compareTo(coinExchangeForm.getFromAmount()) < 0) {
      throw new SzException("当前币种余额不足");
    }
    //去数据库获取最近的汇率，计算toAmount，忽略传进来的toAmount
    BigDecimal proportion = walletDao.getProportion(coinExchangeForm.getFromCoinId(), coinExchangeForm.getToCoinId()).getAmount();
    coinExchangeForm.setToAmount(coinExchangeForm.getFromAmount().multiply(proportion));
    Map<String, Object> map = new HashMap<>(2);
    int referenceCount = walletDao.exchangeCoin(user, coinExchangeForm);
    if (referenceCount == 1) {
      //申请兑币后余额对应减少
      walletDao.updateBalance(user.getId(), coinExchangeForm.getFromCoinId(), coinExchangeForm.getFromAmount());
      map.put("code", 0);
      map.put("msg", "兑币成功");
    } else {
      map.put("code", 1);
      map.put("msg", "兑币失败");
    }
    return map;
  }
}
