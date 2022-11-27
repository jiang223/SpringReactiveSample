package com.springboot.cloud.common.core.rest;

import org.springframework.web.bind.annotation.*;



@RestController
public class HelloController {

    @RequestMapping(method = RequestMethod.GET, value = "/hello/{name}")
    public String hello(@PathVariable String name) {
        return  "22";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public String world(@RequestParam String name) {
        return "success212311112312342343";
    }
}