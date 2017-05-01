package com.kyrincloud.interview.algorithmic;

/**
 * 快速排序:一种特殊的冒泡排序
 * 时间复杂度：O(n*log2n)
 * 
 * @author kyrin
 *
 */
public class QuickSort {
	
	public static int quickSort( int start , int end ,int [] array){
		int location = array[start];
		while(start < end){
			while(start < end && array[end] >= location ){
				end--;
			}
			array[start] = array[end];
			while(start < end && array[start] <= location){
				start++;
			}
			array[end] = array[start];
		}
		array[end] = location;

		return start;
	}
	
	public static void sort(int start , int end ,int [] array){
		if(end > start){
			int location = quickSort(start, end, array);
			sort(start, location-1, array);
			sort(location+1,end, array);
		}
	}
	
	public static void main(String[] args) {
		int [] array = new int[]{3,2,5,8,4,9,10,22,11};
		sort(0,array.length-1,array);
		for(int value : array){
			System.out.println(value);
		}
	}

}
