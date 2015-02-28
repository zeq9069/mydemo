package com.search.elasticsearch.elasticsearch_api.service;

import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.search.elasticsearch.elasticsearch_api.util.AppConfigUtils;
import com.search.elasticsearch.elasticsearch_api.util.GlobalConstant;

/**
 * elasticsearch公共URL
 * @author lenovo
 *
 */
public final class PublicService {

	private static String server;
	private static String port;
	private static String index;
	private static String type;
	private static Properties property;
	public String search_url;
	/*****************get api url***********************/
	public String get_one_url;
	public String get_one_only_fields_url;
	public String get_one_not_fields_url;

	/****************delete api url*********************/
	public String delete_one_url;
	/****************update api url********************/
	public String update_one_url;

	/****************multi get api url******************/
	public String multi_get_url;
	public String multi_get_type_url;

	/****************bulk api url*********************/
	/****************delete by query api**************/
	public String delete_by_query_url;
	/****************term vectors url*****************/
	public String term_vector_url;

	public PublicService() {
		property = AppConfigUtils.load();
		server = property.getProperty(GlobalConstant.ELASTICSEARCH_SERVER_HOST);
		port = property.getProperty(GlobalConstant.ELASTICSEARCH_SERVER_PORT);
		index = property.getProperty(GlobalConstant.ELASTICSEARCH_INDEX);
		type = property.getProperty(GlobalConstant.ELASTICSEARCH_INDEX_TYPE);
	}

	/**
	 * GET
	 *  Q请求参数很多，
	 *  <1>
	 *  {
	 *  	"query":{
	 *  		"match_all":{}
	 *  	}
	 *  }
	 *  <2>
	 *  {
	 * 	"query": { 
	 * 			"match": { "name": "John jack Mac" }
	 * 	 },
	 * 	"sort":{
	 * 			"ksh":{"order":"desc"}
	 * 	},
	 * 	"from":10,
	 * 	"size":100,
	 * 	"_source": ["ksh", "xm"]
	 *}
	 *  <3>
	 *  localhost:9200/bank/_search?q=*&pretty
	 *  <4>
	 *  {
	 *		"query": {
	 *			"bool": {
	 * 				"must|should|must_not": [
	 * 						{ "match": { "address": "mill" } },
	 *							{ "match": { "address": "lane" } }
	 *     				]
	 *			}
	 *		}
	 *}
	 *<4>filter
	 *
	 *{
	 *	"query": {
	 *		"filtered": {
	 *				"query": { "match_all": {} },
	 * 					"filter": {
	 * 						"range": {
	 * 							"balance": {
	 *      							 "gte": 20000,
	 *      							 "lte": 30000
	 *      					 }
	 *  					}
	 *					}
	 *		}
	 *	}
	 *}
	 *  <5>Aggregations 
	 *  {
	 *	 "size": 0,
	 *	 "aggs": {
	 *		"group_by_state": {
	 * 					"terms": {
	 *   					"field": "state",
	 *  					"order": {
	 *      					"ksh": "desc"
	 *    					}
	 * 					}
	 *				}
	 *		}
	 *}
	 * @return
	 */
	public String getSearchUrl() {
		search_url = "http://" + server + ":" + port + "/" + index + "/" + type + "/" + "_search?pretty";
		return search_url;
	}

	/**
	 * GET 
	 *无请求参数 
	 * @param id  _source ID
	 * @return
	 */
	public String getSearchOneUrl(String id) {
		get_one_url = "http://" + server + ":" + port + "/" + index + "/" + type + "/" + id + "/_source?pretty";
		return get_one_url;
	}

	/**
	 * GET
	 * 无请求参数
	 * @param id _source ID
	 * @param filedsList 查询的字段列表
	 * @return
	 */
	public String getSearchOneContantFieldsUrl(String id, List<String> filedsList) {
		get_one_only_fields_url = "http://" + server + ":" + port + "/" + index + "/" + type + "/" + id
				+ "/_source?pretty&_source_include=";
		String fields = StringUtils.join(filedsList, ",");
		get_one_only_fields_url += fields;
		return get_one_only_fields_url;
	}

	/**
	 * GET
	 * 无请求参数
	 * @param id _source ID
	 * @param filedsList 排除的字段列表
	 * @return
	 */
	public String getSearchOneNotContantFieldsUrl(String id, List<String> filedsList) {
		get_one_only_fields_url = "http://" + server + ":" + port + "/" + index + "/" + type + "/" + id
				+ "/_source?pretty&_source_exclude=";
		String fields = StringUtils.join(filedsList, ",");
		get_one_only_fields_url += fields;
		return get_one_only_fields_url;
	}

	/**
	 * DELETE
	 * 无请求参数
	 * @param id _source ID
	 * @return
	 */
	public String deleteOneUrl(String id) {
		delete_one_url = "http://" + server + ":" + port + "/" + index + "/" + type + "/" + id + "?timeout=3ms";
		return delete_one_url;
	}

	/**
	 * POST
	 *{
	 * 	"doc":{
	 * 		"name":"new_name"
	 * 	},
	 * 	"doc_as_upsert":false   //true意味着执行insert操作
	 *}
	 * @param id _source ID
	 * @return
	 */
	public String updateOneUrl(String id) {
		update_one_url = "http://" + server + ":" + port + "/" + index + "/" + type + "/" + id + "/_update";
		return update_one_url;
	}

	/**
	 * GET
	 * <1>
	 *{
	 * 	"docs":[
	 * 			{"id":1},
	 * 			{"id":2}
	 * 		   ]
	 *}
	 * <2>
	 *{
	 * 	“ids”:["1","2"]
	 *}
	 * 
	 * @return
	 */
	public String getMultiGetUrl() {
		multi_get_url = "http://" + server + ":" + port + "/" + index + "/" + type + "/_mget'";
		return multi_get_url;
	}

	/**
	 * GET
	 * <1>
	 *{
	 * 	"docs":[
	 * 		{
	 * 		  "_type":"type",
	 * 		  "_id":1
	 * 		},
	 * 		{
	 * 		  "_type":"type",
	 * 		  "_id":2
	 * 		}
	 * 	]
	 *}
	 * @return
	 */
	public String getMultiGetTypeUrl() {
		multi_get_type_url = "http://" + server + ":" + port + "/" + index + "/_mget";
		return multi_get_type_url;
	}

	/**
	 * 使用DELETE请求
	 * 参数：
	 *{
	 *  “query”:{
	 * 		"term":{"user":"jack"}
	 *	}
	 *}
	 * 
	 * @return
	 */
	public String getDeleteByQueryUrl() {
		delete_by_query_url = "http://" + server + ":" + port + "/" + index + "/_query";
		return delete_by_query_url;
	}

}
