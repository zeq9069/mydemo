package com.kyrin.OltuDemo.shiro.config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis 工具类
 * @author kyrin
 *
 */
public final class RedisUtil {
	
	
	private static JedisPool jedisPool;
	
	private static String HOST="127.0.0.1";
	
	private static int PORT=6379;
	
	private static int MAX_IDLE=60000;
	
	private static int MAX_TOTAL=1024;
	
	private static int MAX_WAIT_MILLIS=60000;
	
	private static boolean TEST_ON_BORROW=true; 
	
	private static int TIMEOUT=60000;

	
	//jedis 配置初始化
	static{
		try{
			JedisPoolConfig config=new JedisPoolConfig();
			config.setMaxIdle(MAX_IDLE);
			config.setMaxTotal(MAX_TOTAL);
			config.setMaxWaitMillis(MAX_WAIT_MILLIS);
			config.setTestOnBorrow(TEST_ON_BORROW);
			jedisPool=new JedisPool(config,HOST,PORT,TIMEOUT);
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
