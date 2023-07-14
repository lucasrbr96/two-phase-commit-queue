package com.poc.coordinate.queue;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class PocCoordinateQueueApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocCoordinateQueueApplication.class, args);
	}

}
