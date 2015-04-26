package com.demo.ConcurrentPackageDemo.someDemo;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * **************************
 * 
 *  wait/notify/notifyall测试
 *
 * *************************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月26日]
 *
 */
public class ThreadDemo {
	
	private static Queue<String> queue=new ConcurrentLinkedQueue<String>();
	
	public static void main(String ap[]){
		new Thread(new Consume(queue,"consume_1")).start();
		new Thread(new Product(queue,"prouct_1")).start();
		new Thread(new Consume(queue,"consume_2")).start();
		new Thread(new Product(queue,"prouct_2")).start();
	}
	
	
	
	/**
	 * (1) 使用notifyAll的时候
	 * 
prouct_1生产者添加元素
prouct_1生产者添加元素
prouct_1生产者添加元素
prouct_1生产者添加元素
prouct_1生产者添加元素
prouct_1生产者添加元素
consume_1消费者获取：element_result
consume_1消费者获取：element_result
consume_1消费者获取：element_result
consume_1消费者获取：element_result
consume_1消费者获取：element_result
consume_1消费者获取：element_result
prouct_2生产者添加元素
prouct_2生产者添加元素
prouct_2生产者添加元素
prouct_2生产者添加元素
prouct_2生产者添加元素
prouct_2生产者添加元素
consume_2消费者获取：element_result
consume_2消费者获取：element_result
consume_2消费者获取：element_result
consume_2消费者获取：element_result
consume_2消费者获取：element_result
consume_2消费者获取：element_result
	 * 
	 * 
	 * 
	 * 
	 * 
	 * （2）当使用notify的时候
	 * 
	 * 
	 prouct_1生产者添加元素
prouct_1生产者添加元素
prouct_1生产者添加元素
prouct_1生产者添加元素
prouct_1生产者添加元素
prouct_1生产者添加元素
consume_1消费者获取：element_result
consume_1消费者获取：element_result
consume_1消费者获取：element_result
consume_1消费者获取：element_result
consume_1消费者获取：element_result
consume_1消费者获取：element_result
prouct_1生产者添加元素
prouct_1生产者添加元素
prouct_1生产者添加元素
prouct_1生产者添加元素
prouct_1生产者添加元素
prouct_1生产者添加元素
consume_1消费者获取：element_result
consume_1消费者获取：element_result
consume_1消费者获取：element_result
consume_1消费者获取：element_result
consume_1消费者获取：element_result
consume_1消费者获取：element_result
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	
	
	
	
}
