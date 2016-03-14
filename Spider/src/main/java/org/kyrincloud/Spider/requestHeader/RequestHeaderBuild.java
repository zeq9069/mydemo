package org.kyrincloud.Spider.requestHeader;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

/**
 * 请求头得构建
 * @author kyrin
 *
 */
public class RequestHeaderBuild {

	//请求公司搜索列表页header
	public static Header[] buildCompanyListHeader(String currentProvince,Header ...header){
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
		public static Header[] buildCompanyDetailsHeader(Header... header) {
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
		
		/*//公司详情页header
		public static Header[] buildCompanyDetailsXXHeader(Header ...header){
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
		*/
		public static Header[] buildCheckCodeRequestHeader(Header ...header){
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
		
		public static Header[] buildCommonHeader(){
			Header[] headers=new Header[7];
			headers[0]=new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			headers[1]=new BasicHeader("Accept-Encoding", "gzip, deflate");
			headers[2]=new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
			headers[3]=new BasicHeader("Cache-Control", "max-age=0");
			headers[4]=new BasicHeader("Connection", "keep-alive");
			headers[5]=new BasicHeader("Upgrade-Insecure-Requests","1");
			//headers[6]=new BasicHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36");
			headers[6]=new BasicHeader("Host", "qyxy.baic.gov.cn");
			return headers;
		}

	
}
