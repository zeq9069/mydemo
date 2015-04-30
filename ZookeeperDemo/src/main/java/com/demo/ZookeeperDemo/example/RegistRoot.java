package com.demo.ZookeeperDemo.example;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
/**
 * ***********************
 * 
 *  zookeeper 创建root services 目录,注册根目录
 *  
 * 所有的客户端的PID存放到该root services 目录下
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月30日]
 *
 */
public class RegistRoot {
	
	public static void main(String args[]) throws Exception{
		
		ZooKeeper zk=new ZooKeeper("127.0.0.1", 2000, null);
		
		while(!zk.getState().isConnected()){
			;
		}
		if(zk.exists("/services",false)==null){
			zk.create("/services",null , Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
		}
		
		System.out.println("The /services has been created !");
		
		Thread.sleep(Long.MAX_VALUE);
				
	}
}
