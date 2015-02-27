package com.demo.ehcache.utils;

/**
 * 
 * @author kyrin
 */
public class EhStringUtils {

	public static boolean hasText(String value) {
		return value != null && !value.trim().equals("");
	}

	public static boolean isNull(Object value) {
		return value == null;
	}

}
