package io.discovery.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.discovery.annotation.Login;
import io.discovery.annotation.LoginUser;
import io.discovery.common.utils.R;
import io.discovery.entity.CoinBalanceEntity;
import io.discovery.entity.UserEntity;
import io.discovery.form.CoinExchangeForm;
import io.discovery.form.CoinWithdrawlForm;
import io.discovery.service.WalletService;
import io.discovery.vo.CoinProportionlVO;
import io.discovery.vo.CoinWithdrawalVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 钱包接口
 *
 * @author fzx
 * @date 2018/10/06
 */
@RestController
@RequestMapping("/v1")
@Api(tags = "钱包接口")
public class ApiWalletController {
  @Autowired
  private WalletService walletService;

  @Login
  @GetMapping("wallets")
  @ApiOperation("获取用户闪住币以太币余额")
  public R getCoinInfo(@LoginUser UserEntity user) {
    CoinBalanceEntity coinBalanceEntity = walletService.getCoinInfo(user);
    return R.data(coinBalanceEntity);
  }

  @Login
  @GetMapping("/wallets/exchange-rate")
  @ApiOperation("获取币价比率")
  public R getProportionHistory(Long fromCoinId, Long toCoinId, String from, String to) {
    List<CoinProportionlVO> coinProportionlVOS = walletService.getProportion(fromCoinId, toCoinId, from, to);
    return R.data(coinProportionlVOS);
  }

  @Login
  @GetMapping("/wallets/withdrawal-history")
  @ApiOperation("历史提币信息")
  public R getWithdrawalHistory(@LoginUser UserEntity user) {
    List<CoinWithdrawalVO> coinWithdrawalVOS = walletService.getWithdrawalHistory(user);
    return R.data(coinWithdrawalVOS);
  }

  @Login
  @PostMapping("/wallets/withdrawal")
  @ApiOperation("发起提币申请")
  public R withDrawCoin(@LoginUser UserEntity user, @RequestBody @Validated CoinWithdrawlForm coinWithdrawlForm) {
    return R.ok(walletService.withdrawCoin(user, coinWithdrawlForm));
  }

  @Login
  @PostMapping("/wallets/exchange")
  @ApiOperation("发起兑币申请")
  public R exchangeCoin(@LoginUser UserEntity user, @RequestBody @Validated CoinExchangeForm coinExchangeForm) {
    return R.ok(walletService.exchangeCoin(user, coinExchangeForm));
  }
}
