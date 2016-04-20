package com.kyrin.JsonLexer;

public class JSONTest {
	
	public static void main(String[] args) {
		String value="{WWW:{EEE:,,}}";
		JSONLexer lexer=new JSONLexer(value);
		Token token=lexer.nextToken();
		while(token.tokenType!=Lexer.EOF_TYPE){
			System.out.println(token.toString());
			token=lexer.nextToken();
		}
	}

}
