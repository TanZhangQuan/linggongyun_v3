package com.example.merchant.controller.makerend;


import com.example.common.util.ReturnJson;
import com.example.merchant.service.WorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "小程序首页内容的展示", tags = "小程序首页内容的展示")
@RestController
@RequestMapping("/makerend/homePage")
@Validated
public class HomePageMakerendController {

    @Resource
    private WorkerService workerService;

    @ApiOperation("赚取金额")
    @GetMapping(value = "/setWorkerMakeMoney")
    public ReturnJson setWorkerMakeMoney() {
        return workerService.setWorkerMakeMoney();
    }


}
