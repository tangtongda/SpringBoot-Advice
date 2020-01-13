package com.tino.advice.starter.annotation;

import com.tino.advice.starter.config.DefaultConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author tino
 * @date 2020/1/2
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(DefaultConfiguration.class)
public @interface EnableGlobalDispose {

}
