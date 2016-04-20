package com.kyrin.JsonLexer;

public class JSONTest {
	
	public static void main(String[] args) {
		//并不能校验json文本的格式正确与否，只能校验单个词法单元
		String value="{WWW:{EEE:,,’‘’}}";
		JSONLexer lexer=new JSONLexer(value);
		Token token=lexer.nextToken();
		while(token.tokenType!=Lexer.EOF_TYPE){
			System.out.println(token.toString());
			token=lexer.nextToken();
		}
	}

}
