package com.sankuai.NIOFile2.invertedIndex;

/**
 * 文本
 * @author zhangerqiang
 *
 */
public class Term {
	
	int id;
	
	String text;
	
	public Term(String text , int id) {
		this.text = text;
		this.id = id;
	}

	public String getText() {
		return text;
	}
	
	public int getId(){
		return id;
	}

}
