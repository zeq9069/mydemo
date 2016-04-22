package com.kyrin.JsonLexer.LL1;

import com.kyrin.JsonLexer.JSONLexer;

/**
 * 	JSON 语法解析器（负责语法的合法性校验）
 * 
 * LL(1)模式，递归下降语法分析
 * 通常选择使用FIRST和FOLLOW两个运算来计算向前看集合。
 * 向前看1个字符
 * 那些词法单元可能会出现在这个解析选项的开头
 * 
 * 
 * 在JSONLexer文法的基础上改造
 * 
 * 稍微完善之后为json文法，支持嵌套和kv，但是目前不支持一些数组之类的类型还有一些关键字，以后会慢慢加进来
 * JSON::='{'<CONTENT>'}' ;
 * CONTENT::=<EXP>(','<EXP>)*;
 * EXP::=<KV>| <JSON> ;
 * KV::=<KEY> ':' <VALUE>;
 * KEY::='"' <VAL> '"'
 * VALUE::='"'<VAL>'"'| <JSON> | <ARRAY> 
 * ARRAY::='['<COLUME>']'
 * COLUME::=<VALUE>(','<VALUE>)*
 * VAL::={'a'..'z'|'A'..'Z'|0..9}+
 * @author kyrin
 */
public class JSONParser extends Parser{
	
	public JSONParser(JSONLexer lexer) {
		super(lexer);
	}
	
	public void json(){
		match(JSONLexer.LBRACK);
		content();
		match(JSONLexer.RLRACK);
	}
	
	public void content(){
		exp();
		while(lookahead.tokenType==JSONLexer.COMMA){
			match(JSONLexer.COMMA);
			exp();
		}
	}
	
	public void exp(){
		if(lookahead.tokenType==JSONLexer.DQUOTES){
			kv();
		}else if(lookahead.tokenType==JSONLexer.LBRACK){
			json();
		}else {
			throw new Error("exception KV or JSON ; found "+lookahead);
		}
	}
	
	public void kv(){
			key();
			match(JSONLexer.SEMICOLON);
			value();
	}
	
	public void key(){
		match(JSONLexer.DQUOTES);
		match(JSONLexer.TEXT);
		match(JSONLexer.DQUOTES);
	}
	
	public void value(){
		if(lookahead.tokenType==JSONLexer.DQUOTES){
			match(JSONLexer.DQUOTES);
			match(JSONLexer.TEXT);
			match(JSONLexer.DQUOTES);
		}else if(lookahead.tokenType==JSONLexer.LBRACK){
			json();
		}else if(lookahead.tokenType==JSONLexer.LBRACKETS){
			match(JSONLexer.LBRACKETS);
			array();
			match(JSONLexer.RBRACKETS);
		}else {
			throw new Error("exception TEXT or JSON or ARRAY ; found "+lookahead);
		}
	}
	
	public void array(){
		value();
		while(lookahead.tokenType==JSONLexer.COMMA){
			match(JSONLexer.COMMA);
			value();
		}
	}
	
}
