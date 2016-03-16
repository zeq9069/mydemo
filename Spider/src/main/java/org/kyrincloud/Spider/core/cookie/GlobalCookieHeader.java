package org.kyrincloud.Spider.core.cookie;

import org.apache.http.Header;

/**
 * 全局缓存cookie Header 用来共享cookie  （cookie过时之后还没有验证）
 * Cookie:JSESSIONID=YnPyWhjGw2mLN8dHxhS7HHJBv7GjYVb41ylcGxvv1MVdGDlrVGCn!-968895144; BIGipServerpool_xy3_web=1108715712.16671.0000
 * @author kyrin
 *
 */
public  class GlobalCookieHeader{
	
	private static  Header header;

	public static Header getHeader() {
		return header;
	}

	public static void setHeader(Header header) {
		GlobalCookieHeader.header = header;
	}
	
	
}
