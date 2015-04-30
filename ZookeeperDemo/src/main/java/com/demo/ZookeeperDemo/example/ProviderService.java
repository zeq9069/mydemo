package com.demo.ZookeeperDemo.example;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * ****************************************************
 * 
 * 
 * 注册服务，将自身的PID注册到zookeeper中的/services/child 下面
 *
 *
 * *****************************************************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月30日]
 *
 */
public class ProviderService {
	
	public static void main(String a[]) throws IOException, KeeperException, InterruptedException{
		
		ZooKeeper zoo=new ZooKeeper("127.0.0.1", 2000, null);
		
		while(!zoo.getState().isConnected()){
			;
		}
		String pid=getPID();
		zoo.create("/services/child", pid.getBytes(),Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		
		System.out.println("pid 已经注册到 zookeeper! pid="+pid);
		
		Thread.sleep(Long.MAX_VALUE);
	}
	
	
	private static String getPID(){
		RuntimeMXBean runtime=ManagementFactory.getRuntimeMXBean();
		String name=runtime.getName();
		String pid=name.substring(0,name.indexOf("@"));
		return pid;
	}
	
	

}
