package com.poc.coordinate.queue.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CoordinateService{

    @Autowired
    private final RabbitTemplate rabbitTemplate;

    public CoordinateService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public void execute() throws InterruptedException {
        cleaningQueue();
        UUID uuid = UUID.randomUUID();

        try {
            transactionOrder(uuid);
            transactionPayment(uuid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        confirmTransaction();
    }

    private static void cleaningQueue() throws InterruptedException {
        Thread.sleep(5000);
    }

    private void confirmTransaction() {
        rabbitTemplate.convertAndSend("order.confirm", true);
        rabbitTemplate.convertAndSend("payment.confirm", true);
    }

    private void transactionPayment(UUID uuid) throws Exception {
        rabbitTemplate.send("payment.started", new Message(uuid.toString().getBytes()));

        Long idPayment = (Long) this.rabbitTemplate.receiveAndConvert("payment.preparation", 10000);

        if (idPayment == null || idPayment == 0L){
            rabbitTemplate.convertAndSend("order.confirm", false);
            throw new Exception("Error call to Payment");
        }
    }

    private void transactionOrder(UUID uuid) throws Exception {
        rabbitTemplate.send("order.started", new Message(uuid.toString().getBytes()));

        Long idOrder = (Long) this.rabbitTemplate.receiveAndConvert("order.preparation", 10000);

        if(idOrder == null || idOrder == 0L){
            throw new Exception("Error call to Order");
        }
    }

}
