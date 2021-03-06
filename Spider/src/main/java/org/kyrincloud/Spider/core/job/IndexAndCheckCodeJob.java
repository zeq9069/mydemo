package org.kyrincloud.Spider.core.job;

import java.io.IOException;
import java.util.Calendar;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.kyrincloud.Spider.core.constant.Constant;
import org.kyrincloud.Spider.core.cookie.GlobalCookieHeader;
import org.kyrincloud.Spider.core.htmlpage.CheckCodePage;
import org.kyrincloud.Spider.core.htmlpage.IndexPage;
import org.kyrincloud.Spider.core.queue.WaitInputCheckCodeQueue;
import org.kyrincloud.Spider.core.requestHeader.RequestHeaderBuild;
import org.kyrincloud.Spider.core.util.ImageUtil;
import org.kyrincloud.Spider.core.util.HttpUtils.HttpMethodType;

import com.alibaba.fastjson.JSONObject;

/**
 * 首页访问（获取验证票和验证码类型）+验证码访问
 * {"Cookie":"xxx","credit_ticket":"xxx","checkcode":"","timestamp":"xxx","keyword":"","browser":"xxx"} 放入等待验证码人工输入队列  ->
 * {"Cookie":"xxx","credit_ticket":"xxx","checkcode":"xxx","timestamp":"xxx","keyword":"xxx","browser":"xxx"} 输入完成之后，放入等待使用的队列
 * 
 * @author kyrin
 *
 */
public class IndexAndCheckCodeJob extends AbstractJob{
	
	private static String[] provinces=new String[]{"beijing","tianjin","henan","hebei","hubei","hunan","jiangxi","zhejiang","shanghai","jiangsu"
		,"fujian","guangdong","guangxi","hainan","yunnan","sichuan","chongqing","shanxi","shanxi","chongqing","neimenggu",
		"jilin","liaoning","heilongjiang","qinghai","gansu","xinjiang","xizang","shandong","taiwan","xianggang","aomen",
		"guizhou","ningxia"};
	
	private static String currentProvince=provinces[0];
	
	private static String keyword="中石油";

	public void start() {
		
		//1请求搜索首页
		IndexPage index=new IndexPage(HttpMethodType.GET, RequestHeaderBuild.buildIndexHeader(),currentProvince);
		index.handler();
		System.out.println("[>>>首页请求完毕<<<]");
		
		//2，构建cookie header = Cookie:JSESSIONID=YnPyWhjGw2mLN8dHxhS7HHJBv7GjYVb41ylcGxvv1MVdGDlrVGCn!-968895144; BIGipServerpool_xy3_web=1108715712.16671.0000
		Header headerCookie=null;
		String val="";
		if(!index.getCookie().isEmpty()){//如果首页存在cookie，就放全局缓存
			 val="JSESSIONID="+index.getCookie().get("JSESSIONID")+"; "+"BIGipServerpool_xy3_web="+index.getCookie().get("BIGipServerpool_xy3_web");
			headerCookie=new BasicHeader("Cookie",val);
			GlobalCookieHeader.setHeader(headerCookie);
		}else{
			headerCookie=GlobalCookieHeader.getHeader();
			val=headerCookie.getValue();
		}
		
		
		//3请求验证码
		long currentTimeMillis=System.currentTimeMillis();
		CheckCodePage checkCode=new CheckCodePage(HttpMethodType.GET, index.checkCodeUri()+"?currentTimeMillis="+currentTimeMillis, RequestHeaderBuild.buildCheckCodeRequestHeader(headerCookie,browser));
		
		try {
			Calendar calendar=Calendar.getInstance();
			ImageUtil.compass(checkCode.getResponse().getEntity().getContent(),Constant.checkCodeImagePath+calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH)+"/"+currentTimeMillis+".png");
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//4,构造数据，放入等待输入验证码的缓存队列中
		JSONObject obj=new JSONObject();
		obj.put("Cookie",val);
		obj.put("credit_ticket", index.getCreditTicket());
		obj.put("checkcode","");
		obj.put("timestamp", checkCode.getcurrentTimeMills());
		obj.put("keyword", keyword);
		obj.put("browser", browser);
		WaitInputCheckCodeQueue.put(checkCode.getcurrentTimeMills()+"",obj);
		System.out.println("[>>>验证码请求完毕<<<]");
	}
	
	
	

}
