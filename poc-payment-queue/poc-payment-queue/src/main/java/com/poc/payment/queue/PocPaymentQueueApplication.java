package com.poc.payment.queue;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class PocPaymentQueueApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocPaymentQueueApplication.class, args);
	}

}
