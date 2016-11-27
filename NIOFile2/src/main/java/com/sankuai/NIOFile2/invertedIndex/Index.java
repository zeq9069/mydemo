package com.sankuai.NIOFile2.invertedIndex;

import java.util.List;

/**
 * 索引和偏移量
 * @author zhangerqiang
 *
 */
public class Index {
	
	String index;
	
	List<Integer> ids;
	
	public Index(String index,List<Integer> ids) {
		this.index = index;
		this.ids = ids;
	}

	public String getIndex() {
		return index;
	}

	public List<Integer> getIds() {
		return ids;
	}

}
