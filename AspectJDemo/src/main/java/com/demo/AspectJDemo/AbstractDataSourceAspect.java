package com.demo.AspectJDemo;

import java.util.Iterator;
import java.util.List;

import com.demo.AspectJDemo.filter.MonitorFilter;

public class AbstractDataSourceAspect {
	
	protected List<MonitorFilter> filters;
	
	
	public AbstractDataSourceAspect(){
		this(null);
	}
	
	public AbstractDataSourceAspect(List<MonitorFilter> filters){
		this.filters=filters;
	}
	
	protected void before(){
		if(filters!=null){
			Iterator<MonitorFilter> it=filters.iterator();
			while(it.hasNext()){
				it.next().before();
			}
		}
	}
	
	protected void after(){
		if(filters!=null){
			Iterator<MonitorFilter> it=filters.iterator();
			while(it.hasNext()){
				it.next().after();
			}
		}
	}
}
