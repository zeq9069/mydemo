

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;


/**
 * kyrin 
 * 
 * logback test 
 *
 */
public class Root {
	
	private static Logger log=LoggerFactory.getLogger(Root.class);
	private static String name="kyrin";
	
	
    public static void main( String[] args ){
    	log.info("My name is {}",name);
    	LoggerContext lc=(LoggerContext) LoggerFactory.getILoggerFactory();
    	//StatusPrinter.print(lc);
    	ch.qos.logback.classic.Logger logger= lc.getLogger(Root.class);
    	logger.debug("I'm {}",name);
        System.out.println( "Hello World!" );
        
    }
}
