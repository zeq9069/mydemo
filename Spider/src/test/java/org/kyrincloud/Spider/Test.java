package org.kyrincloud.Spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.kyrincloud.Spider.core.job.FetchCompanyInfoJob;
import org.kyrincloud.Spider.core.job.IndexAndCheckCodeJob;
import org.kyrincloud.Spider.core.queue.WaitFetchQueue;
import org.kyrincloud.Spider.core.queue.WaitInputCheckCodeQueue;
import org.kyrincloud.Spider.core.requestHeader.BrowserType;

import com.alibaba.fastjson.JSONObject;

/**
 * 测试
 * @author kyrin
 *
 */
public class Test {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		for(int i=0;i<2;i++){
			IndexAndCheckCodeJob index=new IndexAndCheckCodeJob();
			if(i==0){
				index.setBrowser(BrowserType.MAC_CHROME);
			}else if(i==1){
				index.setBrowser(BrowserType.WIN_CHROME);
			}else{
				index.setBrowser(BrowserType.WIN_FIREFOX);
			}
			index.start();
		}
		
		/*java.util.Timer timer=new java.util.Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				IndexAndCheckCodeJob index=new IndexAndCheckCodeJob();
					index.setBrowser(BrowserType.MAC_CHROME);
					index.run();
			}
		}, 100);*/
		
		InputStreamReader is=new InputStreamReader(System.in);
		BufferedReader br=new BufferedReader(is);
		String str="";
		while(!(str=br.readLine()).equals("ok")){
			String[] values=str.split(",");
			String currentTimeMillis=values[0];
			String checkcode=values[1];
			JSONObject obj=WaitInputCheckCodeQueue.remove(values[0]);
			obj.put("timestamp", currentTimeMillis);
			obj.put("checkcode", checkcode);
			WaitFetchQueue.add(obj);
		}
		
		for(int i=0;i<2;i++){
			(new FetchCompanyInfoJob()).start();
		}
	}
	
}
