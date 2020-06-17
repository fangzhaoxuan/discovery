package io.discovery.exception;

import io.discovery.common.exception.SzException;
import io.discovery.common.exception.UnauthorizedException;
import io.discovery.common.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception handlers
 *
 * @author chenwei <acee06.weichen@gmail.com>
 * @date 2018-10-16
 */
@RestControllerAdvice
public class SzExceptionHandler {
  private Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * 处理自定义异常
   */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(SzException.class)
  public R handleSzException(SzException e) {
    R r = new R();
    r.put("code", e.getCode());
    r.put("msg", e.getMessage());

    return r;
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(UnauthorizedException.class)
  public R handleUnauthorizedException(UnauthorizedException e) {
    return new R(e.getCode(), e.getMessage());
  }

  @ExceptionHandler(DuplicateKeyException.class)
  public R handleDuplicateKeyException(DuplicateKeyException e) {
    logger.error(e.getMessage(), e);
    return R.error("数据库中已存在该记录");
  }

  @ExceptionHandler(Exception.class)
  public R handleException(Exception e) {
    logger.error(e.getMessage(), e);
    return R.error();
  }
}
