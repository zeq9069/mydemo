package com.demo.SpringBootDemo.configurationProperties;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class Validator_1 extends ConfigurationPropertiesBindingPostProcessor{

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("[ ConfigurationProperties AfterPropertiesSet ]");
		super.afterPropertiesSet();
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		System.out.println(String.format("[ %s ConfigurationProperties postProcessBeforeInitialization ]",beanName));
		return super.postProcessBeforeInitialization(bean, beanName);
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		System.out.println(String.format("[ %s ConfigurationProperties postProcessAfterInitialization ]",beanName));
		return super.postProcessAfterInitialization(bean, beanName);
	}

}
