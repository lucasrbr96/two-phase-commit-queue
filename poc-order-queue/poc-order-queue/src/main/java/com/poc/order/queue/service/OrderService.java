package com.poc.order.queue.service;

import com.poc.order.queue.domain.Order_;
import com.poc.order.queue.repository.OrderRepository;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;


@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Transactional(rollbackFor = Exception.class)
    @RabbitListener(queues = "order.started")
    public void save(Message message, Channel channel) throws Exception {
        try {

            Order_ save = createAndSave();

            checkPrepared(save);

            Boolean isConfirm = receiveConfirmCommit();


            if (isConfirm == null ||  Boolean.FALSE.equals(isConfirm)) {
                throw new Exception("Rollback");
            }

        }catch (Exception r){
            removeQueue(message, channel);
            throw r;
        }
    }

    private Boolean receiveConfirmCommit() {
        Boolean isConfirm = (Boolean) rabbitTemplate.receiveAndConvert("order.confirm", 20000);
        return isConfirm;
    }

    private void checkPrepared(Order_ save) {
        rabbitTemplate.convertAndSend("order.preparation", save.getId());
    }

    private Order_ createAndSave() {
        Order_ order = new Order_();
        Order_ save = repository.save(order);
        return save;
    }

    private static void removeQueue(Message message, Channel channel) throws IOException {
        channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
    }

}
