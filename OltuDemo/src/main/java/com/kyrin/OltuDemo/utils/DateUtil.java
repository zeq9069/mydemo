package com.kyrin.OltuDemo.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 
 *时间工具类
 */
public final class DateUtil {
	
	/*
	 * 给date加时
	 */
	public static Date plus(Date date,int amount,TimeUnit amountUnit){
		Calendar cal = Calendar.getInstance();
		if(amountUnit.equals(TimeUnit.DAYS)){
			cal.add(Calendar.DAY_OF_MONTH, amount);
		}
		if(amountUnit.equals(TimeUnit.HOURS)){
			cal.add(Calendar.HOUR_OF_DAY, amount);
		}
		if(amountUnit.equals(TimeUnit.SECONDS)){
			cal.add(Calendar.SECOND, amount);
		}
		return cal.getTime();
	}
	
	/*
	 * 时间相减,返回 seconds
	 */
	public static long less(Date first,Date second){
		return (first.getTime()-second.getTime())/1000;
	}
}
