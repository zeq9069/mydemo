package com.test.Alldemo.hash;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 分布式一致性hash
 *  一致性的实现：通过java的TreeMap来模拟环状结构，实现均匀分布
 * @author kyrin
 *
 */
public class KemataHashDemo {

	private static TreeMap<Long, String> tree = new TreeMap<Long, String>();
	private static int num = 10;// 每个主机生成的虚拟环的数量
	static Map<String, Integer> result = new ConcurrentHashMap<String, Integer>();

	public static void main(String[] args) {
		
		//1.分配10台物理主机
		List<String> ips=new ArrayList<String>();
		for(int i=1;i<=11;i++){
			ips.add(String.format("192.168.1.%d",i));
		}
		//2.构建虚拟环路
		build(ips);

		System.out.println(toStr());
		
		//3.添加缓存，计算主机命中次数
		for(int i=0;i<=1000000;i++){
			String key=String.format("hash_key_%d",i);
			String ip=dispatch(key, i+"");
			Integer val=result.get(ip);
			if(val==null){
				val=0;
			}
			result.put(ip, ++val);
		}
		
		for(Map.Entry<String, Integer> entry:result.entrySet()){
			System.out.println(String.format("ip=%s,被命中次数：%d", entry.getKey(),entry.getValue()));
		}
	}

	/**
	 * 分发缓存到主机
	 * 
	 * @param key
	 *            缓存key
	 * @param value
	 *            缓存值
	 * @return 返回主机对应的ip
	 */
	public static String dispatch(String key, String value) {
		Long key_hash = murMurHash(key);
		SortedMap<Long, String> sort = tree.tailMap(key_hash);//获取大于等于key_hash的map中的元素，默认湿从小到大排列的
		if (sort == null || sort.isEmpty()) {
			return tree.get(tree.firstKey());
		}
		return sort.get(sort.firstKey());
	}

	/**
	 * 形成一个虚拟主机环
	 * 
	 * @param ips
	 */
	public static void build(List<String> ips) {
		for (String ip : ips) {
			Long hash_ip = murMurHash(ip);
			tree.put(hash_ip, ip);
			for (int i = 0; i < num; i++) {
				Long hash_ip_N = murMurHash(String.format("%d_%d", hash_ip, i));
				tree.put(hash_ip_N, ip);
			}
		}
	}

	/**
	 * MurMurHash算法，是非加密HASH算法，性能很高，
	 * 比传统的CRC32,MD5，SHA-1（这两个算法都是加密HASH算法，复杂度本身就很高，带来的性能上的损害也不可避免）
	 * 等HASH算法要快很多，而且据说这个算法的碰撞率很低. http://murmurhash.googlepages.com/
	 */
	private static Long murMurHash(String key) {

		ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
		int seed = 0x1234ABCD;

		ByteOrder byteOrder = buf.order();
		buf.order(ByteOrder.LITTLE_ENDIAN);

		long m = 0xc6a4a7935bd1e995L;
		int r = 47;

		long h = seed ^ (buf.remaining() * m);

		long k;
		while (buf.remaining() >= 8) {
			k = buf.getLong();

			k *= m;
			k ^= k >>> r;
			k *= m;

			h ^= k;
			h *= m;
		}

		if (buf.remaining() > 0) {
			ByteBuffer finish = ByteBuffer.allocate(8).order(
					ByteOrder.LITTLE_ENDIAN);
			// for big-endian version, do this first:
			// finish.position(8-buf.remaining());
			finish.put(buf).rewind();
			h ^= finish.getLong();
			h *= m;
		}

		h ^= h >>> r;
		h *= m;
		h ^= h >>> r;

		buf.order(byteOrder);
		return h;
	}

	public static String toStr() {
		StringBuilder str = new StringBuilder();
		for (Map.Entry<Long, String> enty : tree.entrySet()) {
			str.append(String.format("[key=%d,ip=%s]\n", enty.getKey(),
					enty.getValue()));
		}
		return str.toString();
	}

}
