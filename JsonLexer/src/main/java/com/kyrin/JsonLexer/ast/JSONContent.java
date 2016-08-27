package com.kyrin.JsonLexer.ast;

import java.util.List;

import com.kyrin.JsonLexer.visitor.IVisitor;

/**
 * {<content>}
 * @author kyrin
 *
 */
public class JSONContent implements ASTree{

	private List<JSONKv> items;
	
	public void accept(IVisitor visitor) {
		visitor.visitor(this);
	}

	public String getValue() {
		return null;
	}

	public List<JSONKv> getItems() {
		return items;
	}

	public void setItems(List<JSONKv> items) {
		this.items = items;
	}
	
	

}
