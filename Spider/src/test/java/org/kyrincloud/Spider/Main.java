package org.kyrincloud.Spider;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.kyrincloud.Spider.constant.Constant;
import org.kyrincloud.Spider.htmlpage.CheckCodePage;
import org.kyrincloud.Spider.htmlpage.CompanyDetailsPage;
import org.kyrincloud.Spider.htmlpage.CompanyListPage;
import org.kyrincloud.Spider.htmlpage.IndexPage;
import org.kyrincloud.Spider.util.HttpUtils.HttpMethodType;
import org.kyrincloud.Spider.util.ImageUtil;

/**
 * 测试
 */
@Deprecated
public class Main {
	
	private static String[] provinces=new String[]{"beijing","tianjin","henan","hebei","hubei","hunan","jiangxi","zhejiang","shanghai","jiangsu"
			,"fujian","guangdong","guangxi","hainan","yunnan","sichuan","chongqing","shanxi","shanxi","chongqing","neimenggu",
			"jilin","liaoning","heilongjiang","qinghai","gansu","xinjiang","xizang","shandong","taiwan","xianggang","aomen",
			"guizhou","ningxia"};
	private static String currentProvince=provinces[0];
	private static String checkCodeImagePath="C:\\Users\\lenovo\\Desktop\\code.png";
	private static String keyword="中石油";
	
	public void testIndex() throws UnsupportedOperationException, IOException{
		IndexPage index=new IndexPage(HttpMethodType.GET, null,currentProvince);
		index.handler();
		System.out.println(index.getResponseFirstHeader("Set-Cookie").getValue());
		InputStream is=index.getResponse().getEntity().getContent();
		byte[] b=new byte[1024*10];
		while(is.read(b)!=-1){
			System.out.println(new String(b,"UTF-8"));
			Arrays.fill(b, (byte)0);
		}
	}
	
	public static void main(String[] args) throws UnsupportedOperationException, IOException {
		currentProvince=provinces[0];
	
		//1请求搜索首页
		IndexPage index=new IndexPage(HttpMethodType.GET, null,currentProvince);
		index.handler();
		System.out.println("[>>>首页请求完毕<<<]");
		//构建cookie header = Cookie:JSESSIONID=YnPyWhjGw2mLN8dHxhS7HHJBv7GjYVb41ylcGxvv1MVdGDlrVGCn!-968895144; BIGipServerpool_xy3_web=1108715712.16671.0000
		Header headerCookie=new BasicHeader("Cookie","JSESSIONID="+index.getCookie().get("JSESSIONID")+"; "+"BIGipServerpool_xy3_web="+index.getCookie().get("BIGipServerpool_xy3_web"));
		
		//2请求验证码
		CheckCodePage checkCode=new CheckCodePage(HttpMethodType.GET, index.checkCodeUri()+"?currentTimeMillis="+System.currentTimeMillis(), buildCheckCodeRequestHeader(headerCookie));
		ImageUtil.compass(checkCode.getResponse().getEntity().getContent(),checkCodeImagePath);
		checkCode.handler();
		System.out.println("[>>>验证码请求完毕<<<]");
		
		//3请求公司列表页
		CompanyListPage companyList=new CompanyListPage(HttpMethodType.POST,Constant.company_list+"?currentTimeMillis="+checkCode.getcurrentTimeMills()+"&"+"credit_ticket="+index.getCreditTicket()+"&checkcode="+checkCode.getCode()+"&"+"keyword="+keyword, buildCompanyListHeader(headerCookie));
		companyList.handler();
		System.out.println("[>>>公司列表请求完毕<<<]");
		
		//4获取公司详情信息
		for(int i=0;i<companyList.getCompanyUrlLit().size();i++){
			CompanyDetailsPage companyDetails=new CompanyDetailsPage(HttpMethodType.GET, companyList.getCompanyUrlLit().get(i), buildCompanyDetailsHeader(headerCookie));
			companyDetails.handler();
		}
		System.out.println("[>>>公司详情请求完毕<<<]");
	}
	
	//请求公司搜索列表页header
	private static Header[] buildCompanyListHeader(Header ...header){
		Header[] appendHeaders=buildCommonHeader();
		Header[] headers=new Header[2+appendHeaders.length+header.length];
		int appendHeaderLength=appendHeaders.length;
		int headerLength=header.length;
		for(int i=0;i<appendHeaderLength;i++){
			headers[i]=appendHeaders[i];
		}
		for(int i=appendHeaderLength;i<appendHeaderLength+headerLength;i++){
			headers[i]=header[i-appendHeaderLength];
		}
		headers[appendHeaderLength+headerLength+1]=new BasicHeader("Origin", "http://qyxy.baic.gov.cn");
		headers[appendHeaderLength+headerLength+1]=new BasicHeader("Referer","http://qyxy.baic.gov.cn/"+currentProvince);
		return headers;
	}
	
	// 请求公司搜索列表页header
	private static Header[] buildCompanyDetailsHeader(Header... header) {
		Header[] appendHeaders = buildCommonHeader();
		Header[] headers = new Header[1 + appendHeaders.length + header.length];
		int appendHeaderLength = appendHeaders.length;
		int headerLength = header.length;
		for (int i = 0; i < appendHeaderLength; i++) {
			headers[i] = appendHeaders[i];
		}
		for (int i = appendHeaderLength; i < appendHeaderLength + headerLength; i++) {
			headers[i] = header[i - appendHeaderLength];
		}
		headers[appendHeaderLength + headerLength] = new BasicHeader(
				"Referer",
				"http://qyxy.baic.gov.cn/gjjbj/gjjQueryCreditAction!getBjQyList.dhtml");
		return headers;
	}
	
	//公司详情页header
	private static Header[] buildCompanyDetailsXXHeader(Header ...header){
		Header[] appendHeaders=buildCommonHeader();
		Header[] headers=new Header[appendHeaders.length+header.length];
		int appendHeaderLength=appendHeaders.length;
		int headerLength=header.length;
		for(int i=0;i<appendHeaderLength;i++){
			headers[i]=appendHeaders[i];
		}
		for(int i=appendHeaderLength;i<appendHeaderLength+headerLength;i++){
			headers[i]=header[i-appendHeaderLength];
		}
		//缺少"Referer",Constant.company_details+"?entId=a1a1a1a027fc643a0128149ecf4a34d9&credit_ticket=8966768067B0FB088A20F1E501A8F026&entNo=110112604140634&timeStamp=1457663593763"
		//还缺少"Cookie", "JSESSIONID=nbTPWvrNS5GqdDQxNXG2N2pTXsvnwyVT32lJQhsG3dGk8pbvg8xJ!-968895144; BIGipServerpool_xy3_web=1108715712.16671.0000"
		return headers;
	}
	
	private static Header[] buildCheckCodeRequestHeader(Header ...header){
		Header[] appendHeaders=buildCommonHeader();
		Header[] headers=new Header[appendHeaders.length+header.length];
		int appendHeaderLength=appendHeaders.length;
		int headerLength=header.length;
		for(int i=0;i<appendHeaderLength;i++){
			headers[i]=appendHeaders[i];
		}
		for(int i=appendHeaderLength;i<appendHeaderLength+headerLength;i++){
			headers[i]=header[i-appendHeaderLength];
		}
		return header;
	}
	
	private static Header[] buildCommonHeader(){
		Header[] headers=new Header[8];
		headers[0]=new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		headers[1]=new BasicHeader("Accept-Encoding", "gzip, deflate");
		headers[2]=new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
		headers[3]=new BasicHeader("Cache-Control", "max-age=0");
		headers[4]=new BasicHeader("Connection", "keep-alive");
		headers[5]=new BasicHeader("Upgrade-Insecure-Requests","1");
		headers[6]=new BasicHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36");
		headers[7]=new BasicHeader("Host", "qyxy.baic.gov.cn");
		return headers;
	}

}
