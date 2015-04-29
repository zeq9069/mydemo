package com.demo.DesignPatterns.CompositePattern;
/**
 * ***********************
 * 
 *  组合模式
 *  
 *	组合模式有时又叫部分-整体模式在处理类似树形结构的问题时比较方便
 *
 *	使用场景：将多个对象组合在一起进行操作，常用于表示树形结构中，例如二叉树，数等。
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月29日]
 *
 */
public class CompositePattern {

	
	public static void main(String args[]){
		TreeNode root=new TreeNode("root");
		TreeNode a=new TreeNode("A");
		TreeNode b=new TreeNode("B");
		a.add(b);
		root.add(a);
		System.out.println("Build the tree finished!");
	}
	
	
	
}
