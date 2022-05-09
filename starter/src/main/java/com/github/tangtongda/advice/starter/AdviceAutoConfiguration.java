package com.github.tangtongda.advice.starter;

import com.github.tangtongda.advice.starter.advice.ExceptionAdvice;
import com.github.tangtongda.advice.starter.advice.ResponseAdvice;
import com.github.tangtongda.advice.starter.annotation.EnableGlobalDispose;
import com.github.tangtongda.advice.starter.properties.AdviceProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 * @author <a href="mailto:tangtongda@gmail.com">Tino_Tang</a>
 * @version 1.0.0-4/27/2022
 */
@ConditionalOnClass(EnableGlobalDispose.class)
@PropertySource(value = "classpath:dispose.properties", encoding = "UTF-8")
@EnableAutoConfiguration
public class AdviceAutoConfiguration {

  @Bean
  public AdviceProperties adviceProperties(
      @Value("${advice.exclude-packages}") List<String> packages,
      @Value("${advice.exclude-classes}") List<String> classes) {
    AdviceProperties adviceProperties = new AdviceProperties();
    adviceProperties.setExcludePackages(packages);
    adviceProperties.setExcludeClasses(classes);
    return adviceProperties;
  }

  @ConditionalOnBean(AdviceProperties.class)
  @Bean
  public ResponseAdvice responseAdvice(AdviceProperties adviceProperties) {
    return new ResponseAdvice(adviceProperties);
  }

  @Bean
  public ExceptionAdvice responseAdvice() {
    return new ExceptionAdvice();
  }
}
