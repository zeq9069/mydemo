package org.kyrincloud.Spider.core.abstracts;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.kyrincloud.Spider.core.util.HttpUtils;
import org.kyrincloud.Spider.core.util.HttpUtils.HttpMethodType;

/**
 * 页面行为抽象类
 * @author kyrin
 *
 */
public abstract class AbstractRequestHtml implements RequestHtml{

	private HttpResponse response;
	
	private HttpUriRequest request;
	
	public AbstractRequestHtml(HttpMethodType type,String url,Header[] headers){
		request = HttpUtils.buildRquest(type, url, headers);
		try {
			response = HttpUtils.exec(request);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 页面处理方法（页面解析）
	 */
	public abstract void  handler();

	public Header[] getRequestAllHeader() {
		return request.getAllHeaders();
	}

	public Header[] getRequestHeaders(String key) {
		return request.getHeaders(key);
	}

	public Header getRequestFirstHeader(String key) {
		return request.getFirstHeader(key);
	}
	
	public Header getRequestLastHeader(String key) {
		return request.getLastHeader(key);
	}


	public Header[] getResponseAllHeader() {
		return response.getAllHeaders();
	}

	public Header[] getResponseHeaders(String key) {
		return response.getHeaders(key);
	}

	public Header getResponseFirstHeader(String key) {
		return response.getFirstHeader(key);
	}
	
	public Header getResponseLastHeader(String key) {
		return response.getLastHeader(key);
	}

	public HttpResponse getResponse() {
		return this.response;
	}

	public HttpRequest getRequest() {
		return this.request;
	}
	
	//获取源代码
	public String getHtmlText() throws UnsupportedOperationException, IOException{
		String htmlText=null;
		try {
			InputStream is = getResponse().getEntity().getContent();
			InputStreamReader isr = new InputStreamReader(is,"utf-8"); 
			char[] b=new char[1024];
			while(isr.read(b)!=-1){
				String res=new String(b);
				Arrays.fill(b, (char)0);
				htmlText+=res;
			}
			isr.close();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return htmlText;
	}
	
}
