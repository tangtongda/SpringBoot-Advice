package com.tino.advice.starter.exception;

/**
 * 基础异常类
 *
 * @author tino
 * @date 2020/1/2
 */
public enum BaseErrorCode {

    NOT_FOUND(404, "找不到该路径"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持该媒体类型"),
    EXCEPTION(500, "服务器开小差了，请稍后再试"),
    TRAFFIC_LIMITING(429, "网络异常"),
    API_GATEWAY_ERROR(430, "网络繁忙，请稍后再试"),
    BUSINESS_ERROR(4000, "业务异常"),
    PARAM_ERROR(4001, "参数异常"),
    SYSTEM_ERROR(4002, "系统异常"),
    DATA_ERROR(4003, "数据异常");

    private Integer code;
    private String message;

    BaseErrorCode(Integer code, String message) {
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