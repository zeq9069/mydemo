package com.kyrin.JsonLexer;
/**
 * 词法单元
 * @author kyrin
 *
 */
public class Token {

	public int tokenType;
	
	public String text;
 
	public Token(int tokenType,String text) {
		this.tokenType=tokenType;
		this.text=text;
	}
	
	@Override
	public String toString(){
		return String.format("<'%s',%s>",text,JSONLexer.tokenNames[tokenType]);
	}
	
}
