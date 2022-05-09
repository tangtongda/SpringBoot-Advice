package com.github.tangtongda.advice.starter.advice;

import com.github.tangtongda.advice.starter.annotation.exception.BaseException;
import com.github.tangtongda.advice.starter.annotation.exception.CommonErrorCode;
import com.github.tangtongda.advice.starter.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;
import java.sql.SQLException;

@RestControllerAdvice
@ResponseBody
public class ExceptionAdvice {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);

  @ExceptionHandler(SQLException.class)
  public BaseResponse<Object> SQLExceptionHandler(SQLException e) {
    LOGGER.error(e.getMessage(), e);
    return BaseResponse.fail(CommonErrorCode.EXCEPTION);
  }

  @ExceptionHandler(IOException.class)
  public BaseResponse<Object> IOExceptionHandler(IOException e) {
    LOGGER.error(e.getMessage(), e);
    return BaseResponse.fail(CommonErrorCode.EXCEPTION);
  }

  @ExceptionHandler(RuntimeException.class)
  public BaseResponse<Object> runtimeExceptionHandler(RuntimeException e) {
    LOGGER.error(e.getMessage(), e);
    return BaseResponse.fail(CommonErrorCode.EXCEPTION);
  }

  @ExceptionHandler({BaseException.class})
  public BaseResponse<Object> baseExceptionHandler(BaseException e) {
    LOGGER.error(e.getMessage(), e);
    return BaseResponse.fail(e.getCode(), e.getMessage());
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public BaseResponse<Object> noHandlerFoundExceptionHandler(NoHandlerFoundException e) {
    LOGGER.error(e.getMessage(), e);
    return BaseResponse.fail(CommonErrorCode.NOT_FOUND);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public BaseResponse<Object> httpRequestMethodNotSupportedExceptionHandler(
      HttpRequestMethodNotSupportedException e) {
    LOGGER.error(e.getMessage(), e);
    return BaseResponse.fail(CommonErrorCode.METHOD_NOT_ALLOWED);
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public BaseResponse<Object> httpMediaTypeNotSupportedExceptionHandler(
      HttpMediaTypeNotSupportedException e) {
    LOGGER.error(e.getMessage(), e);
    return BaseResponse.fail(CommonErrorCode.UNSUPPORTED_MEDIA_TYPE);
  }
}
