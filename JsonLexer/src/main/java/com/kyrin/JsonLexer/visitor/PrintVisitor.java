package com.kyrin.JsonLexer.visitor;

import java.util.concurrent.atomic.AtomicInteger;

import com.kyrin.JsonLexer.ast.JSONContent;
import com.kyrin.JsonLexer.ast.JSONRoot;
import com.kyrin.JsonLexer.ast.JSONKv;

/**
 * 
 * 打印json k-v键值对 观察者
 * 
 * @author kyrin
 *
 */
public class PrintVisitor implements IVisitor{

	AtomicInteger number=new AtomicInteger(0);
	
	public void visitor(JSONRoot tree) {
		//System.out.println(tree.getLeft().text);
		tree.getContent().accept(this);
		//System.out.println(tree.getRight().text);
	}
	
	public void visitor(JSONContent content) {
		for(JSONKv kv:content.getItems()){
			kv.accept(this);
		}
		System.out.println("K-V键值对数量："+number);
	}
	
	public void visitor(JSONKv jSONKv) {
		System.out.println(String.format("\"%s\":\"%s\"",jSONKv.getKey().text,jSONKv.getValue().text));
		number.getAndIncrement();
	}

}
