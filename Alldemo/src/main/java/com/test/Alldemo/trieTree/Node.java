package com.test.Alldemo.trieTree;

import java.util.LinkedList;
import java.util.List;
/**
 * 
 * @author kyrin
 *
 */
public  class Node implements INode{
	
	private  LinkedList<INode> childs = new LinkedList<INode>();
	private char value;

	private boolean isEnd;
	
	public Node(char value,boolean isEnd) {
		this.value=value;
		this.isEnd=isEnd;
	}
	public Node(char value) {
		this(value,false);
	}

	@Override
	public List<INode> getChild() {
		return childs;
	}

	@Override
	public boolean add(INode node) {
		return childs.add(node);
	}

	@Override
	public boolean remove(INode node) {
		return childs.remove(node);
	}

	@Override
	public INode isExist(char val) {
		for(INode node:childs){
			if(node.getValue()==val){
				return node;
			}
		}
		return null;
	}

	@Override
	public char getValue() {
		return this.value;
	}
	
	public  boolean isEnd(){
		return isEnd;
	}
	@Override
	public boolean isEmpty() {
		return childs.isEmpty();
	}
	@Override
	public int size() {
		return childs.size();
	}

}
