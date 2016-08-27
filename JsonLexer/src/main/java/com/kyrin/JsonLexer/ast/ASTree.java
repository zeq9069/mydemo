package com.kyrin.JsonLexer.ast;


import com.kyrin.JsonLexer.visitor.IVisitor;

/**
 * json 抽象语法树
 * @author kyrin
 *
 */
public interface ASTree {


	 public void accept(IVisitor visitor);
	  
}
