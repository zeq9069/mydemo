package com.test.Alldemo.trieTree;

import java.util.LinkedList;
import java.util.List;

/**
 * trie Tree （字典树）：实现关键词过滤，缺点就是占用内存过多，比如各个阶段的list元素
 * 根节点
 * @author kyrin
 *
 */
public class RootNode implements INode{

	private  LinkedList<INode> childs = new LinkedList<INode>();

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
		return 0;
	}
	@Override
	public boolean isEmpty() {
		return childs.isEmpty();
	}
	@Override
	public int size() {
		return childs.size();
	}

	@Override
	public boolean isEnd() {
		return false;
	}
	

}
