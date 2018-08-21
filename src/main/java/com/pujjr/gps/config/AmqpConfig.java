package com.pujjr.gps.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * 消息队列配置
 * 
 * @author tom
 *
 */
@Configuration
public class AmqpConfig {
	public static final String EXCHANGE = "pujjr.wms.exchange";
	public static final String ROUTINGKEY = "pujjr.wms.routing";

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setAddresses("172.18.10.47:5672");
		connectionFactory.setUsername("test");
		connectionFactory.setPassword("test");
		connectionFactory.setVirtualHost("test");
		// 消息回调
		connectionFactory.setPublisherConfirms(true);
		return connectionFactory;
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	/**
	 * 必须是prototype类型
	 * 
	 * @return
	 */
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		return template;
	}

	/**
	 * 针对消费者配置 1. 设置交换机类型 2. 将队列绑定到交换机
	 * 
	 * 
	 * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念 HeadersExchange ：通过添加属性key-value匹配 DirectExchange:按照routingkey分发到指定队列 TopicExchange:多关键字匹配
	 */
	@Bean
	public DirectExchange defaultExchange() {
		return new DirectExchange(EXCHANGE);
	}

	@Bean
	public Queue queue() {
		// 队列持久
		return new Queue("pujjr.wms.queue", true);

	}

	@Bean
	public Binding binding() {
		return BindingBuilder.bind(queue()).to(defaultExchange()).with(AmqpConfig.ROUTINGKEY);
	}

}
