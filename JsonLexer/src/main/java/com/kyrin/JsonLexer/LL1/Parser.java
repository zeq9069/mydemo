package com.kyrin.JsonLexer.LL1;

import java.util.LinkedList;
import java.util.Queue;

import com.kyrin.JsonLexer.JSONLexer;
import com.kyrin.JsonLexer.Token;

public abstract class Parser {

	JSONLexer lexer;//词法分析器
	
	Token lookahead;//当前的向前看词法单元
	
	Queue<Token> jsonToken=new LinkedList<Token>();

	public Parser(JSONLexer lexer){
		this.lexer=lexer;
		lookahead=lexer.nextToken();
		jsonToken.add(lookahead);
	}
	
	/**如果向前看词法单元类型能匹配X，那么就忽略返回，否则报错**/
	public void match(int x){
		if(lookahead.tokenType==x)consume();
		else throw new Error("excepting "+lexer.getTokenName(x)+";found"+lookahead);
	}
	
	public void consume(){
		lookahead=lexer.nextToken();
		jsonToken.add(lookahead);
	}
	
	public Queue<Token> getAllToken(){
		return jsonToken;
	}
	
}
