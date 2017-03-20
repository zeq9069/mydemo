package com.kyrincloud.MyBPlusTree;

import java.util.Iterator;
import java.util.List;

public class Node <K extends Comparable<K> , V>{
	
	/** 是否为叶子节点 */
	private boolean isLeaf;
	
	/** 是否为根节点 */
	private boolean isRoot;
	
	/** 叶子节点的头节点 */
	private Node<K, V> header;
	
	/** 叶子节点的前一个节点 */
	private Node<K, V> previous;
	
	/** 叶子节点的下一个节点 */
	private Node<K, V> next;
	
	/** 父节点 */
	private Node<K, V> parent;
	
	/** 关键字 */
	private List<Entry<K, V>> keywords;
	
	/** 孩子节点 */
	private List<Node<K, V>> childrens;
	
	private int order = 3;
	
	public Entry<K,V> get(Comparable<K> key){
		
		//如果是叶子节点，就遍历关键字列表
		if(isLeaf){
			for(Entry<K,V> entry : keywords){
				if(key.compareTo(entry.getKey()) == 0){
					return entry;
				}
			}
		}else{//如果不是叶子节点
			if(key.compareTo(keywords.get(0).getKey()) <= 0){
				return childrens.get(0).get(key);
			}else if(key.compareTo(keywords.get(keywords.size()-1).getKey()) >= 0){
				return childrens.get(keywords.size() - 1).get(key);
			}else{
				for(int i = 0 ; i < keywords.size() ; i++){
					if(key.compareTo(keywords.get(i).getKey()) >= 0 && key.compareTo(keywords.get(i+1).getKey()) < 0){
						return childrens.get(i+1).get(key);
					}
				}
			}
		}
		return null;
	}
	
	public void insertOrUpdate(K key , V value){
		//如果是叶子节点
		if(isLeaf){
			if(contain(key) || keywords.size() < this.order){//如果存在，或者有空余的空间
				insertOrUpdateKey(key,value);
				if(keywords.size() >= this.order){
					
				}
			}
		}else{
			
		}
	}
	
	/** 分裂当前节点*/
	public void splite(){
		if(keywords.size() >= this.order){
			
			
			
		}
	}
	
	/** 插入或者更新当前节点的Key */
	public void insertOrUpdateKey(K key , V value){
		Entry<K, V> entry = new Entry<K, V>(key, value);
		if(keywords.isEmpty()){
			keywords.add(entry);
		}
		for(Entry<K, V> e : keywords){
			if(e.getKey().compareTo(key) == 0){
				e.setValue(value);
				return;
			}
		}
		return ;
	}
	
	public boolean contain(K key){
		for(Entry<K, V> entry : keywords){
			if(key.compareTo(entry.getKey()) == 0){
				return true;
			}
		}
		return false;
	}

}
