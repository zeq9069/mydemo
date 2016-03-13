package org.kyrincloud.Spider.htmlpage;

import java.io.IOException;

import org.apache.http.Header;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasChildFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.kyrincloud.Spider.abstracts.AbstractRequestHtml;
import org.kyrincloud.Spider.htmlpage.model.CompanyInfo;
import org.kyrincloud.Spider.util.HttpUtils.HttpMethodType;

/**
 * 公司详情页
 * @author kyrin
 *
 */
public class CompanyDetailsPage extends AbstractRequestHtml{

	private CompanyInfo companyInfo; 
	
	
	public CompanyDetailsPage(HttpMethodType type, String url, Header[] headers) {
		super(type, url, headers);
	}

	@Override
	public void handler() {
		// TODO 解析页面,获取公司信息
		try {
			AndFilter andFilter=new AndFilter(new TagNameFilter("tr"),new HasChildFilter(new TagNameFilter("td")));
			String htmlText=getHtmlText();
			Parser parser=new Parser();
			parser.setInputHTML(htmlText);
			NodeList nodeList=parser.parse(andFilter);
			companyInfo=new CompanyInfo();
			for(int i=0;i<nodeList.size();i++){
				Node node=nodeList.elementAt(i);
				NodeList childList=node.getChildren();
				for(int j=0;j<childList.size();j++){
					Node child=childList.elementAt(j);
					if(child==null || child.toPlainTextString().trim().equals("")){
						continue;
					}
					
					System.out.println(child.toPlainTextString());
					if(child.toPlainTextString().trim().equals("注册号")||child.toPlainTextString().trim().equals("统一社会信用代码")){
						while(child.getNextSibling()!=null && child.getNextSibling().toPlainTextString().trim().equals("")){
							child=child.getNextSibling();
					}
					if(child.getNextSibling()==null && child.toPlainTextString().trim().equals("")){
						continue;
					}
						companyInfo.setEntNo(child.getNextSibling().toPlainTextString().trim().replace("\n", "").replace("\t", ""));
						continue;
					}
					if(child.toPlainTextString().trim().equals("名称")){
						while(child.getNextSibling()!=null && child.getNextSibling().toPlainTextString().trim().equals("")){
							child=child.getNextSibling();
					}
					if(child.getNextSibling()==null && child.toPlainTextString().trim().equals("")){
						continue;
					}
						companyInfo.setName(child.getNextSibling().toPlainTextString().trim().replace("\n", "").replace("\t", ""));
						continue;
					}
					if(child.toPlainTextString().trim().equals("类型")){
						while(child.getNextSibling()!=null && child.getNextSibling().toPlainTextString().trim().equals("")){
							child=child.getNextSibling();
					}
					if(child.getNextSibling()==null && child.toPlainTextString().trim().equals("") ){
						continue;
					}
						companyInfo.setType(child.getNextSibling().toPlainTextString().trim().replace("\n", "").replace("\t", ""));
						continue;
					}
					if(child.toPlainTextString().trim().equals("经营者") || child.toPlainTextString().trim().equals("法定代表人")){
						while(child.getNextSibling()!=null && child.getNextSibling().toPlainTextString().trim().equals("")){
							child=child.getNextSibling();
					}
					if(child.getNextSibling()==null && child.toPlainTextString().trim().equals("")){
						continue;
					}
						companyInfo.setOperator(child.getNextSibling().toPlainTextString().trim().replace("\n", "").replace("\t", ""));
						continue;
					}
					if(child.toPlainTextString().trim().equals("经营场所") || child.toPlainTextString().trim().equals("住所") ){
						while(child.getNextSibling()!=null && child.getNextSibling().toPlainTextString().trim().equals("")){
							child=child.getNextSibling();
					}
					if(child.getNextSibling()==null && child.toPlainTextString().trim().equals("")){
						continue;
					}
						companyInfo.setAddress(child.getNextSibling().toPlainTextString().trim().replace("\n", "").replace("\t", ""));
						continue;
					}
					if(child.toPlainTextString().trim().equals("组成形式")){
						while(child.getNextSibling()!=null && child.getNextSibling().toPlainTextString().trim().equals("")){
							child=child.getNextSibling();
					}
					if(child.getNextSibling()==null && child.toPlainTextString().trim().equals("")){
						continue;
					}
						companyInfo.setGroupType(child.getNextSibling().toPlainTextString().trim().replace("\n", "").replace("\t", ""));
						continue;
					}
					if(child.toPlainTextString().trim().equals("注册日期")){
						while(child.getNextSibling()!=null && child.getNextSibling().toPlainTextString().trim().equals("")){
							child=child.getNextSibling();
					}
					if(child.getNextSibling()==null && child.toPlainTextString().trim().equals("")){
						continue;
					}
						companyInfo.setRegDate(child.getNextSibling().toPlainTextString().trim().replace("\n", "").replace("\t", ""));
						continue;
					}
					if(child.toPlainTextString().trim().equals("营业期限自")){
						while(child.getNextSibling()!=null && child.getNextSibling().toPlainTextString().trim().equals("")){
							child=child.getNextSibling();
					}
					if(child.getNextSibling()==null && child.toPlainTextString().trim().equals("")){
						continue;
					}
						companyInfo.setOptDateStart(child.getNextSibling().toPlainTextString().trim().replace("\n", "").replace("\t", ""));
						continue;
					}
					if(child.toPlainTextString().trim().equals("营业期限至")){
						while(child.getNextSibling()!=null && child.getNextSibling().toPlainTextString().trim().equals("")){
								child=child.getNextSibling();
						}
						if(child.getNextSibling()==null && child.toPlainTextString().trim().equals("")){
							continue;
						}
						companyInfo.setOptDateEnd(child.getNextSibling().toPlainTextString().trim().replace("\n", "").replace("\t", ""));
						continue;
					}
					if(child.toPlainTextString().trim().equals("注册资本")){
						while(child.getNextSibling()!=null && child.getNextSibling().toPlainTextString().trim().equals("")){
							child=child.getNextSibling();
					}
					if(child.getNextSibling()==null && child.toPlainTextString().trim().equals("")){
						continue;
					}
						companyInfo.setFound(child.getNextSibling().toPlainTextString().trim().replace("\n", "").replace("\t", ""));
						continue;
					}
					if(child.toPlainTextString().trim().equals("成立日期")){
						while(child.getNextSibling()!=null && child.getNextSibling().toPlainTextString().trim().equals("")){
							child=child.getNextSibling();
					}
					if(child.getNextSibling()==null && child.toPlainTextString().trim().equals("")){
						continue;
					}
						companyInfo.setBuildDate(child.getNextSibling().toPlainTextString().trim().replace("\n", "").replace("\t", ""));
						continue;
					}
					if(child.toPlainTextString().trim().equals("经营范围")){
						while(child.getNextSibling()!=null && child.getNextSibling().toPlainTextString().trim().equals("")){
							child=child.getNextSibling();
					}
					if(child.getNextSibling()==null && child.toPlainTextString().trim().equals("")){
						continue;
					}
						companyInfo.setOptScope(child.getNextSibling().toPlainTextString().trim().replace("\n", "").replace("\t", ""));
						continue;
					}
					if(child.toPlainTextString().trim().equals("登记机关")){
						while(child.getNextSibling()!=null && child.getNextSibling().toPlainTextString().trim().equals("")){
							child=child.getNextSibling();
					}
					if(child.getNextSibling()==null && child.toPlainTextString().trim().equals("")){
						continue;
					}
						companyInfo.setRegDepartment(child.getNextSibling().toPlainTextString().trim().replace("\n", "").replace("\t", ""));
						continue;
					}
					if(child.toPlainTextString().trim().equals("核准日期")){
						while(child.getNextSibling()!=null && child.getNextSibling().toPlainTextString().trim().equals("")){
							child=child.getNextSibling();
					}
					if(child.getNextSibling()==null && child.toPlainTextString().trim().equals("")){
						continue;
					}
						companyInfo.setCheckDate(child.getNextSibling().toPlainTextString().trim().replace("\n", "").replace("\t", ""));
						continue;
					}
					if(child.toPlainTextString().trim().equals("登记状态")){
						while(child.getNextSibling()!=null && child.getNextSibling().toPlainTextString().trim().equals("")){
							child=child.getNextSibling();
					}
					if(child.getNextSibling()==null && child.toPlainTextString().trim().equals("")){
						continue;
					}
						companyInfo.setRegStatus(child.getNextSibling().toPlainTextString().trim().replace("\n", "").replace("\t", ""));
						continue;
					}
				}
			}
			System.out.println(companyInfo.toString());
		} catch (ParserException e) {
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public CompanyInfo getCompanyInfo(){
		return this.companyInfo;
	}
}
