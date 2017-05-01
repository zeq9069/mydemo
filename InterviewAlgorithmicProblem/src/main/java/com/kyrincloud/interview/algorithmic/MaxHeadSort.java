package com.kyrincloud.interview.algorithmic;

import java.util.Random;

/**
 * 最大堆排序
 * 时间复杂度：O(n*logn)
 * 适合数据量比较大的时候使用
 * 
 * @author kyrin
 *
 */
public class MaxHeadSort {
	
	public static void buildHeap(int location , int len , int [] array){
		int idx ;
		if((idx = (location+1)*2) < len){//判断右节点
			if(array[idx] > array[idx-1]){
				if(array[idx] > array[location]){
					int tmp = array[idx];
					array[idx] = array[location];
					array[location] = tmp;
					
					buildHeap(idx, len, array);
				}
			}else{
				if(array[idx-1] > array[location]){
					int tmp = array[idx-1];
					array[idx-1] = array[location];
					array[location] = tmp;
					
					buildHeap(idx-1, len, array);
				}
			}
		}else if((idx = (location+1)*2-1) < len){//判断左节点
			if(array[idx] > array[location]){
				int tmp = array[idx];
				array[idx] = array[location];
				array[location] = tmp;
				
				buildHeap(idx, len, array);
			}
		}
	}
	
	public static void initHeap(int len , int[] array){
		for(int i = len-1;i >= 0 ;i-- ){
			buildHeap(i, len, array);
		}
	}
	
	public static int[] topN(int n , int len , int[] array){
		int[] topN = new int[n];
		
		initHeap( len, array);
		
		for(int i = 0; i < n ; i++){
			topN[i] = array[0];
			
			int tmp = array[0];
			array[0] = array[array.length-1-i];
			array[array.length-1-i] = tmp;
			
			buildHeap(0, array.length-1-i, array);
		}
		
		return topN;
		
	}
	
	public static void main(String[] args) {
		int[] array = new int[100000000];//{3,2,5,8,4,9,10,22,11};
		Random r = new Random();
		for(int i = 0;i < 100000000;i++){ //随机生成1亿和数字
			array[i] = r.nextInt(1000000000);
		}
		int[] topN= topN(20, array.length, array);
		for(int i = 0 ; i < 20 ;i++){
			System.out.println(topN[i]);
		}
	}
}
