package com.kyrin.JsonLexer.LL1;

import java.util.Queue;

import com.kyrin.JsonLexer.JSONLexer;
import com.kyrin.JsonLexer.Token;

public class JSONParserTest {
	
	public static void main(String[] args) {
		String input="{\"key\":\"value\",{{\"key\":\"value\"}}}";
		JSONLexer lexer=new JSONLexer(input);
		JSONParser parser=new JSONParser(lexer);
		parser.json();
		Queue<Token> q=parser.getAllToken();
		for(Token t:q){
			System.out.println(t);
		}
	}

}
