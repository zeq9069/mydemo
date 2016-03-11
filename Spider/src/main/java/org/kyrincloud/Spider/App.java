package org.kyrincloud.Spider;

import java.io.InputStream;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
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
 *   header:GET
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
 *     header: POST
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
 *   
 *   header: GET
 *   Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,;q=0.8
 *   Accept-Encoding:gzip, deflate, sdch
 *   Accept-Language:zh-CN,zh;q=0.8,en;q=0.6
 *   Connection:keep-alive
 *   Cookie:JSESSIONID=CrvMWvYCkKKJT9tnv38GlCk9Tf0BC666X8nYw5RvWdrhShgQBQZX!-966417528; BIGipServerpool_xy3_web=1091938496.16671.0000; CNZZDATA1257386840=1567385135-1457596878-http%253A%252F%252Fqyxy.baic.gov.cn%252F%7C1457653914
 *   Host:qyxy.baic.gov.cn
 *   Referer:http://qyxy.baic.gov.cn/gjjbj/gjjQueryCreditAction!getBjQyList.dhtml
 *   Upgrade-Insecure-Requests:1
 *   User-Agent:Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36
 *   
 *   
 *   公司详情里包括以下URL：
 *   		//登记信息
 			/gjjbj/gjjQueryCreditAction!biangengFrame.dhtml?ent_id="+encodeURIComponent(jQuery.trim(entId))+"&clear=true&timeStamp="+new Date().getTime());
			//动产抵押
			/gjjbjTab/gjjTabQueryCreditAction!dcdyFrame.dhtml?entId="+encodeURIComponent(jQuery.trim(entId))+"&clear=true&timeStamp="+new Date().getTime());
			//股权出质登记信息
			/gdczdj/gdczdjAction!gdczdjFrame.dhtml?entId="+encodeURIComponent(jQuery.trim(entId))+"&clear=true&timeStamp="+new Date().getTime());
			//行政处罚
			/gsgs/gsxzcfAction!list.dhtml?entId="+encodeURIComponent(jQuery.trim(entId))+"&clear=true&timeStamp="+new Date().getTime());
			//经营异常
			/gsgs/gsxzcfAction!list_jyycxx.dhtml?entId="+encodeURIComponent(jQuery.trim(entId))+"&clear=true&timeStamp="+new Date().getTime());
			//严重违法
			/gsgs/gsxzcfAction!list_yzwfxx.dhtml?ent_id="+encodeURIComponent(jQuery.trim(entId))+"&clear=true&timeStamp="+new Date().getTime());
			//抽查检查
			/gsgs/gsxzcfAction!list_ccjcxx.dhtml?ent_id="+encodeURIComponent(jQuery.trim(entId))+"&clear=true&timeStamp="+new Date().getTime());
 *   
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
	
	//company详细信息
	static String company_details="http://qyxy.baic.gov.cn/gjjbj/gjjQueryCreditAction!openEntInfo.dhtml";
	
	//company_details登记信息
	static String company_details_djxx="http://qyxy.baic.gov.cn/gjjbj/gjjQueryCreditAction!biangengFrame.dhtml";//ent_id=&clear=true&timeStamp=
	//company_details动产抵押
	static String company_details_dcdy="http://qyxy.baic.gov.cn/gjjbjTab/gjjTabQueryCreditAction!dcdyFrame.dhtml";//entId=&clear=true&timeStamp=;
	//company_details股权出质登记信息
	static String company_details_bqczdjxx="http://qyxy.baic.gov.cn/gdczdj/gdczdjAction!gdczdjFrame.dhtml";//entId=&clear=true&timeStamp=;
	//company_details行政处罚
	static String company_details_xzcf="http://qyxy.baic.gov.cn/gsgs/gsxzcfAction!list.dhtml";//entId=&clear=true&timeStamp=
	//company_details经营异常
	static String company_details_jyyc="http://qyxy.baic.gov.cn/gsgs/gsxzcfAction!list_jyycxx.dhtml";//entId=&clear=true&timeStamp=
	//company_details严重违法
	static String company_details_yzwf="http://qyxy.baic.gov.cn/gsgs/gsxzcfAction!list_yzwf"
			+ "xx.dhtml";//ent_id=&clear=true&timeStamp=
	//company_details抽查检查
	static String company_details_ccjc="http://qyxy.baic.gov.cn/gsgs/gsxzcfAction!list_ccjcxx.dhtml";//ent_id=&clear=true&timeStamp=
	
	/*****************************以下url，实用性待验证*****************************************/
	/*//company_details个体工商户信息
	static String company_details_gtgshxx="http://qyxy.baic.gov.cn/gtgsh/gtgsh_gtgshxxAction!gtgshxx.dhtml";//entId=&clear=true&timeStamp= 
	
	//工商公示信息
	static String company_gsgsxx ="http://qyxy.baic.gov.cn/gjjbj/gjjQueryCreditAction!openEntInfo.dhtml";//entId= &entNo= &credit_ticket= &str=1&timeStamp= ;给该url一个时间戳~~这样就必须每次从服务器读取数
	
	//企业公示信息
	static String company_qygs ="http://qyxy.baic.gov.cn/gjjbj/gjjQueryCreditAction!openInfo.dhtml";//entId= &entNo= &credit_ticket= &str=2&timeStamp= ;给该url一个时间戳~~这样就必须每次从服务器读取数
		
	//其他公示信息
	static String company_qtgs ="http://qyxy.baic.gov.cn/gjjbj/gjjQueryCreditAction!openInfo.dhtml";//entId= &entNo= &credit_ticket= &str=3&timeStamp= ;给该url一个时间戳~~这样就必须每次从服务器读取数
			*/
	
	
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
			ImageUtil.compass(is, "C:\\Users\\lenovo\\Desktop\\code.jpg");
			System.out.println(c);
		}
	}
	
	//请求搜索企业列表currentTimeMillis需要跟每次请求验证码的currentTimeMillis保持一致
	public static void companyList() throws Exception{
		HttpPost get=new HttpPost(company_list+"?currentTimeMillis=1457663488640&credit_ticket=6ABC044E8EEF660D946545495908B8FA&checkcode=6&keyword=百度");
		setCompanyListHeader(get);
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
	
	//请求公司详情页的timeStamp一致,credit_ticket是每个url的认证票，每个url都不相同
	public static void companyDetails() throws Exception{
		long timestamp=(new Date()).getTime();
		HttpGet get=new HttpGet(company_details+"?entId=a1a1a1a027fc643a0128149ecf4a34d9&credit_ticket=84996718BB9D765595A703FE1C1EE038&entNo=110112604140634&timeStamp="+timestamp);
		setCompanyDetailsHeader(get);
		HttpResponse response=client.execute(get);
		System.out.println("header :"+response);
		InputStream is=response.getEntity().getContent();
		System.out.println(response.getEntity().getContentLength());
		System.out.println("时间戳："+timestamp);
		byte[] context=new byte[1024];
		StringBuffer result=new StringBuffer();
		while(is.read(context)!=-1){
			Thread.sleep(1000);
			result.append(new String(context,"UTF-8"));
		}
		System.out.println(result);
	}
	
	
	//请求公司详情页的timeStamp一致
		public static void companyDetailsQygs() throws Exception{
			//任何一个company_details_xxx
			HttpGet get=new HttpGet(company_details_djxx+"?entId=a1a1a1a027fc643a0128149ecf4a34d9&clear=true&timeStamp="+(new Date()).getTime());
			setCompanyDetailsXXHeader(get);
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
		index();
		//code();
		//companyList();
		//companyDetails();
		//companyDetailsQygs();
	}
	
	//请求公司搜索列表页header
	private static void setCompanyListHeader(HttpRequestBase req){
		setCodeHeader(req);
		req.setHeader("Origin", "http://qyxy.baic.gov.cn");
		req.setHeader("Referer","http://qyxy.baic.gov.cn/beijing");
	}
	
	//请求公司搜索列表页header
	private static void setCompanyDetailsHeader(HttpRequestBase req){
			setCodeHeader(req);
			req.setHeader("Referer","http://qyxy.baic.gov.cn/gjjbj/gjjQueryCreditAction!getBjQyList.dhtml");
	}
	//公司详情页header
	private static void setCompanyDetailsXXHeader(HttpRequestBase req){
			setCodeHeader(req);
			req.setHeader("Referer",company_details+"?entId=a1a1a1a027fc643a0128149ecf4a34d9&credit_ticket=8966768067B0FB088A20F1E501A8F026&entNo=110112604140634&timeStamp=1457663593763");
	}
	
	
	//请求验证码header
	private static void setCodeHeader(HttpRequestBase req){
		req.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		req.setHeader("Accept-Encoding", "gzip, deflate");
		req.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
		req.setHeader("Cache-Control", "max-age=0");
		req.setHeader("Connection", "keep-alive");
		req.setHeader("Cookie", "JSESSIONID=nbTPWvrNS5GqdDQxNXG2N2pTXsvnwyVT32lJQhsG3dGk8pbvg8xJ!-968895144; BIGipServerpool_xy3_web=1108715712.16671.0000");
		req.setHeader("Host", "qyxy.baic.gov.cn");
		req.setHeader("Upgrade-Insecure-Requests","1");
		req.setHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36");
	}
	
	    
}

