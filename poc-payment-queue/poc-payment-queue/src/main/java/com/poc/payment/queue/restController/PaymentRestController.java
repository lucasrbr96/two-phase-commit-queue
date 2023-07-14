package com.poc.payment.queue.restController;

import com.poc.payment.queue.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentRestController {

    @Autowired
    private PaymentService service;

    @PostMapping
    public void save(){
        //service.save();
    }
}
