package com.kyrin.OltuDemo.shiro.config;

import java.io.InputStream;
import java.util.Properties;


/**
 * properties配置文件加载工具类
 * @author kyrin
 *
 */
public final class ResourceLoaderUtil {
	
	
	public static Properties loader(String name){
		if(name==null || name.trim().equals("")){
			return null;
		}
		try{
			InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
			Properties properties = new Properties();
			properties.load(is);
			return properties;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	} 
}
