package org.kyrincloud.Spider.htmlpage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.kyrincloud.Spider.abstracts.AbstractRequestHtml;
import org.kyrincloud.Spider.util.HttpUtils.HttpMethodType;

//2.验证码请求页
public class CheckCodePage extends AbstractRequestHtml{

	//验证码结果
	private String code;
	
	//请求验证码的时间戳
	private long currentTimeMills;
	
	
	public CheckCodePage(HttpMethodType type, String url, Header[] headers) {
		super(type, url, headers);
		int index=url.indexOf("=");
		this.currentTimeMills=Long.parseLong(url.substring(index+1,url.length()));
	}

	@Override
	public void handler() {
		// TODO 解析图片，获取验证码
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			code = br.readLine().replace("\t", "").replace("\n", "").replace("\r", "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getCode(){
		return this.code;
	}
	
	public long getcurrentTimeMills(){
		return this.currentTimeMills;
	}

}
