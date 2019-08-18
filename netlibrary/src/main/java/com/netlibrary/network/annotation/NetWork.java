package com.netlibrary.network.annotation;

import com.netlibrary.network.type.NetType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Creator :Wen
 * DataTime: 2019/8/18
 * Description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NetWork {

    NetType netType() default NetType.AUTO;

}
