package com.kyrin.OltuDemo.shiro.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.util.SerializationUtils;

import redis.clients.jedis.Jedis;

/**
 * redis操作session     序列化需要自己实现
 * @author kyrin
 *
 */
public class RedisSessionDao extends AbstractSessionDAO{

	private String spring_session_prefix="oltu:spring:session:sessions";
	
	@Override
	public void update(Session session) throws UnknownSessionException {
		if(session==null || session.getId()==null){
			return ;
		}
		byte[] sessionId = SerializationUtils.serialize(getSessionKey(session.getId()));
		byte[] sessionValue = SerializationUtils.serialize(session);
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
			byte[] sessionId = SerializationUtils.serialize(getSessionKey(session.getId()));
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
			Session session=(Session) SerializationUtils.deserialize(jedis.get(key.getBytes()));
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
    	   jedis.set(SerializationUtils.serialize(getSessionKey(session.getId())), SerializationUtils.serialize(session));
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
			return (Session) SerializationUtils.deserialize(jedis.get(SerializationUtils.serialize(getSessionKey(sessionId))));
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
