package com.poc.payment.queue.service;

import com.poc.payment.queue.domain.Payment;
import com.poc.payment.queue.exception.PaymentRepositoryRuntimeException;
import com.poc.payment.queue.repository.PaymentRepository;
import com.rabbitmq.client.Channel;
import jakarta.transaction.RollbackException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final Long flagError = 0L;

    @RabbitListener(queues = "payment.started")
    @Transactional(rollbackFor = Exception.class)
    public void save(Message message, Channel channel) throws Exception {

        try {
            Payment paymentSaved;
            try {
                paymentSaved = createAndSavePayment();
            }catch (Exception e){
                throw new PaymentRepositoryRuntimeException("Error in payment repository");
            }
            checkPrepared(paymentSaved.getId());

            Boolean isConfirm = receiveConfirmCommit();

            if (isConfirm == null ||  Boolean.FALSE.equals(isConfirm)) {
                throw new RollbackException("Rollback");
            }

        }catch (PaymentRepositoryRuntimeException p){
            checkPrepared(flagError);
            removeMessageQueue(message, channel);
            throw new Exception("Rollback");
        } catch (Exception r){
            removeMessageQueue(message, channel);
            throw new Exception("Rollback");
        }

    }

    private Boolean receiveConfirmCommit() {
        return (Boolean) rabbitTemplate.receiveAndConvert("payment.confirm", 20000);
    }

    private void checkPrepared(Long paymentSaved) {
        rabbitTemplate.convertAndSend("payment.preparation", paymentSaved);
    }

    private Payment createAndSavePayment() {
        //logic
        Payment payment = new Payment();
        repository.save(payment);
        return payment;
    }

    private static void removeMessageQueue(Message message, Channel channel) throws IOException {
        channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
    }

}
