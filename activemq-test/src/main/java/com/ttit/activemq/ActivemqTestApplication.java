package com.ttit.activemq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class ActivemqTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivemqTestApplication.class, args);
    }

   /* @JmsListener(destination = "aa")
    public void test(Message message) throws JMSException {
        TextMessage textMessage = (TextMessage) message;
        System.out.println(textMessage.getText());
    }
*/


}
