package com.tsystems.trainsProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/MyData")
public class RestController {

    @RequestMapping(value="/{time}", method = RequestMethod.GET)
    public @ResponseBody
    MyData getMyData( @PathVariable String time) {
        return new MyData(time, "REST GET Call !!!");
    }

    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody MyData putMyData(
            @RequestBody MyData md) {

        return md;
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody MyData postMyData() {

        return new MyData("1",
                "REST POST Call !!!");
    }

    @RequestMapping(value="/{time}", method = RequestMethod.DELETE)
    public @ResponseBody MyData deleteMyData(
            @PathVariable String time) {

        return new MyData(time, "REST DELETE Call !!!");
    }
}