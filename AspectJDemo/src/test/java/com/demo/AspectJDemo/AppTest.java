package com.demo.AspectJDemo;

import java.util.regex.Pattern;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
	
   public static void main(String g[]){
	   String seq="get*|delete*";
	   boolean res=Pattern.compile(seq).matcher("deleteName").find();
	   
	   System.out.print(res);
   }
}
