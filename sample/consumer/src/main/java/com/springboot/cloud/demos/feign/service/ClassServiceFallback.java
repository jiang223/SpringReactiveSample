package com.springboot.cloud.demos.feign.service;

import com.springboot.cloud.common.core.entity.vo.Result;
import com.springboot.cloud.common.core.exception.SystemErrorType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ClassServiceFallback implements ClassService {
    @Override
    public String users(String name) {
        return "FAIL";
    }

    @Override
    public Result users(Map map) {
        return Result.fail(SystemErrorType.SYSTEM_BUSY);
    }
}
