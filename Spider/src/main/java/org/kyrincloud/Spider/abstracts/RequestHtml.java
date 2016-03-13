package org.kyrincloud.Spider.abstracts;

import org.apache.http.Header;

public interface RequestHtml {
	
	public Header[] getRequestAllHeader();
	
	public Header[] getRequestHeaders(String key);
	
	public Header getRequestFirstHeader(String key);
	
	public Header getRequestLastHeader(String key);
	
	public Header[] getResponseAllHeader();
	
	public Header[] getResponseHeaders(String key);

	public Header getResponseFirstHeader(String key);
	
	public Header getResponseLastHeader(String key);
	
}
