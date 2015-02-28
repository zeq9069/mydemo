package com.search.elasticsearch.elasticsearch_api.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * http请求工具类：实现restful接口
 * 
 * @author zeq
 *
 */
public class HttpUtils {
	private static CloseableHttpClient client = null;
	private static HttpResponse response;

	public HttpUtils() {

	}

	//创建链接
	private static CloseableHttpClient createClient() {
		if (client == null) {
			client = HttpClientBuilder.create().build();
		}
		return client;
	}

	//GET

	public static HttpEntity get(String url) {

		try {
			HttpGet get = new HttpGet(url);
			client = createClient();
			response = client.execute(get);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//closeClient();
		}
		return response.getEntity();
	}

	//POST

	public static HttpEntity post(String url, HttpEntity entity) {
		try {
			HttpPost post = new HttpPost(url);
			post.setEntity(entity);
			client = createClient();
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//closeClient();
		}
		return response.getEntity();
	}

	//PUT

	public static HttpEntity put(String url, HttpEntity entity) {
		try {
			HttpPut post = new HttpPut(url);
			post.setEntity(entity);
			client = createClient();
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//closeClient();
		}
		return response.getEntity();
	}

	//DELETE
	public static HttpEntity delete(String url, HttpEntity entity) {
		try {
			HttpDelete delete = new HttpDelete(url);
			client = createClient();
			response = client.execute(delete);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//closeClient();
		}
		return response.getEntity();
	}

	//关闭链接
	/*private static void closeClient() {
		if (client != null) {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/
}
