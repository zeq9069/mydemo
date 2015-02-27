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

	/*ehcache.xml
	 * 
	 *  <diskStore path="java.io.tmpdir/ehcache"/>  
	 * <defaultCache maxElementsInMemory="10" eternal="false"
		    timeToIdleSeconds="120" timeToLiveSeconds="120" overflowToDisk="false">
	   </defaultCache>
	   <cache name="demo1" maxElementsInMemory="20000" eternal="false"
		    timeToIdleSeconds="3600" timeToLiveSeconds="1800" overflowToDisk="false" />
	*/

	/**
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

	/**
	 * 配置文件
	 * @return
	 */
	public static Configuration configuration() {
		Configuration conf = new Configuration();
		conf.setDefaultCacheConfiguration(defaultCacheConfig());
		return conf;
	}

	/**
	 * cache manager
	 * @return
	 */
	private static CacheManager getInstance() {
		//return CacheManager.create(configuration());
		//return CacheManager.create("src/main/resources/ehcache.xml");
		return CacheManager.create("src/main/resources/ehcache2.xml");
	}

	/**
	 * add cache
	 * @param name cache名
	 */
	public static void addCache(String name) {
		CacheManager manager = getInstance();
		manager.addCache(name);
	}

	/**
	 * add a element for  cache
	 * @param name -> cache's name
	 * @param element -> a element 
	 */
	public static void addCache(String name, Element element) {
		if (!EhStringUtils.hasText(name) || EhStringUtils.isNull(element)) {
			throw new IllegalArgumentException(" Argument have some errors !");
		}
		CacheManager manager = getInstance();
		if (manager.cacheExists(name)) {
			Cache cache = manager.getCache(name);
			cache.put(element);
		} else {
			throw new NullPointerException("the " + name + " cache is not exist !");
		}
	}

	/**
	 * get all cache names
	 */
	public static String[] getCacheNames() {
		return getInstance().getCacheNames();
	}

	public static void main(String args[]) {
		/*1.遍历 cache names
		 * addCache("demo");
		String names[] = getCacheNames();
		if (names != null) {
			for (String name : names) {
				System.out.println(name);
			}
		}*/

		addCache("app");

		long date = System.currentTimeMillis();
		Element el1 = new Element("key_1", "value_1");
		Element el2 = new Element("key_2", "value_2", date);
		Element el3 = new Element("key_3", "alue_3", date, date, date, 0, true, 3600, 0, date);

		addCache("app", el1);
		addCache("app", el2);
		addCache("app", el3);

	}
}
