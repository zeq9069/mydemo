package com.demo.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration.Strategy;

import com.demo.ehcache.utils.EhStringUtils;

/**
 * 
 * @author kyrin
 */
public class EhcacheFactory {

	/*-----------------  ehcache.xml  ----------------------------------------------------
	 * <ehcache>
	 *  <diskStore path="java.io.tmpdir/ehcache"/>  
	 *  
	 *  <defaultCache maxElementsInMemory="10" eternal="false" timeToIdleSeconds="120" 
	 *                                      timeToLiveSeconds="120" overflowToDisk="false"/>
	 *                                      
	 * <cache name="demo1" maxElementsInMemory="20000" eternal="false" timeToIdleSeconds="3600"
	 *                                      timeToLiveSeconds="1800" overflowToDisk="false" />
	 *  
	 * </ehcache>
	 */

	/*
	 * 默认的cache config
	 */
	private static CacheConfiguration defaultCacheConfig() {
		CacheConfiguration defaultconf = new CacheConfiguration();
		defaultconf.setMaxEntriesLocalHeap(10);
		defaultconf.setEternal(false);
		defaultconf.setTimeToIdleSeconds(120);
		defaultconf.setTimeToLiveSeconds(120);
		defaultconf.addPersistence(new PersistenceConfiguration().strategy(Strategy.NONE));
		return defaultconf;
	}

	/*
	 * master config
	 */
	public static Configuration configuration() {
		Configuration conf = new Configuration();
		conf.setDefaultCacheConfiguration(defaultCacheConfig());
		return conf;
	}

	/*
	 * cache manager
	 */
	private static CacheManager getInstance() {

		/* CacheManager.create(configuration())
		 * CacheManager.create("src/main/resources/ehcache.xml")
		 */
		return CacheManager.create("src/main/resources/ehcache2.xml");
	}

	/*
	 * shutdown manager
	 */
	private static void shutdownManager() {
		getInstance().shutdown();
	}

	/*
	 * add cache
	 */
	public static void addCache(String name) {
		CacheManager manager = getInstance();
		manager.addCache(name);
	}

	/*
	 * add one cache
	 */
	public static void addCache(Cache cache) {
		if (EhStringUtils.isNull(cache)) {
			throw new NullPointerException("The cache is null !");
		}
		CacheManager manager = getInstance();
		manager.addCache(cache);
	}

	/*
	 * add a element for  cache
	 */
	public static void addElement(String cacheName, Element element) {
		if (!EhStringUtils.hasText(cacheName) || EhStringUtils.isNull(element)) {
			throw new IllegalArgumentException(" Argument have some errors !");
		}
		CacheManager manager = getInstance();
		if (manager.cacheExists(cacheName)) {
			Cache cache = manager.getCache(cacheName);
			cache.put(element);
		} else {
			throw new NullPointerException("The " + cacheName + " cache is not exist !");
		}
	}

	public static void removeCache(String cacheName) {
		if (!EhStringUtils.hasText(cacheName)) {
			throw new IllegalArgumentException("The cacheName is null !");
		}
		CacheManager manager = getInstance();
		manager.removeCache(cacheName);
	}

	/*
	 * remove all caches
	 */
	public static void removeAllCaches() {
		CacheManager manager = getInstance();
		manager.removeAllCaches();
	}

	public static void updateElement(String cacheName, Element element) {
		if (EhStringUtils.isNull(element)) {
			throw new NullPointerException("The element is null !");
		}

		CacheManager manager = getInstance();
		if (!manager.cacheExists(cacheName)) {
			throw new NullPointerException("The " + cacheName + " cache is not exists!");
		}

		Cache cache = manager.getCache(cacheName);

		if (!cache.isElementInMemory(element.getObjectKey())) {
			throw new NullPointerException("The " + element.getObjectKey().toString() + " element is not in "
					+ cacheName + " cache !");
		}
		cache.put(element);

	}

	/*
	 * get all cache names
	 */
	public static String[] getCacheNames() {
		return getInstance().getCacheNames();
	}

	public static void main(String args[]) {
		CacheManager manager = getInstance();

		System.out.println("-----------------Ehcache 操作开始-------------------");

		/*-------------------1. iterator cache names-----------------------------------------------------*/
		System.out.println("<1>");
		addCache("demo");
		String names[] = getCacheNames();
		if (names != null) {
			for (String name : names) {
				System.out.println(name);
			}
		}

		/*------------------2. add and get  one Element to cache----------------------------------------*/
		System.out.println("<2>");
		String cacheName_1 = "app";
		addCache(cacheName_1);
		long date = System.currentTimeMillis();
		Element el1 = new Element("key_1", "value_1");
		Element el2 = new Element("key_2", "value_2", date);
		Element el3 = new Element("key_3", "alue_3", date, date, date, 0, true, 3600, 0, date);

		addElement(cacheName_1, el1);
		addElement(cacheName_1, el2);
		addElement(cacheName_1, el3);
		System.out.println("The number of cache in manager :" + getCacheNames().length);
		System.out.println("The number of elements in " + cacheName_1 + " cache :" + manager.getCache("app").getSize());
		Element element = manager.getCache("app").get("key_1");
		System.out.println("The " + element.getObjectKey().toString() + " element value is :"
				+ element.getObjectValue().toString());

		/*-----------------3. add and remove cache-----------------------------------------------------*/
		System.out.println("<3>");

		String cacheName = "cache_1";
		Cache cache = new Cache(cacheName, 100, null, false, null, false, 3600, 36000, false, 360, null);
		addCache(cache);
		System.out.println("cache_1 is exists :" + manager.cacheExists(cacheName));
		removeCache("cache_1");
		System.out.println("cache_1 is exists :" + manager.cacheExists(cacheName));
		removeAllCaches();
		//shutdown manager
		shutdownManager();
		System.out.println("--------------------ehcache 操作结束--------------------");
	}
}
