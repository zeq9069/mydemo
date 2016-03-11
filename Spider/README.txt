 /* 
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