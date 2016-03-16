package org.kyrincloud.Spider.core.job;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.kyrincloud.Spider.core.constant.Constant;
import org.kyrincloud.Spider.core.htmlpage.CompanyDetailsPage;
import org.kyrincloud.Spider.core.htmlpage.CompanyListPage;
import org.kyrincloud.Spider.core.queue.WaitFetchQueue;
import org.kyrincloud.Spider.core.requestHeader.RequestHeaderBuild;
import org.kyrincloud.Spider.core.util.HttpUtils.HttpMethodType;

import com.alibaba.fastjson.JSONObject;

/**
 * 爬取公司信息整个流程的任务（要从队列获取cookie和验证码类型）
 * @author kyrin
 *
 */
public class FetchCompanyInfoJob extends AbstractJob{
	
	private static String[] provinces=new String[]{"beijing","tianjin","henan","hebei","hubei","hunan","jiangxi","zhejiang","shanghai","jiangsu"
		,"fujian","guangdong","guangxi","hainan","yunnan","sichuan","chongqing","shanxi","shanxi","chongqing","neimenggu",
		"jilin","liaoning","heilongjiang","qinghai","gansu","xinjiang","xizang","shandong","taiwan","xianggang","aomen",
		"guizhou","ningxia"};
	
	private static String currentProvince=provinces[0];

	public void start() {
		JSONObject obj=null;
		while(!WaitFetchQueue.isEmpty()){
			obj=WaitFetchQueue.poll();
			if(obj!=null){
				break;
			}
		}
		//获取User-Agent头部
		BasicHeader headerCookie=new BasicHeader("Cookie",obj.getString("Cookie"));
		setBrowser(obj.getObject("browser", Header.class));
		//3请求公司列表页
		CompanyListPage companyList=new CompanyListPage(HttpMethodType.POST,Constant.company_list+"?currentTimeMillis="+obj.getString("timestamp")+"&"+"credit_ticket="+obj.getString("credit_ticket")+"&checkcode="+obj.getString("checkcode")+"&"+"keyword="+obj.getString("keyword"), RequestHeaderBuild.buildCompanyListHeader(currentProvince, headerCookie,browser));
		companyList.handler();
		System.out.println("[>>>公司列表请求完毕<<<]");
		
		//4获取公司详情信息
		for(int i=0;i<companyList.getCompanyUrlLit().size();i++){
			CompanyDetailsPage companyDetails=new CompanyDetailsPage(HttpMethodType.GET, companyList.getCompanyUrlLit().get(i), RequestHeaderBuild.buildCompanyDetailsHeader(headerCookie,browser));
			companyDetails.handler();
		}
		System.out.println("[>>>公司详情请求完毕<<<]");
	}

}
