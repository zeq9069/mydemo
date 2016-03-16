package com.kyrin.OltuDemo.shiro.config;

import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis 工具类 : 使用的连接记得关闭
 * @author kyrin
 *
 */
public final class RedisUtil {
	
	
	private static JedisPool jedisPool;
	
	private static String HOST="127.0.0.1";
	
	private static String PORT="6379";
	
	private static String MAX_IDLE="60000";
	
	private static String MAX_TOTAL="1024";
	
	private static String MAX_WAIT_MILLIS="60000";
	
	private static String TEST_ON_BORROW="true"; 
	
	private static String TIMEOUT="60000";

	
	//jedis 配置初始化
	static{
		try{
			Properties pro=ResourceLoaderUtil.loader("application.properties");
			JedisPoolConfig config=new JedisPoolConfig();
			config.setMaxIdle(Integer.parseInt(pro.getProperty("redis.util.max_idle", MAX_IDLE)));
			config.setMaxTotal(Integer.parseInt(pro.getProperty("redis.util.max_total", MAX_TOTAL)));
			config.setMaxWaitMillis(Integer.parseInt(pro.getProperty("redis.util.max_wait_millis", MAX_WAIT_MILLIS)));
			config.setTestOnBorrow(Boolean.parseBoolean(pro.getProperty("redis.util.test_on_borrow", TEST_ON_BORROW)));
			jedisPool=new JedisPool(config,pro.getProperty("redis.util.host", HOST),Integer.parseInt(pro.getProperty("redis.util.port", PORT)),
					Integer.parseInt(pro.getProperty("redis.util.timeout", TIMEOUT)));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//获取jedis
	public static synchronized Jedis getJedis(){
		try{
			if(jedisPool!=null){
				return jedisPool.getResource();
			}
			return null;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	//关闭jedis
	public static void close(final Jedis jedis){
		try{
			if(jedis!=null){
				jedis.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
