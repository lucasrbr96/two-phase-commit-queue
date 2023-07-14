package com.poc.order.queue;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class PocOrderQueueApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocOrderQueueApplication.class, args);
	}

}
