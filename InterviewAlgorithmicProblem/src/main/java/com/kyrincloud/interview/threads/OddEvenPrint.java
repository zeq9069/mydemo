package com.kyrincloud.interview.threads;

/**
 * 奇数偶数顺序打印1-100
 * 考察点：线程之间的协作，一般会通过锁来实现线程之间的交替执行
 * @author kyrin
 *
 */
public class OddEvenPrint {
	
	static class Obj {
		public static int num = 1;
	}
	
	private static volatile boolean flag = false;
	
	public static void main(String[] args) throws InterruptedException {

		final Obj obj = new Obj();

		// 奇数线程
		Thread odd = new Thread(new Runnable() {

			public void run() {

				synchronized (obj) {
					for (; obj.num < 100;)
						if (flag) {
							try {
								obj.wait();
							} catch (InterruptedException e) {
							}
						} else {
							System.out.println("奇数 -> " + obj.num++);
							flag = true;
							obj.notify();
						}
				}
			}
		});

		// 偶数线程
		Thread even = new Thread(new Runnable() {

			public void run() {
				synchronized (obj) {
					for (; obj.num <= 100;)
						if (!flag) {
							try {
								obj.wait();
							} catch (InterruptedException e) {
							}
						} else {
							System.out.println("偶数 -> " + obj.num++);
							flag = false;
							obj.notify();
						}
				}
			}
		});

		odd.start();
		even.start();

		odd.join();
		even.join();

	}
}
