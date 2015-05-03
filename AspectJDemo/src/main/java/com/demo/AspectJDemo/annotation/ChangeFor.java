package com.demo.AspectJDemo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * **********************************************************
 * 
 * 		 自定义注解
 * 
 *  用来注解切换数据源,master和slave
 *  
 *  默认就是AbstractRutingDataSource中的defaultTargetDataSource
 *
 * **********************************************************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年5月1日]
 *
 */

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ChangeFor {

	public String value() default "defaultTargetDataSource";
}
