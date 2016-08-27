package com.kyrin.JsonLexer.ast;


import com.kyrin.JsonLexer.Token;
import com.kyrin.JsonLexer.visitor.IVisitor;


/**
 * {"k1":"v1","k2":"v2"}
 * @author kyrin
 *
 */
public class JSONRoot implements ASTree{

	private Token left,right;
	
	private JSONContent content;
	
	public void accept(IVisitor visitor) {
		visitor.visitor(this);
	}

	public String getValue() {
		return null;
	}

	public Token getLeft() {
		return left;
	}

	public void setLeft(Token left) {
		this.left = left;
	}

	public Token getRight() {
		return right;
	}

	public void setRight(Token right) {
		this.right = right;
	}

	public JSONContent getContent() {
		return content;
	}

	public void setContent(JSONContent content) {
		this.content = content;
	}
	
}
