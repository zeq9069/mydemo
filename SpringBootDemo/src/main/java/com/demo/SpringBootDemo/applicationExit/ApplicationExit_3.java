package com.demo.SpringBootDemo.applicationExit;

import org.springframework.boot.ExitCodeGenerator;

public class ApplicationExit_3 implements ExitCodeGenerator{

	@Override
	public int getExitCode() {
		System.out.println(String.format("[ I will exit ! GoodBye ! from %s]",this.getClass().getName()));
		return 404;
	}

}
