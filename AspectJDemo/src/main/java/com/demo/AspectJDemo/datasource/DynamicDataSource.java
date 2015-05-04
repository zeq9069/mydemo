package com.demo.AspectJDemo.datasource;

import java.util.Map;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * ***********************
 * 
 *  动态数据源
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年5月2日]
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource{

	private static Logger logger=Logger.getLogger(DynamicDataSource.class);
	
	private Map<Object,Object> targetDataSources;
	private static final String DEFAULT_TARGET_DATASOURCE="defaultTargetDataSource";
	private static final ThreadLocal<Stack<String>> threadLocal=new ThreadLocal<Stack<String>>(){
		@Override
		protected Stack<String> initialValue() {
			return new Stack<String>();
		}
	};
	
	@Override
	public void afterPropertiesSet() {
		if(targetDataSources==null){
			logger.error("The dataSources property is null ");
			throw new IllegalArgumentException("dataSources is null");
		}
		if(targetDataSources.get(DEFAULT_TARGET_DATASOURCE)==null){
			logger.error("Not set the key of 'defaultTargetDataSource' ");
			throw new IllegalArgumentException("Please set the key 'defaultTargetDataSource'");
		}
		super.setTargetDataSources(targetDataSources);
		super.setDefaultTargetDataSource(targetDataSources.get(DEFAULT_TARGET_DATASOURCE));
		super.afterPropertiesSet();
	}
	
	/**
	 * 当使用@Transaction注解时，只能使用注解的方式。
	 * 手动切换的方式有几个问题:
	 * <1>当你使用@Transaction 注解来托管事务的时候，在service方法内使用手动切换数据源时，是失败的！
	 * 	  必须在事务之前切换数据源！这时候必须使用注解的方式
	 * <2>当你在repository层使用事务时，你可以手动切换也可以使用注解的方式
	 */
	public static void changeFor(String target){
		logger.info("Change current dataSource to "+target);
		Stack<String> current=threadLocal.get();
		current.push(target);
	}
	
	@Override
	protected Object determineCurrentLookupKey() {
		Stack<String> current=threadLocal.get();
		if(current.isEmpty()) return "";
		String name=current.pop() ;
		return name== null ? "" : name;
	}
	
	public Map<Object, Object> getTargetDataSources() {
		return targetDataSources;
	}

	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		this.targetDataSources = targetDataSources;
	}

}
