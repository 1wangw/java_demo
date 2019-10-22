package com.ttit.activemq;

import com.ttit.activemq.config.ActiveMQConfig;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.JMSException;
import javax.jms.MessageNotWriteableException;
import javax.jms.Topic;

@SpringBootTest(classes = ActiveMQConfig.class)
class ActivemqTestApplicationTests {

    @Autowired
    JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    ActiveMQTopic topic;

    @Test
    void contextLoads() throws JMSException {
        ActiveMQTextMessage activeMQTextMessage = new ActiveMQTextMessage();
        activeMQTextMessage.setText("123");
        System.out.println("@@@@ " + topic.getTopicName());
        ActiveMQTopic topic = new ActiveMQTopic("aa");
        jmsMessagingTemplate.convertAndSend(this.topic,activeMQTextMessage);
    }

}
