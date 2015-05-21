package com.demo.SpringOAuth2Server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author zhangerqiang
 *
 */
@SpringBootApplication
@ComponentScan
public class Application {
    public static void main( String[] args ){
    	SpringApplication.run(Application.class, args);
    }
}
