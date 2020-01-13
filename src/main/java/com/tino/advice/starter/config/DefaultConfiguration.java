package com.tino.advice.starter.config;

import com.tino.advice.starter.advice.BaseResponseAdvice;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 全局返回拦截默认配置
 *
 * @author tino
 * @date 2020/1/2
 */
@Configuration
@EnableConfigurationProperties(DefaultProperties.class)
@PropertySource(value = "classpath:dispose.properties", encoding = "UTF-8")
public class DefaultConfiguration {

    @Bean
    public BaseResponseAdvice commonResponseDataAdvice(DefaultProperties defaultProperties) {
        return new BaseResponseAdvice(defaultProperties);
    }
}
