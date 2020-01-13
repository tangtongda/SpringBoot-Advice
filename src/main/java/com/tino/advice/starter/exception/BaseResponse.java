package com.tino.advice.starter.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;

/**
 * 基础返回封装类
 *
 * @author tino
 * @date 2020/1/2
 */
public class BaseResponse<T> implements Serializable {

    public BaseResponse() {
    }

    public BaseResponse(Boolean success, T data, Integer code, String message) {
        this.success = success;
        this.data = data;
        this.code = code;
        this.message = message;
    }

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 成功数据
     */
    private T data;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误描述
     */
    private String message;

    public static BaseResponse succeed() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.success = true;
        baseResponse.code = 200;
        baseResponse.data = null;
        return baseResponse;
    }

    public static BaseResponse succeed(Object data) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.success = true;
        baseResponse.code = 200;
        baseResponse.setData(data);
        return baseResponse;
    }

    public static BaseResponse fail(Integer code, String msg) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.success = false;
        baseResponse.code = code;
        baseResponse.message = msg;
        return baseResponse;
    }

    public static BaseResponse fail(Integer code, String msg, Object data) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.success = false;
        baseResponse.code = code;
        baseResponse.message = msg;
        baseResponse.setData(data);
        return baseResponse;
    }

    public static <T extends BaseErrorCode> BaseResponse fail(T resultEnum) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.success = false;
        baseResponse.code = resultEnum.getCode();
        baseResponse.message = resultEnum.getMessage();
        return baseResponse;
    }

    /**
     * 获取 json
     */
    public String buildResultJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", this.success);
        jsonObject.put("code", this.code);
        jsonObject.put("msg", this.message);
        jsonObject.put("data", this.data);
        return JSON.toJSONString(jsonObject, SerializerFeature.DisableCircularReferenceDetect);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
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
