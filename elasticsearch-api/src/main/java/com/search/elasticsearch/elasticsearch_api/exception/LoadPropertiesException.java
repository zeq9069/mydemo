package com.search.elasticsearch.elasticsearch_api.exception;

/**
 * 
 * 配置文件加载不到异常
 * @author zeq
 *
 */
public class LoadPropertiesException extends RuntimeException {

	private static final long serialVersionUID = -4348856441156845046L;

	public LoadPropertiesException(String message) {
		super(message);
	}

}
