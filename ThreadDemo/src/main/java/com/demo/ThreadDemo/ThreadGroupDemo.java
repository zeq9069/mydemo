package com.demo.ThreadDemo;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池，多线程
 * @author Kyrin
 *
 */
public class ThreadGroupDemo {
	private static final int CORE_POOL_SIZE = 1;//线程池中保存的线程数量
	private static final int MAXMUM_POOL_SIZE = 10;//最大启动的线程数
	private static final int KEEP_ACTIVE_TIME = 60;//存活时间
	private static TimeUnit time = TimeUnit.SECONDS;
	private static int count = 2000;
	private static final List<Integer> list = new LinkedList<Integer>();
	private static BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(count);
	private static ThreadPoolExecutor threadGroup = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXMUM_POOL_SIZE,
			KEEP_ACTIVE_TIME, time, workQueue);
	private static ScheduledThreadPoolExecutor stp = new ScheduledThreadPoolExecutor(1);

	//ThreadPoolExecutor测试
	public static void m1() {
		threadGroup.allowCoreThreadTimeOut(true);
		final Random rand = new Random();
		for (int i = 1; i <= count; i++) {
			threadGroup.execute(new Runnable() {
				public void run() {
					int res = rand.nextInt();
					list.add(res);
					System.out.println(res + "---" + workQueue.size() + "---" + threadGroup.getActiveCount());
				}
			});
		}
		System.out.println(workQueue.size() + "-------------------1-----------------" + threadGroup.getActiveCount());
		threadGroup.shutdown();
		System.out.println(workQueue.size() + "-----------------2-------------------" + threadGroup.getActiveCount());
		try {
			System.out.println(workQueue.size() + "---------------3---------------------"
					+ threadGroup.getActiveCount());

			threadGroup.awaitTermination(0, TimeUnit.DAYS);

			System.out.println(workQueue.size() + "---------------4---------------------"
					+ threadGroup.getActiveCount());

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//ScheduledThreadPoolExecutor测试
	public static void m2() {

		Call call = new Call();

		stp.schedule(call, 1, TimeUnit.SECONDS);
		stp.setKeepAliveTime(1, TimeUnit.SECONDS);
		stp.allowCoreThreadTimeOut(true);
		final Random rand = new Random();
		for (int i = 1; i <= count; i++) {
			stp.execute(new Runnable() {
				public void run() {
					int res = rand.nextInt();
					list.add(res);
					System.out.println(res + "---" + workQueue.size() + "---" + stp.getActiveCount());
				}
			});
		}
	}

	//callable测试
	public static void m3() {

		Call call = new Call();
		FutureTask<Object> f = new FutureTask<Object>(call);

		Thread t = new Thread(f);
		t.start();

		try {
			System.out.println("Callable的返回值：" + f.get().toString());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		long stat = System.currentTimeMillis();
		m3();
		long end = System.currentTimeMillis();
		System.out.println("总共" + CORE_POOL_SIZE + "个线程，" + count + "个循环，共消耗：" + (end - stat));
	}

}

class Call implements Callable<Object> {
	String res = "1111111";

	public Object call() throws Exception {
		res = "22222222222";
		Thread.sleep(100000);
		System.out.println("---不好意思，我要关闭了-----");
		return "2222";
	}

	public String m() {
		return res;
	}
}
