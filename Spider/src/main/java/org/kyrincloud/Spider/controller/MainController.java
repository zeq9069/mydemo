package org.kyrincloud.Spider.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.kyrincloud.Spider.core.constant.Constant;
import org.kyrincloud.Spider.core.job.FetchCompanyInfoJob;
import org.kyrincloud.Spider.core.job.IndexAndCheckCodeJob; 
import org.kyrincloud.Spider.core.queue.WaitFetchQueue;
import org.kyrincloud.Spider.core.queue.WaitInputCheckCodeQueue;
import org.kyrincloud.Spider.core.requestHeader.BrowserType;
import org.kyrincloud.Spider.core.util.ImageUtil;
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
				for(int i=0;i<5;i++){
					IndexAndCheckCodeJob index=new IndexAndCheckCodeJob();
					index.setBrowser(BrowserType.MAC_CHROME);
					index.start();
				}
			}
		}, 5000, 5000);
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				for(int i=0;i<5;i++){
					FetchCompanyInfoJob company=new FetchCompanyInfoJob();
					company.start();
				}
			}
		}, 1000, 1000);
	}
	
	
	@RequestMapping(value="/index.html")
	public ModelAndView name(ModelAndView model) {
		Calendar calendar=Calendar.getInstance();
		Map<String,JSONObject> objectValue=WaitInputCheckCodeQueue.removeTopN(10);
		Map<String,String> urls=new HashMap<String, String>();
		for(String key:objectValue.keySet()){
			urls.put(key,"upload/images/"+calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH)+"/"+key+".png");
		}
		model.addObject("urls", urls);
		model.addObject("objectValue",objectValue);
		model.setViewName("index");
		return model;
	}
	
	@RequestMapping(value="/checkcode/{key}/{code}",method=RequestMethod.GET)
	@ResponseBody
	public boolean fetchCode(@PathVariable("key") String key,@PathVariable("code") String code,
			String cookie, String credit_ticket, String keyword){
		JSONObject object=new JSONObject();
		object.put("Cookie", cookie);
		object.put("credit_ticket", credit_ticket);
		object.put("timestamp", key);
		object.put("keyword", keyword);
		object.put("checkcode", code);
		object.put("browser",BrowserType.MAC_CHROME );
		ImageUtil.removeImg(Constant.checkCodeImagePath,key+".png");//删除已经输入过的验证码图片
		try {
			WaitFetchQueue.put(object);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
