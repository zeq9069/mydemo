package com.demo.SpringBootDemo.programmaticCustomization;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * 自定义处理servlet container
 * 也可以直接定义
 * 
@Bean
public EmbeddedServletContainerFactory servletContainer() {
    TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
    factory.setPort(9000);
    factory.setSessionTimeout(10, TimeUnit.MINUTES);
    factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/notfound.html"));
    return factory;
}
 * 
 * @author zhangerqiang
 *
 */
@Component
public class CustomizationBean implements EmbeddedServletContainerCustomizer{

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		System.out.println("[ ==========customizationBean is run============ ]");
		container.setPort(9090);
		container.setSessionTimeout(30,TimeUnit.MINUTES);
		container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/index"));
	}

}
