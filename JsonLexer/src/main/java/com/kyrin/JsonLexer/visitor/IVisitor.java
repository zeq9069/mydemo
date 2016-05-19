package com.kyrin.JsonLexer.visitor;

import com.kyrin.JsonLexer.ast.JSONContent;
import com.kyrin.JsonLexer.ast.JSONRoot;
import com.kyrin.JsonLexer.ast.JSONKv;

/**
 * 观察者接口
 * @author kyrin
 *
 */
public interface IVisitor {
	
	public void visitor(JSONRoot tree);

	public void visitor(JSONContent content);
	
	public void visitor(JSONKv kv);
}
