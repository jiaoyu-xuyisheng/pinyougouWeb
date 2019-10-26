package com.jiaoyu.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueueController {
	
	@Autowired
	private JmsMessagingTemplate jsmMessageTemplate;
	
	
	@RequestMapping("/sendmap")
	public void sendMap() {
		Map map=new HashMap<>();
		map.put("mobile", "13424365224");
		map.put("template_code", "SMS_147975686");	
		map.put("sign_name", "521jiaoyu");
		map.put("param", "{\"code\":\"102931\"}");
		System.out.println(map);
		jsmMessageTemplate.convertAndSend("sms",map);
	}
	
}
