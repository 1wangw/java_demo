package com.ttit.activemq.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTempTopic;
import org.apache.activemq.command.ActiveMQTopic;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;
import java.net.URI;

/**
 * @author ：jialongzhao
 * @date ：Created in 2019-10-22 上午 11:00
 * @description：
 */
@Configuration
@Slf4j
public class ActiveMQConfig {

    private static final String QUEUE_NAME = "aa";
    private static final String TOPIC_NAME = "cc";
    @Value("${spring.activemq.user}")
    private String userName;
    @Value("${spring.activemq.password}")
    private String password;
    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Bean
    ActiveMQTopic topic() {
        log.debug("init topic {}", TOPIC_NAME);
        // 创建主题
        return new ActiveMQTopic(ActiveMQConfig.TOPIC_NAME);
    }

    @Bean
    ActiveMQQueue queue() {
        log.debug("init queue {}", QUEUE_NAME);
        // 创建队列
        return new ActiveMQQueue(ActiveMQConfig.QUEUE_NAME);
    }

    @Bean
    ActiveMQConnectionFactory jmsConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(userName, password, brokerUrl);
        // 创建连接工厂
        return activeMQConnectionFactory;
    }

    @Bean
    JmsMessagingTemplate messagingTemplate(ConnectionFactory connectionFactory) {
        log.debug("create JmsMessagingTemplate");
        // 创建消息发送模板操作类
        return new JmsMessagingTemplate(connectionFactory);
    }

    @Bean
    JmsListenerContainerFactory<DefaultMessageListenerContainer> queueJmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory defaultMessageListenerContainer = new DefaultJmsListenerContainerFactory();
        defaultMessageListenerContainer.setPubSubDomain(false);
        defaultMessageListenerContainer.setConnectionFactory(connectionFactory);
        // 创建队列监听容器
        return defaultMessageListenerContainer;
    }

    @Bean
    JmsListenerContainerFactory<DefaultMessageListenerContainer> topicJmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory defaultMessageListenerContainer = new DefaultJmsListenerContainerFactory();
        defaultMessageListenerContainer.setPubSubDomain(true);
        // 设置持久订阅的客户端id
        // 不同的destination 需要的listenerContainerFactory 的 clientId必须不同，所以clientId最好根据业务类型来命名。
        // 相同的destination 在分布式部署下如果clientId 相同则后启动的应用无法消费该desination
        // 所以要添加 新的destination 的消费者，必须要新建对应的listenerContainerFactory，并且clientId不同
        defaultMessageListenerContainer.setClientId("AA");
        defaultMessageListenerContainer.setSubscriptionDurable(true);
        defaultMessageListenerContainer.setConnectionFactory(connectionFactory);
        // 创建主题监听容器
        return defaultMessageListenerContainer;
    }
}