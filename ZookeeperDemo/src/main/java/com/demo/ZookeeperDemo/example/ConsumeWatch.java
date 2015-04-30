package com.demo.ZookeeperDemo.example;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;


/**
 * ***********************
 * 
 *  监控/services下得数据动态
 *
 * ***********************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年4月30日]
 *
 */
public class ConsumeWatch implements Watcher{
	
	
	
	private final ZooKeeper zoo;
	
	public ConsumeWatch(ZooKeeper zoo){
		super();
		this.zoo=zoo;
	}
	
	public static void main(String args[]) throws IOException, InterruptedException, KeeperException{
		
		ZooKeeper zoo=new ZooKeeper("127.0.0.1",2000,null);
		
		while(!zoo.getState().isConnected()){
			;
		}
		
		ConsumeWatch watch=new ConsumeWatch(zoo);
		
		watch.handle();
		
		Thread.sleep(Long.MAX_VALUE);
		
	}
	
	
	
	private void handle() throws KeeperException, InterruptedException{
		
		List<String> pathList=zoo.getChildren("/services", this);
		
		for(String path :pathList){
			byte[] pid = zoo.getData("/services/"+path, false, null);
			System.out.println("The child pid is "+new String(pid));
		}
	}
	
	

	public void process(WatchedEvent event) {
		if (event.getType() == EventType.NodeChildrenChanged) {//遇子结点(provider)变化，则处理一下所有子结点  
            try {
				handle();
			} catch (KeeperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        }  
	}

}
