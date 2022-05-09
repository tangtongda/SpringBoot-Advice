package com.github.tangtongda.advice.test.controller;

import com.github.tangtongda.advice.starter.annotation.exception.BaseException;
import com.github.tangtongda.advice.starter.annotation.exception.CommonErrorCode;
import com.github.tangtongda.advice.test.entity.UserEntity;
import com.github.tangtongda.advice.test.exception.BizException;
import com.github.tangtongda.advice.test.exception.BizExceptionEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:tangtongda@gmail.com">Tino_Tang</a>
 * @version 1.0.0-5/6/2022
 */
@RestController
@RequestMapping("/test")
public class DemoController {

  @GetMapping("/get")
  public String get() {
    return "This is my test data.";
  }

  @PostMapping("/post")
  public Integer post() {
    return 1;
  }

  @GetMapping("/getEntity")
  public UserEntity getEntity() {
    UserEntity userEntity = new UserEntity();
    userEntity.setName("tino");
    userEntity.setAge(12);
    return userEntity;
  }

  @GetMapping("/ex")
  public void ex() {
    throw new BaseException(CommonErrorCode.API_GATEWAY_ERROR);
  }

  @GetMapping("/bizEx")
  public void bizEx() {
    throw new BizException(BizExceptionEnum.USER_AUTH_ERROR);
  }
}
