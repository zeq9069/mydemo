package com.kyrincloud.interview.other;

/**
 * 打印1...n中1的数量
 * 
 * @author kyrin
 *
 */
public class PrintOneCount {
	
	public static int count(int n){
	    if(n<1)
	        return 0;
	    int count = 0;
	    int base = 1;
	    int round = n;
	    while(round>0){
	        int weight = round%10;
	        round/=10;
	        count += round*base;
	        if(weight==1)
	            count+=(n%base)+1;
	        else if(weight>1)
	            count+=base;
	        base*=10;
	    }
	    return count;
	}
	
	public static void main(String[] args) {
		System.out.println(count(123));
	}

}
