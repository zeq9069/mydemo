package com.kyrincloud.interview.algorithmic;

/**
 * 冒泡排序
 * 时间复杂度：O(n^2)
 * 
 * @author kyrin
 *
 */
public class MaoPaoSort {
	
	
	public static void sort(int[] array){
		for(int i = 1 ; i< array.length;i++){
			for(int j = 0  ; j < array.length - i ;j++){
				if(array[j] > array[j+1]){
					int tmp = array[j];
					array[j] = array[j+1];
					array[j+1] = tmp;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		int [] array = new int[]{3,2,5,8,4,9,10,22,11};
		sort(array);
		for(int value : array){
			System.out.println(value);
		}
	}

}
