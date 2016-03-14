package org.kyrincloud.Spider.core.htmlpage;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.kyrincloud.Spider.core.abstracts.AbstractRequestHtml;
import org.kyrincloud.Spider.core.constant.Constant;
import org.kyrincloud.Spider.core.util.HttpUtils.HttpMethodType;

/**
 * 获取企业列表页面
 * @author kyrin
 *
 */
public class CompanyListPage extends AbstractRequestHtml{

	//每个公司的访问url
	public List<String> companyUrlList=new ArrayList<String>();
	
	public CompanyListPage(HttpMethodType type,String url, Header[] headers) {
		super(type, url, headers);
	}

	@Override
	public void handler() {
		// TODO 处理公司列表页html内容，解析出每个公司的访问url
		
		try {
			Parser parser=new Parser();
			parser.setInputHTML(getHtmlText());
			NodeFilter[] nodeFilter=new NodeFilter[] {new TagNameFilter("a"),new HasAttributeFilter("href", "#"),new HasAttributeFilter("onclick")};
			AndFilter andFilter_1=new AndFilter(nodeFilter);
			AndFilter andFilter=new AndFilter(andFilter_1,new HasParentFilter(new TagNameFilter("li")));
			
			NodeList nodeList=parser.parse(andFilter);
			
			for(int i=0;i<nodeList.size();i++){
				Node node=nodeList.elementAt(i);
				TagNode tagNode=new TagNode();
				tagNode.setText(node.toHtml());
				String onclickValue=tagNode.getAttribute("onclick");
				String [] values=onclickValue.trim().replace("\t", "").replace("\n", "").replace("\n\t", "").replace("openEntInfo", "").replace("(", "").replace(");", "").replace("'", "")
						.split(",");
				if(values.length!=4){
					continue;
				}
				String entName=new String(values[0].replace("openEntInfo", "").trim().getBytes(),"UTF-8");
				String entId=new String(values[1].trim().getBytes(),"UTF-8");
				String entNo=new String(values[2].trim().getBytes(),"UTF-8");
				String credit_ticket =new String(values[3].trim().getBytes(),"UTF-8");
				long timeStamp=System.currentTimeMillis();
				String url=Constant.company_details+"?entId="+entId+"&entName="+entName+"&entNo="+entNo+"&timeStamp="+timeStamp+"&credit_ticket="+credit_ticket;
				//url=url.replace("\n\t\t", "");
				url=URLEncoder.encode(url, "UTF-8");
				System.out.println(url);
				url=url.replace("%00", "");//过滤不合法字符
				System.out.println(url);
				url=URLDecoder.decode(url,"UTF-8");
				companyUrlList.add(url);
			}
			
		} catch (ParserException   e) {
			e.printStackTrace();
		} catch (  UnsupportedOperationException  e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<String> getCompanyUrlLit(){
		return this.companyUrlList;
	}

}
