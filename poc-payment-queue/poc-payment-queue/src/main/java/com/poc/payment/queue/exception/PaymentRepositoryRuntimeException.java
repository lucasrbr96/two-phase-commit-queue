package com.poc.payment.queue.exception;

public class PaymentRepositoryRuntimeException extends RuntimeException{
    public PaymentRepositoryRuntimeException() {
        super();
    }

    public PaymentRepositoryRuntimeException(String message) {
        super(message);
    }
}
