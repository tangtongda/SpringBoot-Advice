package com.tino.advice.starter.advice;

import com.tino.advice.starter.exception.BaseErrorCode;
import com.tino.advice.starter.exception.BaseException;
import com.tino.advice.starter.exception.BaseResponse;
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

/**
 * 基础全局异常处理
 *
 * @author tino
 * @date 2020/1/2
 */
@RestControllerAdvice
@ResponseBody
public class BaseExceptionAdvice {

    private static Logger logger = LoggerFactory.getLogger(BaseExceptionAdvice.class);

    /**
     * 处理其他所以未知的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    public BaseResponse globalExceptionHandler(Exception e) {
        logger.error(e.getMessage(), e);
        return BaseResponse.fail(BaseErrorCode.EXCEPTION);
    }

    /**
     * 业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler({BaseException.class})
    public BaseResponse businessExceptionHandler(BaseException e) {
        logger.error(e.getMessage(), e);
        return BaseResponse.fail(e.getCode(), e.getMessage());
    }

    /**
     * 404 异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseResponse handlerNoHandlerFoundException(NoHandlerFoundException e) {
        logger.error(e.getMessage(), e);
        return BaseResponse.fail(BaseErrorCode.NOT_FOUND);
    }

    /**
     * 405 异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResponse handlerHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        logger.error(e.getMessage(), e);
        return BaseResponse.fail(BaseErrorCode.METHOD_NOT_ALLOWED);
    }

    /**
     * 415 异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public BaseResponse handlerHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException e) {
        logger.error(e.getMessage(), e);
        return BaseResponse.fail(BaseErrorCode.UNSUPPORTED_MEDIA_TYPE);
    }

}
