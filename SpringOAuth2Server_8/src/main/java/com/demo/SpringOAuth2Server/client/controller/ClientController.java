package com.demo.SpringOAuth2Server.client.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * oauth2.0 client controller
 * 
 * @author zhangerqiang
 *
 */
@Controller
public class ClientController {
	
	@Autowired
	private OAuth2RestOperations sinaRestTemplate;
	@Autowired
	private OAuth2ClientContext oauth2ClientContext;
	
	@Value("${sinaResourceURL}")
	private String resourceURL;
	
	@RequestMapping(value="/cc/client",method=RequestMethod.GET)
	@ResponseBody
	public String client(Model model){
		InputStream result = new ByteArrayInputStream(sinaRestTemplate.getForObject(
				URI.create(resourceURL), byte[].class));
		return convertStreamToString(result);
	}
	
	
	  public String convertStreamToString(InputStream is) {   
		   BufferedReader reader = new BufferedReader(new InputStreamReader(is));   
		        StringBuilder sb = new StringBuilder();   
		        String line = null;   
		        try {   
		            while ((line = reader.readLine()) != null) {   
		                sb.append(line + "\n");   
		            }   
		        } catch (IOException e) {   
		            e.printStackTrace();   
		        } finally {   
		            try {   
		                is.close();   
		            } catch (IOException e) {   
		                e.printStackTrace();   
		            }   
		        }   
		        return sb.toString();   
		    }   
	
}
