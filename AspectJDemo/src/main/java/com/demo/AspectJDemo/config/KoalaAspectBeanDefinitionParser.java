package com.demo.AspectJDemo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import com.demo.AspectJDemo.DataSourceAspect;
import com.demo.AspectJDemo.filter.MonitorFilter;

public class KoalaAspectBeanDefinitionParser implements BeanDefinitionParser{

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		RootBeanDefinition root=new RootBeanDefinition();
		root.setBeanClass(DataSourceAspect.class);
		
		ConstructorArgumentValues cv=new ConstructorArgumentValues();
		List<MonitorFilter> mf=new ArrayList<MonitorFilter>();
		cv.addIndexedArgumentValue(0, mf);
		
		root.setConstructorArgumentValues(cv);
		parserContext.getRegistry().registerBeanDefinition(DataSourceAspect.class.getName(),root);
		return root;
	}
}
