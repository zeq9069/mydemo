package com.kyrin.JsonLexer;

import com.kyrin.JsonLexer.LL1.JSONParser;
import com.kyrin.JsonLexer.ast.JSONRoot;
import com.kyrin.JsonLexer.visitor.PrintVisitor;


public class ASTTest {
	
	public static void main(String[] args) {
		String input="{\"k1\":\"v1\",\"k2\":\"v2\"}";
		JSONParser p=new JSONParser(new JSONLexer(input));
		JSONRoot root=p.parserRoot();
		
		//打印k-v
		root.accept(new PrintVisitor());
	}
	
}
