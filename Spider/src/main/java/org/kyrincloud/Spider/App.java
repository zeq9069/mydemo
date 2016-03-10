package org.kyrincloud.Spider;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;


/**
 * Hello world!
 * 
 * 爬取企业信息:
 * 
 * 流程：
 *  请求验证码 -> 获取验证码checkcode （设置cookie） -> checkcode + credit_ticket + currentTimeMillis + keyword 请求查询公司列表http://qyxy.baic.gov.cn/gjjbj/gjjQueryCreditAction!getBjQyList.dhtml
 * 
 * 
 * 搜索首页：
 *   http://qyxy.baic.gov.cn/beijing
 *    获取 credit_ticket，currentTimeMillis
 * 
 * 请求验证码：
 *   http://qyxy.baic.gov.cn//CheckCodeCaptcha?currentTimeMillis=1457594746390（生成验证码图片）
 *   
 *   header:
 *   Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,;q=0.8
 *	 Accept-Encoding:gzip, deflate, sdch
 *   Accept-Language:zh-CN,zh;q=0.8,en;q=0.6
 *   Cache-Control:max-age=0
 *   Connection:keep-alive
 *   Host:qyxy.baic.gov.cn
 *   Upgrade-Insecure-Requests:1
 *   User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36
 *   
 *   response:
 *   Cache-Control:no-cache="set-cookie"
 *   Date:Thu, 10 Mar 2016 12:45:34 GMT
 *   Set-Cookie:JSESSIONID=ZB1XWhsTfhbY59hvhnBpQLmTPZM2cnmbZ227994lcr2v8vjL7GpN!-966417528; path=/
 *   Set-Cookie:BIGipServerpool_xy3_web=1091938496.16671.0000; path=/
 *   Transfer-Encoding:chunked
 *   Vary:Accept-Encoding, User-Agent
 *   X-Powered-By:Servlet/2.4 JSP/2.0
 *   
 *   生成 checkcode
 * 
 * 请求搜索公司名称列表：
 *   http://qyxy.baic.gov.cn/gjjbj/gjjQueryCreditAction!getBjQyList.dhtml
 *     
 *     header:
 *   Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,;q=0.8
 *   Accept-Encoding:gzip, deflate
 *   Accept-Language:zh-CN,zh;q=0.8,en;q=0.6
 *   Cache-Control:max-age=0
 *   Connection:keep-alive
 *   Content-Length:120
 *   Content-Type:application/x-www-form-urlencoded
 *   Cookie:JSESSIONID=YnPyWhjGw2mLN8dHxhS7HHJBv7GjYVb41ylcGxvv1MVdGDlrVGCn!-968895144; BIGipServerpool_xy3_web=1108715712.16671.0000; CNZZDATA1257386840=882870343-1457609778-http%253A%252F%252Fgsxt.saic.gov.cn%252F%7C1457609778
 *   Host:qyxy.baic.gov.cn
 *   Origin:http://qyxy.baic.gov.cn
 *   Referer:http://qyxy.baic.gov.cn/beijing
 *   Upgrade-Insecure-Requests:1
 *   User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36
 *    
 *    FormData:
 *    currentTimeMillis:1457611588314
 *	  credit_ticket:E426133E30F93C9F8C1A2ACBEC1F93D6
 *	  checkcode:6m9g
 *	  keyword:百度
 *
 *
 * 请求公司详情页
 *   http://qyxy.baic.gov.cn/gjjbj/gjjQueryCreditAction!openEntInfo.dhtml?entId=a1a1a1a027fc643a0128149ecf4a34d9&credit_ticket=12A68858BF5D270044E1E6C8DE3655B9&entNo=110112604140634&timeStamp=1457624843830
 * credit_ticket 需要从公司列表页解析出来，每个公司对应的url的值都不一样
 */
public class App 
{
	//字母+数字
	static String CheckCodeCaptcha="http://qyxy.baic.gov.cn//CheckCodeCaptcha?currentTimeMillis=";
		
	//逻辑运算（10以内的加减乘除）
	static String CheckCodeYunSuan="http://qyxy.baic.gov.cn/CheckCodeYunSuan?currentTimeMillis=";
	
	//搜索页的逻辑运算验证码
	static String verifycode="http://tjcredit.gov.cn/verifycode?date=";
	
	//搜索index
	static String index="http://qyxy.baic.gov.cn/beijing";
	
	//company_list
	static String company_list="http://qyxy.baic.gov.cn/gjjbj/gjjQueryCreditAction!getBjQyList.dhtml";
	
	static String company_details="http://qyxy.baic.gov.cn/gjjbj/gjjQueryCreditAction!openEntInfo.dhtml";
	
	static CloseableHttpClient client=HttpClients.createDefault();
	
	static long c=0;
	
	//搜索首页
	public static void index() throws Exception{
		HttpGet get=new HttpGet(index);
		HttpResponse response=client.execute(get);
		System.out.println("header :"+response);
		InputStream is=response.getEntity().getContent();
		System.out.println(response.getEntity().getContentLength());
		byte[] context=new byte[1024];
		StringBuffer result=new StringBuffer();
		while(is.read(context)!=-1){
			Thread.sleep(5000);
			result.append(new String(context,"UTF-8"));
		}
		System.out.println(result);
	}
	
	//请求验证码url
	public static void code() throws Exception{
		c=(new Date()).getTime();
		HttpGet get=new HttpGet(CheckCodeYunSuan+c);
		setCodeHeader(get);
		HttpResponse response=client.execute(get);
		if(response.getStatusLine().getStatusCode()==200){
			System.out.println("header :"+response);
			InputStream is=response.getEntity().getContent();
			ImageUtil.compass(is, "/Users/zhangerqiang/Desktop/code.jpg");
			System.out.println(c);
		}
	}
	
	//请求搜索企业列表currentTimeMillis需要跟每次请求验证码的currentTimeMillis保持一致
	public static void company() throws Exception{
		HttpPost get=new HttpPost(company_list+"?currentTimeMillis=1457623956356&credit_ticket=A4588C4298879FCA419BAF525E3DCE00&checkcode=8&keyword=百度");
		setCompanyHeader(get);
		HttpResponse response=client.execute(get);
		System.out.println("header :"+response);
		InputStream is=response.getEntity().getContent();
		System.out.println(response.getEntity().getContentLength());
		byte[] context=new byte[1024];
		StringBuffer result=new StringBuffer();
		while(is.read(context)!=-1){
			Thread.sleep(1000);
			result.append(new String(context,"UTF-8"));
		}
		System.out.println(result);
	}
	
	
	
	public static void main(String[] args) throws Exception {
		//index();
		//code();
		company();
	}
	
	private static void setCompanyHeader(HttpPost get){
		get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		get.setHeader("Accept-Encoding", "gzip, deflate");
		get.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
		get.setHeader("Cache-Control", "max-age=0");
		get.setHeader("Connection", "keep-alive");
		//get.setHeader("Content-Type", "application/x-www-form-urlencoded");
		get.setHeader("Cookie", "JSESSIONID=T4r7WhMZTSpWJS8TGdyg83SbLcrhnVpvBJjmVRKWvClygPBQZD5Y!1768546710; BIGipServerpool_xy3_web=1058384064.17695.0000");
		get.setHeader("Host", "qyxy.baic.gov.cn");
		get.setHeader("Origin", "http://qyxy.baic.gov.cn");
		get.setHeader("Referer","http://qyxy.baic.gov.cn/beijing");
		get.setHeader("Upgrade-Insecure-Requests","1");
		get.setHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36");
	}
	
	
	private static void setCodeHeader(HttpGet get){
		get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		get.setHeader("Accept-Encoding", "gzip, deflate");
		get.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
		get.setHeader("Cache-Control", "max-age=0");
		get.setHeader("Connection", "keep-alive");
		//get.setHeader("Content-Type", "application/x-www-form-urlencoded");
		get.setHeader("Cookie", "JSESSIONID=T4r7WhMZTSpWJS8TGdyg83SbLcrhnVpvBJjmVRKWvClygPBQZD5Y!1768546710; BIGipServerpool_xy3_web=1058384064.17695.0000");
		get.setHeader("Host", "qyxy.baic.gov.cn");
		//get.setHeader("Origin", "http://qyxy.baic.gov.cn");
		//get.setHeader("Referer","http://qyxy.baic.gov.cn/beijing");
		get.setHeader("Upgrade-Insecure-Requests","1");
		get.setHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36");
	
	}
	
	    
}

