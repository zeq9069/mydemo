package com.demo.DynamicProxyDemo.sample;

import net.sf.cglib.core.KeyFactory;

/**
 * 
 * @author kyrin
 *
 */
public class KeySample {
	private interface MyFactory {
		public Object newInstance(int a, char[] b, String d);
	}

	public static void main(String[] args) {
		MyFactory f = (MyFactory) KeyFactory.create(MyFactory.class);
		Object key1 = f.newInstance(20, new char[] { 'a', 'b' }, "Hello");
		Object key2 = f.newInstance(20, new char[] { 'a', 'b' }, "Hello");
		Object key3 = f.newInstance(20, new char[] { 'a', '_' }, "Hello");
		System.out.println(key1);
		System.out.println(key1.equals(key2));
		System.out.println(key1.equals(key3));

	}
}
