package com.jiaoyu.demo;

import java.util.Map;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
	
	//jms监听器
	@JmsListener(destination="itcast_map")
	public void readMessage(Map map) {
		
		System.out.println(map);
		
	}
}
