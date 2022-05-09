package com.github.tangtongda.advice.test.exception;

/**
 * @author <a href="mailto:tangtongda@gmail.com">Tino_Tang</a>
 * @version 1.0.0-5/9/2022
 */
public enum BizExceptionEnum {
  USER_AUTH_ERROR(4001, "User authorization error."),
  ;

  private int code;
  private String message;

  BizExceptionEnum(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
