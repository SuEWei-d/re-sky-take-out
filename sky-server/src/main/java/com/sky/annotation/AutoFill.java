package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于标识某个方法需要进行某个字段自动填充处理
 */
@Target(ElementType.METHOD) // 限定这个注解只能使用在方法上
@Retention(RetentionPolicy.RUNTIME) // 指定这个注解在运行时能够获取到
public @interface AutoFill {
    // 数据库操作类型：UPDATE INSERT
    OperationType value();
}
