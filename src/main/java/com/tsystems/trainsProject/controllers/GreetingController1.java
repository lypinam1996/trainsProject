package com.tsystems.trainsProject.controllers;

import java.util.concurrent.atomic.AtomicLong;

import com.tsystems.trainsProject.models.Car;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController1 {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/greeting", produces = MediaType.APPLICATION_JSON_VALUE)
    public Car greeting(@RequestParam(value="housePower", defaultValue="World") String name) {
        return new Car(String.format(template, name),1, 2);
    }
}
