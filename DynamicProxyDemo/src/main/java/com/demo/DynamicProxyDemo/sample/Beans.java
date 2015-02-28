package com.demo.DynamicProxyDemo.sample;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 
 * @author kyrin
 *
 */
public class Beans implements MethodInterceptor {

	private PropertyChangeSupport propertySupport;

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertySupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertySupport.addPropertyChangeListener(listener);
	}

	@SuppressWarnings("rawtypes")
	public static Object newInstance(Class clazz) {
		try {

			Beans interceptor = new Beans();
			Enhancer en = new Enhancer();
			en.setSuperclass(clazz);
			en.setCallback(interceptor);
			Object bean = en.create();
			interceptor.propertySupport = new PropertyChangeSupport(bean);
			return bean;
		} catch (Throwable e) {
			e.printStackTrace();
			throw new Error(e.getMessage());
		}
	}

	@SuppressWarnings("rawtypes")
	static final Class C[] = new Class[0];
	static final Object emptyArgs[] = new Object[0];

	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		Object retValFromSuper = null;
		try {
			if (!Modifier.isAbstract(method.getModifiers())) {
				retValFromSuper = proxy.invokeSuper(obj, args);
			}
		} finally {
			String name = method.getName();
			if (name.equals("addPropertyChangeListener")) {
				addPropertyChangeListener((PropertyChangeListener) args[0]);
			} else if (name.equals("addPropertyChangeListener")) {
				removePropertyChangeListener((PropertyChangeListener) args[0]);
			}
			if (name.startsWith("set") && args.length == 1 && method.getReturnType() == Void.TYPE) {
				char proName[] = name.substring("set".length()).toCharArray();
				proName[0] = Character.toLowerCase(proName[0]);
				propertySupport.firePropertyChange(new String(proName), null, args[0]);
			}
		}
		return retValFromSuper;
	}

	public static void main(String args[]) {
		Bean bean = (Bean) newInstance(Bean.class);
		bean.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				System.out.println(evt);
			}
		});
		bean.setSampleProperty("TEST1");
		bean.setSampleProperty("TEST2");
	}
}
