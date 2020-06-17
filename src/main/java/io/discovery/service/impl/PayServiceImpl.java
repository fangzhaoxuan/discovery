package io.discovery.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.discovery.common.exception.SzException;
import io.discovery.config.AlipayConfig;
import io.discovery.config.WechatPayConfig;
import io.discovery.constant.BookingConst;
import io.discovery.constant.CommonConstant;
import io.discovery.dao.PayDao;
import io.discovery.dao.UserDao;
import io.discovery.entity.BookingOrderEntity;
import io.discovery.entity.UserEntity;
import io.discovery.service.PayService;
import io.discovery.util.HttpUtil;
import io.discovery.util.PayCommonUtil;
import io.discovery.util.RandomUtil;
import io.discovery.util.XmlUtil;
import net.sf.json.JSONObject;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


/**
 * @author fzx
 * @date 2018/10/08
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PayServiceImpl extends ServiceImpl<PayDao, UserEntity> implements PayService {
  @Autowired
  private PayDao payDao;
  @Autowired
  private UserDao userDao;
  @Autowired
  private AlipayConfig alipayConfig;
  @Autowired
  private WechatPayConfig wechatPayConfig;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Map<String, Object> payBySzc(UserEntity user, Long id, BigDecimal amount) {
    Map<String, Object> map = new HashMap<>(2);
    BookingOrderEntity bookingOrderEntity = payDao.getOrder(id);
    //判断订单是否超时
    Date now = new Date();
    Date gmtCreate = bookingOrderEntity.getGmtCreate();
    Long intervalSeconds = now.getTime() - gmtCreate.getTime();
    if (intervalSeconds > CommonConstant.PAYMENTLIMIT) {
      map.put("code", 1);
      map.put("msg", "订单已超时");
      //将订单状态改为关闭
      payDao.updateOrderState(id, BookingConst.STATE_ORDER_CLOSE.getCode());
      return map;
    }
    //判断传入的amount是否与订单中的抵扣值相同
    if (amount.compareTo(bookingOrderEntity.getDiscountAmount()) != 0) {
      throw new SzException("支付金额与订单信息不符");
    }
    //扣除用户余额
    int referenceCount = payDao.payBySzc(user.getId(), amount);
    if (referenceCount == 1) {
      map.put("code", 0);
      map.put("msg", "支付成功");
      //修改订单状态为预定成功
      payDao.updateOrderState(id, BookingConst.STATE_BOOKING.getCode());
    }
    return map;
  }

  @Override
  public void alipay(UserEntity userEntity, HttpServletResponse response, Long id) {
    try {
      //根据订单ID获取订单信息用于请求支付宝接口的数据封装
      BookingOrderEntity bookingOrderEntity = userDao.getOrderDetail(userEntity.getId(), id);
      AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getGatewayUrl(), alipayConfig.getAppId(),
          alipayConfig.getMerchantPrivateKey(), "json", AlipayConfig.charset, alipayConfig.getAlipayPublicKey(),
          AlipayConfig.signType);
      //创建API对应的request
      AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
      //在公共参数中设置回跳和通知地址
      alipayRequest.setReturnUrl(alipayConfig.getReturnUrl().replace("ID", bookingOrderEntity.getId().toString()));
      alipayRequest.setNotifyUrl(alipayConfig.getNotifyUrl());
      //填充业务参数
      String subject = bookingOrderEntity.getSnapshotHotelName() + "-" + bookingOrderEntity.getBookingNo();
      alipayRequest.setBizContent("{" +
          "    \"out_trade_no\":" + bookingOrderEntity.getBookingNo() + "," +
          "    \"total_amount\":" + bookingOrderEntity.getPayPrice() + "," +
          "    \"subject\":\"" + subject + "\"," +
          "    \"product_code\":\"QUICK_WAP_WAY\"" +
          "  }");
      //调用SDK生成表单
      String form = alipayClient.pageExecute(alipayRequest).getBody();
      response.setContentType("text/html;charset=utf-8");
      //直接将完整的表单html输出到页面
      response.getWriter().write(form);
    } catch (AlipayApiException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void notify(HttpServletRequest request, HttpServletResponse response) {
    try {
      //获取支付宝POST过来反馈信息
      Map<String, String> params = new HashMap<>(30);
      Map requestParams = request.getParameterMap();
      for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
        String name = (String) iter.next();
        String[] values = (String[]) requestParams.get(name);
        String valueStr = "";
        for (int i = 0; i < values.length; i++) {
          valueStr = (i == values.length - 1) ? valueStr + values[i]
              : valueStr + values[i] + ",";
        }
        params.put(name, valueStr);
      }
      // 商户订单号
      String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
      // 支付宝交易号
      String tradeNo = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
      // 付款金额
      String totalAmount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
      // 交易状态
      String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
      // APPID
      String appId = new String(request.getParameter("app_id").getBytes("ISO-8859-1"), "UTF-8");
      //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
      boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayConfig.getAlipayPublicKey(), AlipayConfig.charset, AlipayConfig.signType);
      Boolean isPaysuccess = payDao.getPayStatus(outTradeNo);
      if (isPaysuccess != null) {
        response.getWriter().write("success");
      } else {
        //业务处理
        if (signVerified) {
          //根据返回的订单号查询支付金额用于支付金额验证
          BigDecimal payPrice = payDao.getPayPrice(outTradeNo);
          //普通即时到帐状态下交易成功状态
          String normalTradeStatus = "TRADE_FINISHED";
          //高级即时到帐状态下易成功状态
          String advancedTradeStatus = "TRADE_SUCCESS";

          //支付金额、订单完成标识、appid验证
          Boolean priceFlag = new BigDecimal(totalAmount).compareTo(payPrice) == 0;
          Boolean tradeFlag = normalTradeStatus.equals(tradeStatus) || advancedTradeStatus.equals(tradeStatus);
          Boolean appidFlag = alipayConfig.getAppId().equals(appId);
          if (priceFlag && tradeFlag && appidFlag) {
            //将订单状态改为预定中（支付成功）
            payDao.updateOrderStateByNo(outTradeNo, BookingConst.STATE_BOOKING.getCode(), CommonConstant.ALIPAY, CommonConstant.PAYSUCCESS);
            response.getWriter().write("success");
          } else {
            //将订单状态改为支付失败
            payDao.updateOrderStateByNo(outTradeNo, BookingConst.STATE_ORDER_PAY_FAIL.getCode(), CommonConstant.ALIPAY, CommonConstant.PAYFAIL);
            response.getWriter().write("success");
          }
        } else {
          response.getWriter().write("failure");
        }
      }
    } catch (AlipayApiException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String setOpenid(Long id, String code) {
    String url = wechatPayConfig.getClientAccessTokenUrl().replace("CODE", code);
    String resultJson = HttpUtil.postData(url, null, null);
    JSONObject jsonObject = JSONObject.fromObject(resultJson);
    String openid = String.valueOf(jsonObject.get("openid"));
    userDao.setOpenid(id, openid);
    return openid;
  }

  @Override
  public Map<Object, Object> wechatPay(HttpServletRequest request, HttpServletResponse response, Long userId, Long id) {
    SortedMap<Object, Object> param = new TreeMap<>();
    try {
      //从数据库中获取openid
      String openid = userDao.getOpenid(userId);
      //根据订单ID获取订单信息用于请求支付宝接口的数据封装
      BookingOrderEntity bookingOrderEntity = userDao.getOrderDetail(userId, id);
      // 设置package订单参数
      SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
      packageParams.put("appid", wechatPayConfig.getAppId());
      packageParams.put("mch_id", wechatPayConfig.getMchId());
      // 生成签名的时候需要你自己设置随机字符串
      packageParams.put("nonce_str", RandomUtil.generateStr(32));
      packageParams.put("out_trade_no", bookingOrderEntity.getBookingNo());
      packageParams.put("openid", openid);
      //微信api要求传入金额单位为分
      packageParams.put("total_fee", bookingOrderEntity.getPayPrice().setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString());
      packageParams.put("spbill_create_ip", HttpUtil.getRealIp(request));
      packageParams.put("notify_url", wechatPayConfig.getNotifyUrl());
      packageParams.put("trade_type", wechatPayConfig.getTradeType());
      packageParams.put("body", wechatPayConfig.getBody());
      //生成签名
      String sign = PayCommonUtil.createSign("UTF-8", packageParams, wechatPayConfig.getAppKey());
      packageParams.put("sign", sign);
      String requestXML = PayCommonUtil.getRequestXml(packageParams);
      //请求统一下单接口（主要为获取prepay_id这个参数）
      String resXml = HttpUtil.postData(wechatPayConfig.getUfdorUrl(), requestXML, null);
      Map<String, Object> map = XmlUtil.doXMLParse(resXml);
      //判断请求结果 若returnCode和resultCode均为success 则按新参数重新生成签名返回前端以供前端请求支付接口
      String mark = "SUCCESS";
      String returnCode = "return_code";
      String resultCode = "result_code";
      if (mark.equals(map.get(returnCode)) && mark.equals(map.get(resultCode))) {
        param.put("appId", wechatPayConfig.getAppId());
        param.put("nonceStr", RandomUtil.generateStr(32));
        param.put("package", "prepay_id=" + map.get("prepay_id"));
        param.put("signType", "Md5");
        param.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
        //以新参数生成新的签名
        String paySign = PayCommonUtil.createSign("UTF-8", param, wechatPayConfig.getAppKey());
        param.put("paySign", paySign);
      } else {
        throw new SzException("统一下单出错！");
      }
    } catch (JDOMException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return param;
  }

  @Override
  public void wechatPayNotify(HttpServletRequest request, HttpServletResponse response) {
    SortedMap<Object, Object> map = new TreeMap<>();
    try {
      InputStream inStream = request.getInputStream();
      ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      int len;
      while ((len = inStream.read(buffer)) != -1) {
        outSteam.write(buffer, 0, len);
      }
      outSteam.close();
      inStream.close();

      String resultStr = new String(outSteam.toByteArray(), "utf-8");
      Map<String, String> resultMap = XmlUtil.doXMLParse(resultStr);
      PayCommonUtil.isTenpaySign(resultMap, wechatPayConfig.getAppKey());
      String outTradeNo = resultMap.get("out_trade_no");
      String resultCode = resultMap.get("result_code");
      String returnCode = resultMap.get("return_code");
      //先查询数据库该订单支付状态 若已支付则直接返回SUCCESS给微信
      Boolean isPaysuccess = payDao.getPayStatus(outTradeNo);
      if (isPaysuccess != null) {
        map.put("return_code", "SUCCESS");
        map.put("return_msg", "OK");
        response.getWriter().write(PayCommonUtil.getRequestXml(map));
      } else {
        //验签
        if (PayCommonUtil.isTenpaySign(resultMap, wechatPayConfig.getAppKey())) {
          //通知微信.异步确认成功
          map.put("return_code", "SUCCESS");
          map.put("return_msg", "OK");
          response.getWriter().write(PayCommonUtil.getRequestXml(map));
          String mark = "SUCCESS";
          if (mark.equals(resultCode) && mark.equals(returnCode)) {
            //将订单状态改为预定中（支付成功）
            payDao.updateOrderStateByNo(outTradeNo, BookingConst.STATE_BOOKING.getCode(), CommonConstant.WECHATPAY, CommonConstant.PAYSUCCESS);
          } else {
            //将订单状态改为支付失败
            payDao.updateOrderStateByNo(outTradeNo, BookingConst.STATE_ORDER_PAY_FAIL.getCode(), CommonConstant.WECHATPAY, CommonConstant.PAYFAIL);
            map.put("return_code", "SUCCESS");
            map.put("return_msg", "OK");
            response.getWriter().write(PayCommonUtil.getRequestXml(map));
          }
        } else {
          //通知微信.异步确认失败
          map.put("FAIL", "ERROR");
          response.getWriter().write(PayCommonUtil.getRequestXml(map));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (JDOMException e) {
      e.printStackTrace();
    }
  }
}
