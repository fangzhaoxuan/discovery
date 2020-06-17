package io.discovery.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参数验证异常类
 *
 * @author fzx
 * @date 2018/11/22
 */
@Slf4j
@RestController
@ControllerAdvice
public class DefaultExceptionHandler {


  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, Object> validException(HttpServletRequest request, Exception ex) {
    MethodArgumentNotValidException c = (MethodArgumentNotValidException) ex;
    List<ObjectError> errors = c.getBindingResult().getAllErrors();
    StringBuffer errorMsg = new StringBuffer();
    for (ObjectError error : errors) {
      errorMsg.append(error.getDefaultMessage()).append(";");
    }
    Map<String, Object> map = new HashMap<>(2);
    map.put("code", 1);
    map.put("msg", errorMsg);
    return map;
  }
}
