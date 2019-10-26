package com.jiaoyu.demo;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class TopicProducer {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private Destination topicTextDestination;
	
	
	/**
	 * 广播形式发送文本
	 * @param text
	 */
	public void  sendMessageByTopic(final String text) {
		
		
		jmsTemplate.send(topicTextDestination, new MessageCreator() {
			
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				return session.createTextMessage(text);
			}
		});
		
		
	}
	
}
