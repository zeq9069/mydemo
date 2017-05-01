package com.kyrincloud.interview.algorithmic;

/**
 * 二分查找
 * 时间复杂度：
 * @author kyrin
 *
 */
public class ErFenFind {
	
	public static int find(int[] array,int key){
		int start = 0;
		int end = array.length;
		int middle = (start+end)/2;
		while(start < end){
			if(array[middle] == key){
				return middle;
			}else if(array[middle] > key){
				end = middle;
				middle = (start+end)/2;
			}else if(array[middle] < key){
				start = middle;
				middle = (start+end)/2;
			}
		}
		
		return -1;
	}
	
	public static void main(String[] args) {
		int [] array = new int[]{1,2,3,4,5,6,7,8,9};
		System.out.println(find(array,8));
	}

}
