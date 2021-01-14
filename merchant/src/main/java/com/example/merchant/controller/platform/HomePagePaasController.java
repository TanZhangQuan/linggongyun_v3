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
    @ApiOperation(value = "获取首页基本信息", notes = "获取首页基本信息")
    @LoginRequired
    public ReturnJson myWorker(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String userId) throws CommonException {
        return homePageService.getHomePageInofpaas(userId);
    }

    @PostMapping("/totalDayInfo")
    @ApiOperation(value = "获取总包+分包今天的支付额", notes = "获取总包+分包今天的支付额")
    @LoginRequired
    public ReturnJson totalDayInfo(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String userId) throws CommonException {
        return paymentOrderService.getDaypaa(userId);
    }

    @PostMapping("/totalWeekInfo")
    @ApiOperation(value = "获取总包+分包本周的支付额", notes = "获取总包+分包本周的支付额")
    @LoginRequired
    public ReturnJson totalWeekInfo(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String userId) throws CommonException {
        return paymentOrderService.getWeekPaa(userId);
    }

    @PostMapping("/totalMonthInfo")
    @ApiOperation(value = "获取总包+分包本月的支付额", notes = "获取总包+分包本月的支付额")
    @LoginRequired
    public ReturnJson totalMonthInfo(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String userId) throws CommonException {
        return paymentOrderService.getMonthPaa(userId);
    }

    @PostMapping("/totalYearInfo")
    @ApiOperation(value = "获取总包+分包全年的支付额", notes = "获取总包+分包全年的支付额")
    @LoginRequired
    public ReturnJson totalYearInfo(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String userId) throws CommonException {
        return paymentOrderService.getYearPaa(userId);
    }

    @PostMapping("/manyDayInfo")
    @ApiOperation(value = "获取众包今天的支付额", notes = "获取众包今天的支付额")
    @LoginRequired
    public ReturnJson manyDayInfo(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String userId) throws CommonException {
        return paymentOrderManyService.getDayPaa(userId);
    }

    @PostMapping("/manyWeekInfo")
    @ApiOperation(value = "获取众包本周的支付额", notes = "获取众包本周的支付额")
    @LoginRequired
    public ReturnJson manyWeekInfo(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String userId) throws CommonException {
        return paymentOrderManyService.getWeekPaa(userId);
    }

    @PostMapping("/manyMonthInfo")
    @ApiOperation(value = "获取众包本月的支付额", notes = "获取众包本月的支付额")
    @LoginRequired
    public ReturnJson manyMonthInfo(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String userId) throws CommonException {
        return paymentOrderManyService.getMonthPaa(userId);
    }

    @PostMapping("/manyYearInfo")
    @ApiOperation(value = "获取众包全年的支付额", notes = "获取众包全年的支付额")
    @LoginRequired
    public ReturnJson manyYearInfo(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String userId) throws CommonException {
        return paymentOrderManyService.getYearPaa(userId);
    }
}
