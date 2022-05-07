package com.github.tangtongda.advice.starter.advice;

import com.github.tangtongda.advice.starter.annotation.IgnoreResponseAdvice;
import com.github.tangtongda.advice.starter.properties.AdviceProperties;
import com.github.tangtongda.advice.starter.response.BaseResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

  private final AdviceProperties adviceProperties;

  public ResponseAdvice(AdviceProperties adviceProperties) {
    this.adviceProperties = adviceProperties;
  }

  @Override
  public boolean supports(
      MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
    return filter(methodParameter);
  }

  @Nullable
  @Override
  public Object beforeBodyWrite(
      Object o,
      MethodParameter methodParameter,
      MediaType mediaType,
      Class<? extends HttpMessageConverter<?>> aClass,
      ServerHttpRequest serverHttpRequest,
      ServerHttpResponse serverHttpResponse) {
    // 如果手动进行了返回封装，判断类型防止二次封装
    if (o instanceof BaseResponse) {
      return o;
    }
    return BaseResponse.succeed(o);
  }

  /**
   * Filer response urls which are no need to package.
   *
   * @param methodParameter method parameter
   * @return true or false
   */
  private Boolean filter(MethodParameter methodParameter) {
    Class<?> declaringClass = methodParameter.getDeclaringClass();
    // Skip the filter packages
    if (null != adviceProperties) {
      long count =
          adviceProperties.getExcludeClasses().stream()
              .filter(l -> declaringClass.getName().contains(l))
              .count();
      if (count > 0) {
        return false;
      }
      // Skip the filter classes
      if (adviceProperties.getExcludeClasses().contains(declaringClass.getName())) {
        return false;
      }
    }

    // Skip the methods which have IgnoreResponseAdvice annotation
    if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)) {
      return false;
    }
    return !Objects.requireNonNull(methodParameter.getMethod())
        .isAnnotationPresent(IgnoreResponseAdvice.class);
  }
}
