package org.kyrincloud.Spider.core.util;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * http请求工具类
 * @author kyrin
 *
 */
public final class HttpUtils {
	
	private static CloseableHttpClient httpClient=HttpClients.createDefault();
	
	public static HttpResponse exec(HttpUriRequest request) throws ClientProtocolException, IOException{
		return httpClient.execute(request);
	}
	
	public static HttpUriRequest buildRquest(HttpMethodType type,String url,Header[] headers){
		HttpUriRequest request=null;
		switch(type){
		case GET:
			request = new HttpGet(url);
			break;
		case POST:
			request = new HttpPost(url);
			break;
		default:
			return null;
		}
		if(headers!=null){
			for(Header header:headers){
				request.addHeader(header);
			}
		}
		return request;
	}
	
	public enum HttpMethodType{
		POST,GET
	}

}
