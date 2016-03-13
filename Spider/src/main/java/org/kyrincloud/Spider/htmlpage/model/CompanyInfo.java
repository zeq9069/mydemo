package org.kyrincloud.Spider.htmlpage.model;

/**
 * 公司实体
 * @author kyrin
 *
 */
public class CompanyInfo {

	//公司名
	private String name;
	
	//工商注册号/统一社会信用代码
	private String entNo;
	
	//类型
	private String type;
	
	//经营场所/住所
	private String address;
	
	//组成形式
	private String groupType;
	
	//经营范围
	private String optScope;
	
	//登记机关
	private String regDepartment;
	
	//登记状态
	private String regStatus;
	
	//经营者/法定代表人
	private String operator;
	
	//注册日期
	private String regDate;
	
	//核准日期
	private String checkDate;
	
	//注册资本
	private String found;
	
	//成立日期
	private String buildDate;

	//营业期限自
	private String optDateStart;
	
	//营业期限至
	private String optDateEnd;
				      
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEntNo() {
		return entNo;
	}

	public void setEntNo(String entNo) {
		this.entNo = entNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getOptScope() {
		return optScope;
	}

	public void setOptScope(String optScope) {
		this.optScope = optScope;
	}

	public String getRegDepartment() {
		return regDepartment;
	}

	public void setRegDepartment(String regDepartment) {
		this.regDepartment = regDepartment;
	}

	public String getRegStatus() {
		return regStatus;
	}

	public void setRegStatus(String regStatus) {
		this.regStatus = regStatus;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	
	
	public String getFound() {
		return found;
	}

	public void setFound(String found) {
		this.found = found;
	}

	public String getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	public String getOptDateStart() {
		return optDateStart;
	}

	public void setOptDateStart(String optDateStart) {
		this.optDateStart = optDateStart;
	}

	public String getOptDateEnd() {
		return optDateEnd;
	}

	public void setOptDateEnd(String optDateEnd) {
		this.optDateEnd = optDateEnd;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	@Override
	public String toString(){
		String result = String.format("[ name = %s , entNo = %s , groupType = %s , optScope = %s , regDate = %s , regDepartment = %s , regStatus = %s , operator = %s , type = %s , address = %s , found = %s , optDateStart = %s , optDateEnd = %s , buildDate = %s]" 
				,name , entNo  , groupType  , optScope  , regDate , regDepartment , regStatus  , operator , type  , address , found , optDateStart, optDateEnd , buildDate );
		return result;
	}
	
}
