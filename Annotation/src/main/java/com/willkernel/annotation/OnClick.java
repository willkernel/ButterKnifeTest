package com.willkernel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by willkernel
 * on 2019/5/3.
 */
@Target(ElementType.METHOD)//注解作用在方法上
@Retention(RetentionPolicy.CLASS)
public @interface OnClick {
    int value();
}
