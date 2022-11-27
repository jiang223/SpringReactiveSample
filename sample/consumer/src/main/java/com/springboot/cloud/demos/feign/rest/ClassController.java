package com.springboot.cloud.demos.feign.rest;

import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.demos.feign.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ClassController {


    @Autowired
    private ClassService classService;

    @GetMapping("/classes")
    public String hello(@RequestParam String name) {
        String aa=classService.users(name)+"123123221111231344";
        return aa;
    }

    @PostMapping("/classes")
    public Result hello(@RequestBody Map map) {
        return classService.users(map);
    }

}
