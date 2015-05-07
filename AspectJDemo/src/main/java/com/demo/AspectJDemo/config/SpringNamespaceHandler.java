package com.demo.AspectJDemo.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;



public class SpringNamespaceHandler extends NamespaceHandlerSupport{

	 public void init() {
	        registerBeanDefinitionParser("default-aspect", new KoalaAspectBeanDefinitionParser());
	    }

}
