package com.poc.coordinate.queue.restController;

import com.poc.coordinate.queue.service.CoordinateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeoutException;

@RestController
public class CoordinateRestController {

    @Autowired
    private CoordinateService service;

    @PostMapping
    public void execute() throws InterruptedException, TimeoutException {
        service.execute();
    }
}
