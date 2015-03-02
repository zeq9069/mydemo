package cn.ncss.jym.graduate.service.impl.search;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
/**
 * 
 * 配置文件加载工具类
 * @author kyrin
 *
 */
public class AppConfigUtils {
	private static URL url=null;
	private static Properties  pro=null;
	private static Logger logger=Logger.getLogger(AppConfigUtils.class);
	
	public static  Properties load(String path)  {
		try{
			ClassLoader loader=Thread.currentThread().getContextClassLoader();
			url=loader.getResource(path);
			if(url==null){
				throw new LoadPropertiesException("elasticsearch.properties配置文件加载异常！");
			}
		}catch(LoadPropertiesException e){
			logger.warn(e.getMessage());
			System.out.println(e.getMessage());
		}
		return getProperty();
	}
	
	private static Properties getProperty(){
	try {
 				InputStream in = url.openStream();
				pro=new Properties();
				pro.load(in);
				if(!pro.containsKey(GlobalConstant.ELASTICSEARCH_SERVER_HOST) || 
						!pro.containsKey(GlobalConstant.ELASTICSEARCH_SERVER_PORT) || 
								!pro.containsKey(GlobalConstant.ELASTICSEARCH_INDEX) || 
										!pro.containsKey(GlobalConstant.ELASTICSEARCH_INDEX_TYPE)){
					throw new NotFindArgumentException("elasticsearch.properties中缺少参数异常！");
				}
		} catch (LoadPropertiesException e) {
			// TODO Auto-generated catch block
			logger.warn(e.getMessage());
			System.out.println(e.getMessage());
			e.printStackTrace();
		}catch (IOException e) {
			logger.warn(e.getMessage());
		} catch(NotFindArgumentException e){
			System.out.println(e.getMessage());
			logger.warn(e.getMessage());
		}
	return pro;
	}
         
}
