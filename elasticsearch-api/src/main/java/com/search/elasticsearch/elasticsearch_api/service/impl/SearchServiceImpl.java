package com.search.elasticsearch.elasticsearch_api.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.search.elasticsearch.elasticsearch_api.http.HttpUtils;
import com.search.elasticsearch.elasticsearch_api.service.PublicService;

/**
 * 搜索服务实现类
 * @author zeq
 *
 */
public class SearchServiceImpl {

	private static Logger logger = Logger.getLogger(SearchServiceImpl.class);
	private static PublicService pubService = new PublicService();
	private static HttpEntity entity;

	public static void searchAll() {
		logger.info("search all ");

		String url = pubService.getSearchUrl();
		String json = "{\"query\":{\"match_all\":{}}}";
		StringEntity strEntity = new StringEntity(json, "UTF-8");
		entity = HttpUtils.post(url, strEntity);
		print(entity);
	}

	public static void searchOne(String id) {

		logger.info("search one for id:" + id);
		String url = pubService.getSearchOneUrl(id);
		entity = HttpUtils.get(url);
		print(entity);
	}

	public static void searchOneFields(String id, List<String> filedsList) {
		logger.info("search one contant fields ");
		String url = pubService.getSearchOneContantFieldsUrl(id, filedsList);
		entity = HttpUtils.get(url);
		print(entity);
	}

	public static void searchOneNotFields(String id, List<String> filedsList) {
		logger.info("search one don't contant fields ");
		String url = pubService.getSearchOneNotContantFieldsUrl(id, filedsList);
		entity = HttpUtils.get(url);
		print(entity);

	}

	public static void deleteOneUrl(String id) {
		logger.info("delete one  ");
		String url = pubService.deleteOneUrl(id);
		entity = HttpUtils.get(url);
		print(entity);

	}

	public static void updateOneUrl(String id) {
		/**
		 * restTemplate中存在中文乱码问题
		 */
		logger.info("update one id:" + id);
		String url = pubService.updateOneUrl(id);
		String json = "{\"doc\":{\"xm\":\"李璐12\"},\"update_as_upsert\":false}";
		StringEntity strEntity = new StringEntity(json, "UTF-8");
		entity = HttpUtils.post(url, strEntity);
		print(entity);

	}

	public static void main(String s[]) {
		//1,
		/*searchAll();*/
		//2
		searchOne("10152301171647");
		//3.

		/*List<String> filedsList = new ArrayList<String>();
		filedsList.add("ksh");
		filedsList.add("xm");
		searchOneFields("10150222110875", filedsList);*/
		//4
		/*List<String> filedsList = new ArrayList<String>();
		filedsList.add("ksh");
		filedsList.add("xm");
		searchOneNotFields("10150222110875", filedsList);*/
		//5
		/*deleteOneUrl("10150222110875");*/
		//6
		updateOneUrl("10152301171647");
	}

	private static void print(HttpEntity en) {
		try {
			System.out.println("----------执行--------------");
			System.out.println(EntityUtils.toString(en));
			System.out.println("----------结束--------------");
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
