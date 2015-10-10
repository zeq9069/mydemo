package com.demo.NettyDemo.example.twenty;

import io.netty.channel.Channel;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 
 * 缓存客户端连接
 * @author kyrin
 *
 */
public class ClientCache {
	private static ConcurrentMap<Long, Channel> map = new ConcurrentHashMap<Long, Channel>();

	public static Channel get(long key) {
		return map.get(key);
	}

	public static Iterator<Channel> getList() {
		return map.values().iterator();
	}

	public static void put(long key, Channel value) {
		map.put(key, value);
	}
}
