package com.github.tangtongda.advice.starter.response;

import com.github.tangtongda.advice.starter.annotation.exception.CommonErrorCode;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {

  public BaseResponse() {}

  public BaseResponse(T data, Integer code, String message) {
    this.data = data;
    this.code = code;
    this.message = message;
  }

  /** response data */
  private T data;

  /** status code */
  private Integer code;

  /** error message */
  private String message;

  public static BaseResponse<Object> succeed(Object data) {
    BaseResponse<Object> baseResponse = new BaseResponse<>();
    baseResponse.code = 200;
    baseResponse.setData(data);
    return baseResponse;
  }

  public static BaseResponse<Object> fail(Integer code, String msg) {
    BaseResponse<Object> baseResponse = new BaseResponse<>();
    baseResponse.code = code;
    baseResponse.message = msg;
    return baseResponse;
  }

  public static <T extends CommonErrorCode> BaseResponse<Object> fail(T resultEnum) {
    BaseResponse<Object> baseResponse = new BaseResponse<>();
    baseResponse.code = resultEnum.getCode();
    baseResponse.message = resultEnum.getMessage();
    return baseResponse;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
