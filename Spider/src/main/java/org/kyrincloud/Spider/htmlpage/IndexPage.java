package org.kyrincloud.Spider.htmlpage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.Header;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.kyrincloud.Spider.abstracts.AbstractRequestHtml;
import org.kyrincloud.Spider.constant.Constant;
import org.kyrincloud.Spider.util.HttpUtils.HttpMethodType;

/**
 * 1.搜搜首页
 * @author kyrin
 *
 */
public class IndexPage extends AbstractRequestHtml{
	
	private CheckCodeType checkCodeType;
	
	private String credit_ticket;
	
	private Map<String,String> cookieMap=new ConcurrentHashMap<String, String>();
	
	
	public IndexPage(HttpMethodType type, Header[] headers,String province) {
		super(type, Constant.index+province, headers);
	}

	@Override
	public void handler() {
		//TODO html文本解析
		//1，解析cookie
		Header[] cookieHeader=getResponseHeaders("Set-Cookie");
		for(int i=0;i<cookieHeader.length;i++){
			String[] result=cookieHeader[i].getValue().split(";");
			String[] nameAndValue=result[0].split("=");
			cookieMap.put(nameAndValue[0].trim(), nameAndValue[1].trim());
		}
		
		//2解析credit_ticket和验证码
		try {
			Parser parser=new Parser();
			parser.setInputHTML(getHtmlText());
			AndFilter inputFilter=new AndFilter(new TagNameFilter("input") ,new HasAttributeFilter("name","credit_ticket"));
			AndFilter imgFilter=new AndFilter(new TagNameFilter("img") ,new HasAttributeFilter("id","MzImgExpPwd"));
			OrFilter orFilter=new OrFilter(inputFilter,imgFilter);
			
			NodeList inputNodeList=parser.parse(orFilter);
			Node imgNode=inputNodeList.elementAt(0);
			Node inputNode=inputNodeList.elementAt(1);
			
			TagNode inputTagNode=new TagNode();
			inputTagNode.setText(inputNode.toHtml());
			credit_ticket = inputTagNode.getAttribute("value");

			TagNode imgTagNode=new TagNode();
			imgTagNode.setText(imgNode.toHtml());
			String src=imgTagNode.getAttribute("src");
			
			if(src.contains("CheckCodeCaptcha")){
				checkCodeType=CheckCodeType.CheckCodeCaptcha;
			}
			if(src.contains("CheckCodeYunSuan")){
				checkCodeType=CheckCodeType.CheckCodeYunSuan;
			}
			
			System.out.println(""+credit_ticket+":"+checkCodeUri());
		} catch (ParserException | UnsupportedOperationException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//获取首页中默认的验证码url
	public String checkCodeUri(){
		if(checkCodeType.equals(CheckCodeType.CheckCodeCaptcha)){
			return Constant.CheckCodeCaptcha;
		}
		if(checkCodeType.equals(CheckCodeType.CheckCodeYunSuan)){
			return Constant.CheckCodeYunSuan;
		}
		if(checkCodeType.equals(CheckCodeType.verifycode)){
			return Constant.verifycode;
		}
		return null;
	}
	
	public CheckCodeType checkCodeType(){
		return this.checkCodeType;
	}
	
	public String getCreditTicket(){
		return this.credit_ticket;
	}
	
	public Map<String,String> getCookie(){
		return cookieMap;
	}
	
	public enum CheckCodeType{
		CheckCodeCaptcha,CheckCodeYunSuan,verifycode
	}

}
