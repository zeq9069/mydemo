package org.kyrincloud.Spider.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.kyrincloud.Spider.core.job.FetchCompanyInfoJob;
import org.kyrincloud.Spider.core.job.IndexAndCheckCodeJob;
import org.kyrincloud.Spider.core.queue.WaitFetchQueue;
import org.kyrincloud.Spider.core.queue.WaitInputCheckCodeQueue;
import org.kyrincloud.Spider.core.requestHeader.BrowserType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

@Controller
public class MainController {
	
	static{
		Timer t=new Timer();
		
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
					IndexAndCheckCodeJob index=new IndexAndCheckCodeJob();
					index.setBrowser(BrowserType.MAC_CHROME);
					index.start();
			}
		}, 10000, 10000);
	}
	
	
	@RequestMapping(value="/index.html")
	public ModelAndView name(ModelAndView model) {
		
	
		Calendar calendar=Calendar.getInstance();
		Set<String> keys=WaitInputCheckCodeQueue.getKeys();
		Map<String,String> urls=new HashMap<String, String>();
		for(String key:keys){
			urls.put(key,"upload/images/"+calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH)+"/"+key+".png");
		}
		model.addObject("urls", urls);
		model.setViewName("index");
		return model;
	}
	
	@RequestMapping(value="/checkcode/{key}/{code}",method=RequestMethod.GET)
	@ResponseBody
	public boolean fetchCode(@PathVariable("key") String key,@PathVariable("code") String code){
		JSONObject obj=WaitInputCheckCodeQueue.remove(key);
		if(obj==null){
			return false;
		}
		obj.put("checkcode", code);
		try {
			WaitFetchQueue.put(obj);
			(new FetchCompanyInfoJob()).start();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
