package com.pinyougou.page.service.impl;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinyougou.page.service.ItemPageService;

@Component
public class pageListener implements MessageListener {
	
	
	@Autowired
	private ItemPageService itemPageService;

	@Override
	public void onMessage(Message message) {
		
		
		try {
			TextMessage textMessage= (TextMessage) message;
			String id = textMessage.getText();
			System.out.println("接收到消息："+id);
			boolean b = itemPageService.genItemHtml(Long.parseLong(id));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
