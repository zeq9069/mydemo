package com.demo.AspectJDemo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * **********************************
 * 
 *  自定义注解
 *  
 *  用于将数据源分发到service的每一个方法上
 *  
 *  注解到service层的实现类上
 *
 * *********************************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年5月4日]
 *
 */

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DataSourceDistribute {

	DataSourceEntity[] value();
}
