package com.kyrin.OltuDemo.shiro.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import redis.clients.jedis.Jedis;

/**
 * 自定义实现shiro中的SessionDAO
 * 将session存入redis实现session跨应用的共享（目前仅仅是同域名）
 * @author kyrin
 *
 */
public class RedisSessionDao extends AbstractSessionDAO{

	private String spring_session_prefix="spring:session:sessions";
	
	@Override
	public void update(Session session) throws UnknownSessionException {
		if(session==null || session.getId()==null){
			return ;
		}
		byte[] sessionId =  getSessionKey(session.getId()).getBytes();
		byte[] sessionValue = SerializationUtil.serialize(session);
		Jedis jedis=RedisUtil.getJedis();
		try{
			Long expire = session.getTimeout() / 1000;
			jedis.set(sessionId, sessionValue);
			jedis.expire(sessionId, Integer.parseInt(expire.toString()));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			RedisUtil.close(jedis);
		}
		
	}

	@Override
	public void delete(Session session) {
		if(session == null || session.getId() == null){
			return;
		}
		Jedis jedis=RedisUtil.getJedis();
		try{
			byte[] sessionId =  getSessionKey(session.getId()).getBytes();
			jedis.del(sessionId);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			RedisUtil.close(jedis);
		}
	}

	@Override
	public Collection<Session> getActiveSessions() {
		Jedis jedis=RedisUtil.getJedis();
		List<Session> sessions=new ArrayList<Session>();
		try{
		Set<String> keys =jedis.keys(getSpring_session_prefix()+":*");
		for(String key:keys){
			Session session= SerializationUtil.deserialize(jedis.get(key.getBytes()),Session.class);
			sessions.add(session);
		}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			RedisUtil.close(jedis);
		}
		return sessions;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);  
        this.assignSessionId(session, sessionId);  
       Jedis jedis=RedisUtil.getJedis();
       try{
    	   jedis.set(getSessionKey(session.getId()).getBytes(), SerializationUtil.serialize(session));
       }catch(Exception e){
    	   e.printStackTrace();
       }finally{
    	   RedisUtil.close(jedis);
       }
        return sessionId;  
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		if(sessionId==null){
			return null;
		}
		Jedis jedis=RedisUtil.getJedis();
		try{
			return SerializationUtil.deserialize(jedis.get(getSessionKey(sessionId).getBytes()),Session.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public String getSpring_session_prefix() {
		return spring_session_prefix;
	}

	public void setSpring_session_prefix(String spring_session_prefix) {
		this.spring_session_prefix = spring_session_prefix;
	}
	
	public String getSessionKey(Serializable key){
		return this.spring_session_prefix+":"+key;
	}

}
