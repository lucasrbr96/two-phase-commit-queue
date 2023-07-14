package com.poc.order.queue.restController;

import com.poc.order.queue.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

    @Autowired
    private OrderService service;

    @PostMapping
    public void save(){
        //service.save();
    }
}
