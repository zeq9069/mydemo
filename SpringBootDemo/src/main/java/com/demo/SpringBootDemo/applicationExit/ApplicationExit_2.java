package com.demo.SpringBootDemo.applicationExit;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

/**
 *  
 * @author zhangerqiang
 *
 */
@Component
public class ApplicationExit_2 implements DisposableBean{

	@Override
	public void destroy() throws Exception {
		System.out.println("[ I will exit ! GoodBye ! from ]"+this.getClass().getName());
	}

}
