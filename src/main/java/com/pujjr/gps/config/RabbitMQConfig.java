package com.pujjr.gps.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wen
 * @date 创建时间：2017年7月1日 上午1:33:47
 *
 */
@Configuration
@EnableRabbit
public class RabbitMQConfig {
	/**
	 * topic交换机名
	 */
	public static final String EXCHANGE = "icbc-mq-exchange";
	/**
	 * 工行面签消息队列申请的topic
	 */
	public static final String ICBC_MQ_APPLY = "icbc.mq.apply";

	/**
	 * 工行面签消息队列结果的topic
	 */
	public static final String ICBC_MQ_RESULT = "icbc.mq.result";

	@Bean
	public Queue applyQueue() {
		return new Queue(ICBC_MQ_APPLY, true);
	}

	@Bean
	public Queue resultQueue() {
		return new Queue(ICBC_MQ_RESULT, true);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(EXCHANGE);
	}

	@Bean
	Binding applyBinding() {
		return BindingBuilder.bind(applyQueue()).to(exchange()).with(ICBC_MQ_APPLY);
	}

	@Bean
	Binding resultBinding() {
		return BindingBuilder.bind(resultQueue()).to(exchange()).with(ICBC_MQ_RESULT);
	}

	// @Bean
	// public ConnectionFactory connectionFactory() {
	// CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
	// connectionFactory.setAddresses("127.0.0.1:5672");
	// connectionFactory.setUsername("guest");
	// connectionFactory.setPassword("guest");
	// connectionFactory.setVirtualHost("/");
	// connectionFactory.setPublisherConfirms(true); // 必须要设置
	// return connectionFactory;
	// }

}