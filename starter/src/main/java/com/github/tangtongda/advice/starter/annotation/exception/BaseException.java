package com.github.tangtongda.advice.starter.annotation.exception;

public class BaseException extends RuntimeException {

  private Integer code;

  public BaseException() {}

  public static void exception(CommonErrorCode code) {
    throw new BaseException(code);
  }

  public static void exception(CommonErrorCode code, String msg) {
    throw new BaseException(code, msg);
  }

  public static void exception(Integer code, String msg) {
    throw new BaseException(code, msg);
  }

  public static void businessException(String msg) {
    throw new BaseException(CommonErrorCode.BUSINESS_ERROR, msg);
  }

  /**
   *
   * @param errorCode 异常枚举
   */
  public BaseException(CommonErrorCode errorCode) {
    super(errorCode.getMessage());
    this.code = errorCode.getCode();
  }

  /**
   * @param baseErrorCode
   * @param msg
   */
  public BaseException(CommonErrorCode baseErrorCode, String msg) {
    super(msg);
    this.code = baseErrorCode.getCode();
  }

  public BaseException(Integer code, String msg) {
    super(msg);
    this.code = code;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }
}
