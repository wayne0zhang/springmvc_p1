package com.paxing.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 自定义注解 适用类、接口（包括注解类型）或枚举，
 * @author wayne-zhang
 * @date 2017/7/26 19:13.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented//注解表明这个注解应该被 javadoc工具记录
@Component//泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注
public @interface MyBatisDao {
}
