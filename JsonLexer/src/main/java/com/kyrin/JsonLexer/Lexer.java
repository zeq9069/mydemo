package com.kyrin.JsonLexer;

/**
 * 词法分析器接口
 *
 */
public abstract class Lexer {
	
	public String input;
	
	public char c;
	
	public int p=0;
	
	public static char EOF=(char)-1;
	
	public static int EOF_TYPE=1;
	
	public Lexer(String input) {
		this.input=input;
		c=input.charAt(p);
	}
	
	public abstract Token nextToken();
     
}
