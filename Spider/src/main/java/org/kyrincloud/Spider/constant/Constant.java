package org.kyrincloud.Spider.constant;

/**
 * url常量
 * @author kyrin
 *
 */
public class Constant {
	
	//字母+数字
		public static final String CheckCodeCaptcha="http://qyxy.baic.gov.cn//CheckCodeCaptcha";
			
		//逻辑运算（10以内的加减乘除）
		public static final  String CheckCodeYunSuan="http://qyxy.baic.gov.cn/CheckCodeYunSuan";
		
		//搜索页的逻辑运算验证码
		public static final  String verifycode="http://tjcredit.gov.cn/verifycode?date=";
		
		//搜索index+province
		public static final  String index="http://qyxy.baic.gov.cn/";
		
		//company_list
		public static final  String company_list="http://qyxy.baic.gov.cn/gjjbj/gjjQueryCreditAction!getBjQyList.dhtml";
		
		//company详细信息
		public static final  String company_details="http://qyxy.baic.gov.cn/gjjbj/gjjQueryCreditAction!openEntInfo.dhtml";
		
		//company_details登记信息
		public static final  String company_details_djxx="http://qyxy.baic.gov.cn/gjjbj/gjjQueryCreditAction!biangengFrame.dhtml";//ent_id=&clear=true&timeStamp=
		//company_details动产抵押
		public static final  String company_details_dcdy="http://qyxy.baic.gov.cn/gjjbjTab/gjjTabQueryCreditAction!dcdyFrame.dhtml";//entId=&clear=true&timeStamp=;
		//company_details股权出质登记信息
		public static final  String company_details_bqczdjxx="http://qyxy.baic.gov.cn/gdczdj/gdczdjAction!gdczdjFrame.dhtml";//entId=&clear=true&timeStamp=;
		//company_details行政处罚
		public static final  String company_details_xzcf="http://qyxy.baic.gov.cn/gsgs/gsxzcfAction!list.dhtml";//entId=&clear=true&timeStamp=
		//company_details经营异常
		public static final  String company_details_jyyc="http://qyxy.baic.gov.cn/gsgs/gsxzcfAction!list_jyycxx.dhtml";//entId=&clear=true&timeStamp=
		//company_details严重违法
		public static final  String company_details_yzwf="http://qyxy.baic.gov.cn/gsgs/gsxzcfAction!list_yzwf"
				+ "xx.dhtml";//ent_id=&clear=true&timeStamp=
		//company_details抽查检查
		public static final  String company_details_ccjc="http://qyxy.baic.gov.cn/gsgs/gsxzcfAction!list_ccjcxx.dhtml";//ent_id=&clear=true&timeStamp=
		

}
