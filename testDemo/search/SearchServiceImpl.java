package cn.ncss.jym.graduate.service.impl.search;


import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

public class SearchServiceImpl {

	private static RestTemplate rest=new RestTemplate();
	private static Logger logger=Logger.getLogger(SearchServiceImpl.class);
	 private static PublicService pubService=new PublicService();
	
	
	public static void searchAll(){
		logger.info("search all ");
		rest=new RestTemplate();
		HttpHeaders header=new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		String json="{\"query\":{\"match_all\":{}}}";
		HttpEntity<String> entity=new HttpEntity<String>(json,header);
		JSONObject obj=rest.postForObject(pubService.search_url, entity, JSONObject.class);
		System.out.println("------"+obj.toJSONString());
	}
	
	public static void searchOne(String id){
		logger.info("search one for id:"+id);
		rest=new RestTemplate();
		JSONObject obj=rest.getForObject(pubService.getSearchOneUrl(id), JSONObject.class);
		System.out.println("------"+obj.toJSONString());
	}
	public static void searchOneFields(String id,List<String> filedsList){
	logger.info("search one contant fields ");
		rest=new RestTemplate();
		JSONObject obj=rest.getForObject(pubService.getSearchOneContantFieldsUrl(id, filedsList), JSONObject.class);
		System.out.println("------"+obj.toJSONString());
	}
	public static void searchOneNotFields(String id,List<String> filedsList){
		logger.info("search one don't contant fields ");
			rest=new RestTemplate();
			JSONObject obj=rest.getForObject(pubService.getSearchOneNotContantFieldsUrl(id, filedsList), JSONObject.class);
			System.out.println("------"+obj.toJSONString());
		}
	public static void deleteOneUrl(String id){
		logger.info("delete one  ");
			rest=new RestTemplate();
			rest.delete(pubService.deleteOneUrl(id), JSONObject.class);
			System.out.println("------");
		}
	public static void updateOneUrl(String id){
		/**
		 * 中文乱码问题
		 */
		logger.info("delete one  ");
			rest=new RestTemplate();
			HttpHeaders header=new HttpHeaders();
			List<Charset> list=new ArrayList<Charset>();
			list.add(Charset.forName("utf-8"));
			header.setAcceptCharset(list);
			header.setContentType(MediaType.APPLICATION_JSON);
			String json="{\"doc\":{\"xm\":\"赵剑锋12\"},\"update_as_upsert\":false}";
			HttpEntity<String> entity=new HttpEntity<String>(json,header);
			JSONObject obj=rest.postForObject(pubService.updateOneUrl(id),entity, JSONObject.class);
			System.out.println("------"+obj.toJSONString());
		}
	
		
	
	
	public static void main(String s[]){
		//1,
		/*searchAll();*/
		//2
		searchOne("10152301171647");		
		//3.
		/*
		List<String> filedsList=new ArrayList<String>();
		filedsList.add("ksh");
		filedsList.add("xm");
		searchOneFields("10150222110875",filedsList);*/
		//4
	/*	List<String> filedsList=new ArrayList<String>();
		filedsList.add("ksh");
		filedsList.add("xm");
		searchOneNotFields("10150222110875",filedsList);*/
		//5
		/*deleteOneUrl("10150222110875");*/
		//6
		updateOneUrl("10152301171647");
	} 
}
