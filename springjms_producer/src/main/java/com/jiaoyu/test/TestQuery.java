package com.jiaoyu.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.jiaoyu.demo.QueueProducer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext-jms-producer.xml")
public class TestQuery {

	@Autowired
	private QueueProducer queneProducer;
	
	
	@Test
	public void testSend() {
		queneProducer.sendTextMessage("queueProducer sendMessage haha;");
	}
	
}
