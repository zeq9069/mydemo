package com.kyrin.JsonLexer.LL1;

import com.kyrin.JsonLexer.JSONLexer;

public class JSONParserTest {
	
	public static void main(String[] args) {
		String input="{\"key\":\"value\",{{\"key\":\"value\"}}}";
		JSONLexer lexer=new JSONLexer(input);
		JSONParser parser=new JSONParser(lexer);
		parser.json();
	}

}
