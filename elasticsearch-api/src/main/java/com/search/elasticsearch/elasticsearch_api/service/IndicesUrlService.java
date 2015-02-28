package com.search.elasticsearch.elasticsearch_api.service;

import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.search.elasticsearch.elasticsearch_api.util.AppConfigUtils;
import com.search.elasticsearch.elasticsearch_api.util.GlobalConstant;

/**
 * elasticsearch 索引相关api
 * @author zeq
 *
 */
public class IndicesUrlService {

	private static String server;
	private static String port;
	private static Properties property;
	/*****************create index url***********************/
	public String create_index_url;

	/*****************delete index url***********************/
	public String delete_index_url;
	public String delete_all_index_url;

	/*****************get index url**************************/
	public String get_index_url;
	public String get_all_index_url;
	public String get_paramOfindex_url;

	/*****************open/close index url*******************/
	public String open_index_url;
	public String close_index_url;

	/*****************put mapping****************************/
	public String put_mapping_url;
	public String multi_put_mapping_url;

	/*****************get mapping url************************/
	public String get_one_mapping_url;

	/*****************get field mapping**********************/
	//host:port/{index}/{type}/_mapping/field/{field} 
	public String get_fields_mapping_url;

	/*****************delete mapping*************************/
	//[DELETE] /{index}/{type}/_mapping
	//[DELETE] /{index}/_mapping/{type}
	public String delete_mapping_url;

	/******************indices aliases**********************/
	public String alias_url;
	public String add_one_alias_url;
	public String delete_one_alias_url;
	public String get_all_aliases_url;
	public String containt_alias_url;

	/******************update indices setting***************/
	public String update_one_setting_url;
	public String update_all_setting_url;

	/******************get settings*************************/
	public String get_one_settings_url;
	public String get_all_settings_url;

	/******************index template***********************/
	public String add_one_template_url;
	public String delete_one_template_url;
	public String get_templates_url;

	/******************indices status***********************/
	public String get_multi_status_url;
	public String get_all_status_url;

	/*******************indices stats***********************/
	public String get_all_stats_url;
	public String get_multi_stats_url;

	/*******************indices segments********************/
	public String get_all_segments_url;
	public String get_one_segments_url;
	public String get_multi_segments_url;

	/*******************indices recovery********************/
	public String get_one_recovery_url;
	public String get_multi_recovery_url;
	public String get_all_recovery_url;

	/*******************clear cache*************************/
	public String get_clearOneIndexCache_url;
	public String get_clearMuitlIndexCache_url;
	public String get_clearAllIndexCache_url;

	/*******************flush*******************************/
	public String get_flushOne_url;
	public String get_flushMulti_url;
	public String get_flushAll_url;

	/*******************refresh*****************************/
	public String get_refreshOne_url;
	public String get_refreshMulti_url;
	public String get_refreshAll_url;

	/********************optimize****************************/
	public String get_optimizeOne_url;
	public String get_optimizeMulti_url;
	public String get_optimizeAll_url;

	/********************upgrade************************************/
	public String upgrade_url;
	public String upgrade_status_url;

	public IndicesUrlService() {
		property = AppConfigUtils.load();
		server = property.getProperty(GlobalConstant.ELASTICSEARCH_SERVER_HOST);
		port = property.getProperty(GlobalConstant.ELASTICSEARCH_SERVER_PORT);
	}

	/**
	 * PUT
	 * <1>setting
	 * {
	 * 	 "setting":{
	 * 	 	"index":{
	 * 			"number_of_shards":3,//默认为5
	 * 			"number_of_replicas":2//默认为1
	 * 		}
	 * 	  }
	 * }
	 * <2>aliases
	 * {
	 * 	 "aliases":{
	 * 	 	"aliases_1":{},
	 * 		"aliases_2":{
	 * 			"filter":{
	 * 				"term":{"user":"jack"}
	 * 			},
	 * 			"routing":"jack"
	 * 		}
	 *    }
	 * }
	 * <3>creation data(added in 1.4.0.Betal)
	 * {
	 *   "creation_date":1407751337000 //单位是毫秒
	 * }
	 * @param index
	 * @return
	 */
	public String getCreateIndexUrl(String index) {
		create_index_url = "http://" + server + "/" + port + "/" + index;
		return create_index_url;
	}

	/**
	 * DELETE
	 * 无请求参数
	 * @return
	 */
	public String getDeleteIndexUrl(String index) {
		delete_index_url = "http://" + server + "/" + port + "/" + index;
		return delete_index_url;
	}

	/**
	 * DELETE
	 * 无请求参数(为了安全期间，在setting配置文件设置禁用通配符或者_all进行删除)
	 * @return
	 */
	/*public String getDeleteAllIndexUrl() {
		//_all or *
		delete_all_index_url = "http://" + server + "/" + port + "/_all";
		return delete_all_index_url;
	}*/

	/**
	 * GET
	 * 无请求参数
	 * @param index
	 * @return
	 */
	public String getOneIndexUrl(String index) {
		get_index_url = "http://" + server + "/" + port + "/" + index;
		return get_index_url;
	}

	/**
	 * GET
	 * 无请求参数
	 * @param index
	 * @return
	 */
	public String getAllIndexUrl() {
		//* or _all
		get_all_index_url = "http://" + server + "/" + port + "/_all";
		return get_all_index_url;
	}

	/**
	 * GET
	 * 无请求参数
	 * @param index 
	 * @param param _settings or mappings
	 * @return
	 */
	public String getParamerOfIndexUrl(String index, String param) {
		//_settings or mappings
		get_paramOfindex_url = "http://" + server + "/" + port + "/" + index + "/" + param;
		return get_paramOfindex_url;
	}

	/**
	 * POST
	 * 无请求参数
	 * @param index
	 * @return
	 */
	public String getOpenIndexUrl(String index) {
		open_index_url = "http://" + server + "/" + port + "/" + index + "/_open";
		return open_index_url;
	}

	/**
	 * POST
	 * 无请求参数
	 * @param index
	 * @return
	 */
	public String getCloseIndexUrl(String index) {
		open_index_url = "http://" + server + "/" + port + "/" + index + "/_close";
		return open_index_url;
	}

	/**
	 * PUT
	 * <1>
	 *{
	 *	"tweet" : {
	 *   "properties" : {
	 *       "message" : {"type" : "string", "store" : true }
	 *    }
	 *   }
	 *}
	 * @param index
	 * @param type
	 * @return
	 */
	public String getPutMappingUrl(String index, String type) {
		put_mapping_url = "http://" + server + "/" + port + "/" + index + "/_mapping/" + type;
		return put_mapping_url;
	}

	/**
	 * PUT
	 * <1>
	 *{
	 *	"tweet" : {
	 *   "properties" : {
	 *       "message" : {"type" : "string", "store" : true }
	 *    }
	 *   }
	 *}
	 * @param index
	 * @param type
	 * @return
	 */
	public String getMultiPutMappingUrl(List<String> indexList, String type) {
		String index = StringUtils.join(indexList, ",");
		multi_put_mapping_url = "http://" + server + "/" + port + "/" + index + "/_mapping/" + type;
		return multi_put_mapping_url;
	}

	/**
	 * GET
	 * 无请求参数
	 * @param index
	 * @param type
	 * @return
	 */
	public String getOneMappingUrl(String index, String type) {
		get_one_mapping_url = "http://" + server + "/" + port + "/" + index + "/_mapping/" + type;
		return get_one_mapping_url;
	}

	/**
	 * GET
	 * 无请求参数
	 * @param index
	 * @param type
	 * @param fields
	 * @return
	 */
	public String getFieldsMappingUrl(String index, String type, List<String> fields) {
		String field = StringUtils.join(fields, ",");
		get_fields_mapping_url = "http://" + server + "/" + port + "/" + index + "/_mapping/" + type + "/field/"
				+ field;
		return get_fields_mapping_url;
	}

	/**
	 * DELETE
	 * 无请求参数
	 * @param index
	 * @param type
	 * @return
	 */
	public String getDeleteMappingUrl(String index, String type) {
		delete_mapping_url = "http://" + server + ":" + port + "/" + index + "/_mapping/" + type;
		return delete_mapping_url;
	}

	/**
	 * POST
	 * <1>ADD
	 * {
	 *  "actions" : [
	 *    { "add" : { "index" : "test1", "alias" : "alias1" } }
	 *  ]
	 *}
	 *<2>REMOVE
	 * {
	 *  "actions" : [
	 *    { "remove" : { "index" : "test1", "alias" : "alias1" } }
	 *  ]
	 *}
	 *<3> add and remove
	 *{
	 * "actions" : [
	 *   { "remove" : { "index" : "test1", "alias" : "alias1" } },
	 *   { "add" : { "index" : "test1", "alias" : "alias2" } }
	 * ]
	 *}
	 *<4>multi add
	 *{
	 * "actions" : [
	 *   { "add" : { "index" : "test1", "alias" : "alias1" } },
	 *   { "add" : { "index" : "test2", "alias" : "alias1" } }
	 * ]
	 *}
	 * @return
	 */
	public String getAliasesUrl() {
		alias_url = "http://" + server + ":" + port + "/_aliases";
		return alias_url;
	}

	/**
	 * PUT
	 * @param index
	 * @param name
	 * @return
	 */
	public String getAddOneAliasUrl(String index, String name) {
		add_one_alias_url = "http://" + server + ":" + port + "/" + index + "/_alias/" + name;
		return add_one_alias_url;
	}

	/**
	 * DELETE
	 * @param index
	 * @param name
	 * @return
	 */
	public String getDeleteOneAliasUrl(String index, String name) {
		add_one_alias_url = "http://" + server + ":" + port + "/" + index + "/_alias/" + name;
		return add_one_alias_url;
	}

	/**
	 * PUT
	 * @param index
	 * @param name
	 * @return
	 */
	public String getAllAliasesUrl(String index) {
		get_all_aliases_url = "http://" + server + ":" + port + "/" + index + "/_aliases/*";
		return get_all_aliases_url;
	}

	/**
	 * GET
	 * 获取包含别名name的索引
	 * @param index
	 * @param name
	 * @return
	 */
	public String getContaintAliasUrl(String name) {
		get_all_aliases_url = "http://" + server + ":" + port + "/_alias/" + name;
		return get_all_aliases_url;
	}

	/**
	 * PUT
	 * <1>
	 * {
	 *  "index" : {
	 *     "number_of_replicas" : 4
	 *  }
	 *}
	 * @param index
	 * @return
	 */
	public String getUpdateOneIndexSetting(String index) {
		update_one_setting_url = "http://" + server + ":" + port + "/" + index + "/_setting";
		return update_one_setting_url;
	}

	/**
	 * PUT
	 * <1>
	 * {
	 *  "index" : {
	 *     "number_of_replicas" : 4
	 *  }
	 *}
	 * @param index
	 * @return
	 */
	public String getUpdateAllIndexSetting() {
		update_all_setting_url = "http://" + server + ":" + port + "/_settings";
		return update_all_setting_url;
	}

	/**
	 * GET
	 * @param index
	 * @return
	 */
	public String getOneSettingsUrl(String index) {
		get_one_settings_url = "http://" + server + ":" + port + "/" + index + "/_settings";
		return get_one_settings_url;
	}

	/**
	 * GET
	 * @param index
	 * @return
	 */
	public String getAllSettingsUrl() {
		get_all_settings_url = "http://" + server + ":" + port + "/_all/_settings";
		return get_all_settings_url;
	}

	/**
	 * PUT
	 * <1>
	 * {
	 *  "template" : "te*",
	 *  "settings" : {
	 *     "number_of_shards" : 1
	 *  },
	 *  "mappings" : {
	 *     "type1" : {
	 *        "_source" : { "enabled" : false }
	 *     }
	 *  }
	 *}
	 * @param template
	 * @return
	 */
	public String addOneIndexTemplateUrl(String template) {
		add_one_template_url = "http://" + server + ":" + port + "/_template/" + template;
		return add_one_template_url;
	}

	/**
	 * DELETE
	 * @param template
	 * @return
	 */
	public String deleteOneIndexTemplateUrl(String template) {
		delete_one_template_url = "http://" + server + ":" + port + "/_template/" + template;
		return delete_one_template_url;
	}

	/**
	 * GET
	 * @param template
	 * @return
	 */
	public String getIndexTemplateUrl(List<String> templates) {
		String template = StringUtils.join(templates, ",");
		get_templates_url = "http://" + server + ":" + port + "/_template/" + template;
		return get_templates_url;
	}

	/**
	 * GET
	 * @param index
	 * @return
	 */
	public String getMultiIndexStatusUrl(List<String> indexs) {
		String index = StringUtils.join(indexs, ",");
		get_multi_status_url = "http://" + server + ":" + port + "/" + index + "/_status";
		return get_multi_status_url;
	}

	/**
	 * GET
	 * @param index
	 * @return
	 */
	public String getAllStatusUrl() {
		get_all_status_url = "http://" + server + ":" + port + "/_status";
		return get_all_status_url;
	}

	/**
	 * GET
	 * @param index
	 * @return
	 */
	public String getAllStatsUrl() {
		get_all_stats_url = "http://" + server + ":" + port + "/_stats";
		return get_all_stats_url;
	}

	/**
	 * GET
	 * @param index
	 * @return
	 */
	public String getMultiIndexStatsUrl(List<String> indexs) {
		String index = StringUtils.join(indexs, ",");
		get_multi_stats_url = "http://" + server + ":" + port + "/" + index + "/_stats";
		return get_multi_stats_url;
	}

	/**
	 * GET
	 * @param index
	 * @return
	 */
	public String getOneIndexSegmentsUrl(String index) {
		get_multi_segments_url = "http://" + server + ":" + port + "/" + index + "/_segments";
		return get_multi_segments_url;
	}

	/**
	 * GET
	 * @param index
	 * @return
	 */
	public String getMultiIndexSegmentsUrl(List<String> indexs) {
		String index = StringUtils.join(indexs, ",");
		get_multi_segments_url = "http://" + server + ":" + port + "/" + index + "/_segments";
		return get_multi_segments_url;
	}

	/**
	 * GET
	 * @param index
	 * @return
	 */
	public String getAllSegmentsUrl() {
		get_all_segments_url = "http://" + server + ":" + port + "/_segments";
		return get_multi_segments_url;
	}

	/**
	 * GET
	 * @param index
	 * @return
	 */
	public String getOneIndexRecoveryUrl(String index) {
		get_multi_recovery_url = "http://" + server + ":" + port + "/" + index + "/_recovery";
		return get_multi_recovery_url;
	}

	/**
	 * GET
	 * @param index
	 * @return
	 */
	public String getMultiIndexRecoveryUrl(List<String> indexs) {
		String index = StringUtils.join(indexs, ",");
		get_multi_recovery_url = "http://" + server + ":" + port + "/" + index + "/_recovery";
		return get_multi_recovery_url;
	}

	/**
	 * GET
	 * @param index
	 * @return
	 */
	public String getAllRecoveryUrl() {
		get_all_recovery_url = "http://" + server + ":" + port + "/_recovery";
		return get_multi_recovery_url;
	}

	/**
	 * POST
	 * @param index
	 * @return
	 */
	public String getClearOneIndexCacheUrl(String index) {
		get_clearOneIndexCache_url = "http://" + server + ":" + port + "/" + index + "/_cache/clear";
		return get_clearOneIndexCache_url;
	}

	/**
	 * POST
	 * @param index
	 * @return
	 */
	public String getClearMultiIndexCacheUrl(List<String> indexs) {
		String index = StringUtils.join(indexs, ",");
		get_clearMuitlIndexCache_url = "http://" + server + ":" + port + "/" + index + "/_cache/clear";
		return get_clearMuitlIndexCache_url;
	}

	/**
	 * POST
	 * @param index
	 * @return
	 */
	public String getClearAllCacheUrl() {
		get_clearAllIndexCache_url = "http://" + server + ":" + port + "/_cache/clear";
		return get_clearAllIndexCache_url;
	}

	/**
	 * POST
	 * @param index
	 * @return
	 */
	public String getFlushOneUrl(String index) {
		get_flushOne_url = "http://" + server + ":" + port + "/" + index + "/_flush";
		return get_flushOne_url;
	}

	/**
	 * POST
	 * @param index
	 * @return
	 */
	public String getFlushMultiUrl(List<String> indexs) {
		String index = StringUtils.join(indexs, ",");
		get_flushMulti_url = "http://" + server + ":" + port + "/" + index + "/_flush";
		return get_flushOne_url;
	}

	/**
	 * POST
	 * @param index
	 * @return
	 */
	public String getFlushAllUrl() {
		get_flushAll_url = "http://" + server + ":" + port + "/_flush";
		return get_flushAll_url;
	}

	/**
	 * POST
	 * @param index
	 * @return
	 */
	public String getRefreshOneUrl(String index) {
		get_refreshOne_url = "http://" + server + ":" + port + "/" + index + "/_refresh";
		return get_refreshOne_url;
	}

	/**
	 * POST
	 * @param index
	 * @return
	 */
	public String getRefreshMultiUrl(List<String> indexs) {
		String index = StringUtils.join(indexs, ",");
		get_refreshMulti_url = "http://" + server + ":" + port + "/" + index + "/_refresh";
		return get_refreshOne_url;
	}

	/**
	 * POST
	 * @param index
	 * @return
	 */
	public String getRefreshAllUrl() {
		get_refreshAll_url = "http://" + server + ":" + port + "/_refresh";
		return get_refreshAll_url;
	}

	/**
	 * POST
	 * @param index
	 * @return
	 */
	public String getOptimizeOneUrl(String index) {
		get_optimizeOne_url = "http://" + server + ":" + port + "/" + index + "/_optimize";
		return get_optimizeOne_url;
	}

	/**
	 * POST
	 * @param index
	 * @return
	 */
	public String getOptimizeMultiUrl(List<String> indexs) {
		String index = StringUtils.join(indexs, ",");
		get_optimizeMulti_url = "http://" + server + ":" + port + "/" + index + "/_optimize";
		return get_refreshOne_url;
	}

	/**
	 * POST
	 * @param index
	 * @return
	 */
	public String getOptimizeAllUrl() {
		get_optimizeAll_url = "http://" + server + ":" + port + "/_optimize";
		return get_optimizeAll_url;
	}

	/**
	 * POST
	 * @param index
	 * @return
	 */
	public String getUpgradeUrl(String index) {
		upgrade_url = "http://" + server + ":" + port + "/" + index + "/_upgrade";
		return upgrade_url;
	}

	/**
	 * GET
	 * @param index
	 * @return
	 */
	public String getUpgradeStatusUrl(String index) {
		upgrade_status_url = "http://" + server + ":" + port + "/" + index + "/_upgrade?human";
		return upgrade_status_url;
	}

}
