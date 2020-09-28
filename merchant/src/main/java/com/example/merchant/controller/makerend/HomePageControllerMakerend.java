package com.example.merchant.controller.makerend;


import com.example.common.util.ReturnJson;
import com.example.merchant.controller.merchant.InvoiceControllerMerchant;
import com.example.merchant.service.WorkerService;
import com.example.mybatis.dto.TobeinvoicedDto;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/makerend/homePage")
@Validated
@Api(value = "小程序首页内容的展示", tags = "小程序首页内容的展示")
public class HomePageControllerMakerend {

    private static Logger logger = LoggerFactory.getLogger(HomePageControllerMakerend.class);

    @Autowired
    private WorkerService workerService;

    @ApiOperation("赚取金额")
    @GetMapping(value = "/setWorkerMakeMoney")
    public ReturnJson setWorkerMakeMoney() {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        try {
            returnJson = workerService.setWorkerMakeMoney();
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }


}
