package com.poc.payment.queue.exception;

public class RollBackRunTimeException extends RuntimeException{

    public RollBackRunTimeException() {
        super();
    }

    public RollBackRunTimeException(String message) {
        super(message);
    }
}
