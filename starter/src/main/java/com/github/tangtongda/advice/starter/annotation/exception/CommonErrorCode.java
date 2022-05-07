package com.github.tangtongda.advice.starter.annotation.exception;

public enum CommonErrorCode {
  NOT_FOUND(404, "Not found."),
  METHOD_NOT_ALLOWED(405, "Request method is not permitted."),
  UNSUPPORTED_MEDIA_TYPE(415, "Media type was not supported."),
  EXCEPTION(500, "System internal error."),
  TRAFFIC_LIMITING(429, "Network traffic limiting."),
  API_GATEWAY_ERROR(430, "API gateway error."),
  BUSINESS_ERROR(4000, "Business exception."),
  PARAM_ERROR(4001, "Parameter exception."),
  SYSTEM_ERROR(4002, "Business process handler error."),
  DATA_ERROR(4003, "Data missing error.");

  private final Integer code;
  private final String message;

  CommonErrorCode(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public Integer getCode() {
    return code;
  }
}
