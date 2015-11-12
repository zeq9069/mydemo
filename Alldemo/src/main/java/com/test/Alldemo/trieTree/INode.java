package com.test.Alldemo.trieTree;

import java.util.List;
/**
 * 
 * @author kyrin
 *
 */
public interface  INode {
	
	public  List<INode> getChild();

	public  boolean add(INode node);

	public  boolean remove(INode node);

	public boolean isEmpty();
	
	public int size();
	
	public  INode isExist(char val);

	public  char getValue();
	
	public boolean isEnd();

}
