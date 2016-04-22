package com.kyrin.JsonLexer.LL1;

import java.util.Queue;

import com.kyrin.JsonLexer.JSONLexer;
import com.kyrin.JsonLexer.Token;


public class JSONParserTest {
	
	public static void main(String[] args) {
		//String input="{\"key\":\"value\",{{\"key\":\"value\"}}}";
		String input="{\"key\":\"value\",{{\"key\":[\"xx\"]}}}";
		JSONLexer lexer=new JSONLexer(input);
		JSONParser parser=new JSONParser(lexer);
		parser.json();//可以校验json的格式是否正确，如果有误会报错，没错的话，什么都不会打印
		
		Queue<Token> q=parser.getAllToken();//获取语法解析器中缓存的Token
		
		for(Token t:q){
			System.out.println(t);
		}
	}

}
