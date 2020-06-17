package io.discovery.util;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 生成订单号
 * 定义订单号规则v1
 * 下单渠道+业务类型+时间信息+自增
 * 下单渠道：web=1
 * 业务类型：酒店预订=1
 * 时间信息：秒级时间戳，如java的 (System.currentTimeMillis()/1000 + "").substring(1)
 * 自增：3位自增，可采用redis的incr，从redis取到值后对1000取余，如果值不存在初始可设为111
 * 总位数：1位+1位+9位+3位=14位
 *
 * @author fzx
 * @date 2018/11/2
 */
@Component
public class BookingOrderNoCreator {
  @Resource
  private StringRedisTemplate autowireRedisTemplate;

  private static RedisTemplate redisTemplate;

  private static final String CHANNEL = "1";

  private static final String BUSINESSTYPE = "1";


  @PostConstruct
  public void init() {
    redisTemplate = autowireRedisTemplate;
  }

  public static String createBookingOrderNo() {
    StringBuilder bookingOrderNo = new StringBuilder();
    RedisAtomicLong redisAtomicLong = new RedisAtomicLong("bookingOrderNo", redisTemplate.getConnectionFactory());
    Long currentValue = redisAtomicLong.get();
    if (currentValue == 0) {
      redisAtomicLong.set(111L);
    }
    Long incrementValue = redisAtomicLong.incrementAndGet() % 1000;
    bookingOrderNo.append(CHANNEL).append(BUSINESSTYPE).append((System.currentTimeMillis() / 1000 + "").substring(1)).append(incrementValue);
    return bookingOrderNo.toString();
  }


}
