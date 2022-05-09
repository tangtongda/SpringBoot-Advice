package com.github.tangtongda.advice.test.exception;

import com.github.tangtongda.advice.starter.annotation.exception.BaseException;

/**
 * @author <a href="mailto:tangtongda@gmail.com">Tino_Tang</a>
 * @version 1.0.0-5/9/2022
 */
public class BizException extends RuntimeException {

  public BizException(Integer code, String msg) {
    throw new BaseException(code, msg);
  }

  public BizException(BizExceptionEnum bizExceptionEnum) {
    throw new BaseException(bizExceptionEnum.getCode(), bizExceptionEnum.getMessage());
  }
}
