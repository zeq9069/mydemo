package com.kyrincloud.MyBPlusTree;

public class BPlusTree implements Tree<Integer, Object>{
	
	private int order; //阶，每个节点最多包含order个关键字，包含order+1个孩子节点
	
	/** 根节点 */
	private Node root;
	
	/** 叶子节点的头结点 */
	private Node header;

	public Integer get(Comparable<Integer> key) {
		return root.;
	}

	public void insertOrUpdate(Entry<Integer, Object> entry) {
		
	}

	public void remove(Comparable<Integer> key) {
		
	}

}
