package com.example.merchant.controller;

import com.example.common.util.ReturnJson;
import com.example.merchant.service.GreetingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "获取问候语", tags = "获取问候语")
@RestController
@RequestMapping("/greetings")
public class GreetingsController {
    @Resource
    private GreetingsService greetingsService;

    @GetMapping("/getGreetings")
    @ApiOperation(value = "获取问候语", notes = "获取问候语")
    public ReturnJson getTotalPayInventory() {
        return greetingsService.getGreetings();
    }
}
