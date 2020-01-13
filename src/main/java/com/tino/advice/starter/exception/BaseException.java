package com.tino.advice.starter.exception;

/**
 * 管理器基类
 *
 * @author tino
 * @date 2020/1/2
 */
public class BaseException extends RuntimeException {

    private Integer code;

    public BaseException() {
    }

    public static void exception(BaseErrorCode code) {
        throw new BaseException(code);
    }

    public static void exception(BaseErrorCode code, String msg) {
        throw new BaseException(code, msg);
    }

    public static void exception(Integer code, String msg) {
        throw new BaseException(code, msg);
    }

    public static void businessException(String msg) {
        throw new BaseException(BaseErrorCode.BUSINESS_ERROR, msg);
    }

    /**
     * 使用CommonErrorCode枚举传参
     *
     * @param errorCode 异常枚举
     */
    public BaseException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    /**
     * @param baseErrorCode
     * @param msg
     */
    public BaseException(BaseErrorCode baseErrorCode, String msg) {
        super(msg);
        this.code = baseErrorCode.getCode();
    }

    /**
     * 使用自定义消息
     *
     * @param code 值
     * @param msg  详情
     */
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
