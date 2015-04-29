package com.demo.DesignPatterns.CompositePattern;
/**
 * ***********************
 * 
 *  组合模式
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
