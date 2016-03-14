package org.kyrincloud.Spider.core.requestHeader;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

/**
 * 浏览器类型
 * 用于请求时，构建User-Agent头部
 * @author kyrin
 *
 */
public class BrowserType {
	
	public static Header MAC_CHROME=new BasicHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36");

	public static Header WIN_CHROME=new BasicHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");

	public static Header WIN_FIREFOX=new BasicHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0");
	
}
