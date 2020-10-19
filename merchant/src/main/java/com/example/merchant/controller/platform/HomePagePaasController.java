package com.example.merchant.controller.platform;

import com.example.common.util.ReturnJson;
import com.example.merchant.exception.CommonException;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.HomePageService;
import com.example.merchant.service.PaymentOrderManyService;
import com.example.merchant.service.PaymentOrderService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value = "平台端首页内容的展示", tags = "平台端首页内容的展示")
@RestController
@RequestMapping("/platform/homePage")
@Validated
public class HomePagePaasController {

    @Resource
    private HomePageService homePageService;

    @Resource
    private PaymentOrderService paymentOrderService;

    @Resource
    private PaymentOrderManyService paymentOrderManyService;

    @PostMapping("/homePageInfo")
    @ApiOperation(value = "获取首页基本信息", notes = "获取首页基本信息", httpMethod = "POST")
    @LoginRequired
    public ReturnJson myWorker(@RequestAttribute String userId) throws CommonException {
        return homePageService.getHomePageInofpaas(userId);
    }

    @PostMapping("/totalDayInfo")
    @ApiOperation(value = "获取总包+分包今天的支付额", notes = "获取总包+分包今天的支付额", httpMethod = "POST")
    @LoginRequired
    public ReturnJson totalDayInfo(@RequestAttribute String userId) throws CommonException {
        return paymentOrderService.getDayPaas(userId);
    }

    @PostMapping("/totalWeekInfo")
    @ApiOperation(value = "获取总包+分包本周的支付额", notes = "获取总包+分包本周的支付额", httpMethod = "POST")
    @LoginRequired
    public ReturnJson totalWeekInfo(@RequestAttribute String userId) throws CommonException {
        return paymentOrderService.getWeekPaas(userId);
    }

    @PostMapping("/totalMonthInfo")
    @ApiOperation(value = "获取总包+分包本月的支付额", notes = "获取总包+分包本月的支付额", httpMethod = "POST")
    @LoginRequired
    public ReturnJson totalMonthInfo(@RequestAttribute String userId) throws CommonException {
        return paymentOrderService.getMonthPaas(userId);
    }

    @PostMapping("/totalYearInfo")
    @ApiOperation(value = "获取总包+分包全年的支付额", notes = "获取总包+分包全年的支付额", httpMethod = "POST")
    @LoginRequired
    public ReturnJson totalYearInfo(@RequestAttribute String userId) throws CommonException {
        return paymentOrderService.getYearPaas(userId);
    }

    @PostMapping("/manyDayInfo")
    @ApiOperation(value = "获取众包今天的支付额", notes = "获取众包今天的支付额", httpMethod = "POST")
    @LoginRequired
    public ReturnJson manyDayInfo(@RequestAttribute String userId) throws CommonException {
        return paymentOrderManyService.getDayPaas(userId);
    }

    @PostMapping("/manyWeekInfo")
    @ApiOperation(value = "获取众包本周的支付额", notes = "获取众包本周的支付额", httpMethod = "POST")
    @LoginRequired
    public ReturnJson manyWeekInfo(@RequestAttribute String userId) throws CommonException {
        return paymentOrderManyService.getWeekPaas(userId);
    }

    @PostMapping("/manyMonthInfo")
    @ApiOperation(value = "获取众包本月的支付额", notes = "获取众包本月的支付额", httpMethod = "POST")
    @LoginRequired
    public ReturnJson manyMonthInfo(@RequestAttribute String userId) throws CommonException {
        return paymentOrderManyService.getMonthPaas(userId);
    }

    @PostMapping("/manyYearInfo")
    @ApiOperation(value = "获取众包全年的支付额", notes = "获取众包全年的支付额", httpMethod = "POST")
    @LoginRequired
    public ReturnJson manyYearInfo(@RequestAttribute String userId) throws CommonException {
        return paymentOrderManyService.getYearPaas(userId);
    }
}
