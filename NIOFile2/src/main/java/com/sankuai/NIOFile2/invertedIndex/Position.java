package com.sankuai.NIOFile2.invertedIndex;

/**
 * doc的偏移量
 * @author zhangerqiang
 *
 */
public class Position {
	
	int start;
	
	int offset;
	
	public Position(int start , int offset) {
		this.start = start;
		this.offset = offset;
	}

	public int getStart() {
		return start;
	}

	public int getOffset() {
		return offset;
	}

}
