package com.kyrin.JsonLexer.ast;

import com.kyrin.JsonLexer.Token;
import com.kyrin.JsonLexer.visitor.IVisitor;

/**
 * "key":"value"
 * @author kyrin
 *
 */
public class JSONKv implements ASTree{

	private Token key,value;
	
	public void accept(IVisitor visitor) {
		visitor.visitor(this);
	}

	public Token getValue() {
		return value;
	}

	public Token getKey() {
		return key;
	}

	public void setKey(Token key) {
		this.key = key;
	}

	public void setValue(Token value) {
		this.value = value;
	}
}
