package org.kyrincloud.Spider;

import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
   public static void main(String[] args) throws UnsupportedOperationException, IOException {
	   for(int i=0;i<3;i++){
		   Main m=new Main();
		   m.main(null);
	   }
}
}
