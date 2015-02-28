package com.search.elasticsearch.elasticsearch_api.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.search.elasticsearch.elasticsearch_api.exception.LoadPropertiesException;
import com.search.elasticsearch.elasticsearch_api.exception.NotFindArgumentException;

/**
 * 
 * 配置文件加载工具类
 * @author zeq
 *
 */
public class AppConfigUtils {
	private static URL url = null;
	private static Properties pro = null;
	private static final String DEFAULT_ELASTICSEARCH_PROPERTIES = "elasticsearch.properties";
	private static Logger logger = Logger.getLogger(AppConfigUtils.class);

	public static Properties load() {
		return load(DEFAULT_ELASTICSEARCH_PROPERTIES);
	}

	private static Properties load(String path) {
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			url = loader.getResource(path);
			if (url == null) {
				throw new LoadPropertiesException(DEFAULT_ELASTICSEARCH_PROPERTIES + "配置文件加载异常！");
			}
		} catch (LoadPropertiesException e) {
			logger.warn(e.getMessage());
			System.out.println(e.getMessage());
		}
		return getProperty();
	}

	private static Properties getProperty() {
		try {
			InputStream in = url.openStream();
			pro = new Properties();
			pro.load(in);
			if (!pro.containsKey(GlobalConstant.ELASTICSEARCH_SERVER_HOST)
					|| !pro.containsKey(GlobalConstant.ELASTICSEARCH_SERVER_PORT)
					|| !pro.containsKey(GlobalConstant.ELASTICSEARCH_INDEX)
					|| !pro.containsKey(GlobalConstant.ELASTICSEARCH_INDEX_TYPE)) {
				throw new NotFindArgumentException(DEFAULT_ELASTICSEARCH_PROPERTIES + "中缺少参数异常！");
			}
		} catch (LoadPropertiesException e) {
			// TODO Auto-generated catch block
			logger.warn(e.getMessage());
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.warn(e.getMessage());
		} catch (NotFindArgumentException e) {
			System.out.println(e.getMessage());
			logger.warn(e.getMessage());
		}
		return pro;
	}

}
