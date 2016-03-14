package org.kyrincloud.Spider.job;

import org.apache.http.Header;

public abstract class AbstractJob implements Job{

	
	//浏览器类型
	public Header browser;
	
	public void run() {
		start();
	}
	
	public abstract void start();

	public Header getBrowser() {
		return browser;
	}

	public void setBrowser(Header browser) {
		this.browser = browser;
	}
	
}
