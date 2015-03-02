package com.demo.RandomDemo;

import java.util.ArrayList;
import java.util.List;

/**
 * 随机打乱一定范围内的整数顺序
 * （效率还不错）
 */
public class RandomDemo 
{
	 
public List<Integer> randomList(int start, int end) {
		//1,产生基础数据
		List<Integer> list = new ArrayList<Integer>();
		for (int i = start; i < end; i++) {
			list.add(i);
		}
		//2,打乱顺序
		for (int i = list.size() - 1; i > 0; i--) {
			int index = (int) (Math.random() * i);
			int temp = list.get(index);
			list.set(index, list.get(i));
			list.set(i, temp);
		}
		return list;
	}

	
    public static void main( String[] args )
    {
    	 List<Integer> list=randomList(10000,99999);
   
    }
}
