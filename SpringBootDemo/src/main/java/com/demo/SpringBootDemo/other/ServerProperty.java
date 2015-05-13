package com.demo.SpringBootDemo.other;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="my")
public class ServerProperty {
	
	//验证器，可自己定义，实现接口configurationPropertiesValidator
	@NotNull
	private  List<String> server=new ArrayList<String>();

	public  List<String> getServer(){
		return server;
	}

	public void setServer(List<String> server) {
		this.server = server;
	}
	
	
	
}
