package com.willkernel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by willkernel
 * on 2019/5/3.
 */
@Target(ElementType.FIELD)//注解作用在属性值上
@Retention(RetentionPolicy.CLASS)//编译期方式
public @interface BindView {
    int value();
}
