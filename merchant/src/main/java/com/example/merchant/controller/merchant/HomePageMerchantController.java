package com.example.merchant.controller.merchant;

import com.example.common.util.ReturnJson;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.HomePageService;
import com.example.merchant.service.PaymentOrderManyService;
import com.example.merchant.service.PaymentOrderService;
import io.swagger.annotations.*;
import lombok.extern.java.Log;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

@Api(value = "商户端首页内容的展示", tags = "商户端首页内容的展示")
@RestController
@RequestMapping("/merchant/homePage")
@Validated
public class HomePageMerchantController {

    @Resource
    private HomePageService homePageService;

    @Resource
    private PaymentOrderService paymentOrderService;

    @Resource
    private PaymentOrderManyService paymentOrderManyService;

    @PostMapping("/homePageInfo")
    @ApiOperation(value = "获取首页基本信息", notes = "获取首页基本信息", httpMethod = "POST")
    @LoginRequired
    public ReturnJson myWorker(@RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return homePageService.getHomePageInof(merchantId);
    }

    @PostMapping("/totalDayInfo")
    @LoginRequired
    @ApiOperation(value = "获取总包+分包今天的支付额", notes = "获取总包+分包今天的支付额", httpMethod = "POST")
    public ReturnJson totalDayInfo(@RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return paymentOrderService.getDay(merchantId);
    }

    @PostMapping("/totalWeekInfo")
    @LoginRequired
    @ApiOperation(value = "获取总包+分包本周的支付额", notes = "获取总包+分包本周的支付额", httpMethod = "POST")
    public ReturnJson totalWeekInfo(@RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return paymentOrderService.getWeek(merchantId);
    }

    @PostMapping("/totalMonthInfo")
    @LoginRequired
    @ApiOperation(value = "获取总包+分包本月的支付额", notes = "获取总包+分包本月的支付额", httpMethod = "POST")
    public ReturnJson totalMonthInfo(@RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return paymentOrderService.getMonth(merchantId);
    }

    @PostMapping("/totalYearInfo")
    @LoginRequired
    @ApiOperation(value = "获取总包+分包全年的支付额", notes = "获取总包+分包全年的支付额", httpMethod = "POST")
    public ReturnJson totalYearInfo(@RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return paymentOrderService.getYear(merchantId);
    }

    @PostMapping("/manyDayInfo")
    @LoginRequired
    @ApiOperation(value = "获取众包今天的支付额", notes = "获取众包今天的支付额", httpMethod = "POST")
    public ReturnJson manyDayInfo(@RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return paymentOrderManyService.getDay(merchantId);
    }

    @PostMapping("/manyWeekInfo")
    @LoginRequired
    @ApiOperation(value = "获取众包本周的支付额", notes = "获取众包本周的支付额", httpMethod = "POST")
    public ReturnJson manyWeekInfo(@RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return paymentOrderManyService.getWeek(merchantId);
    }

    @PostMapping("/manyMonthInfo")
    @LoginRequired
    @ApiOperation(value = "获取众包本月的支付额", notes = "获取众包本月的支付额", httpMethod = "POST")
    public ReturnJson manyMonthInfo(@RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return paymentOrderManyService.getMonth(merchantId);
    }

    @PostMapping("/manyYearInfo")
    @LoginRequired
    @ApiOperation(value = "获取众包全年的支付额", notes = "获取众包全年的支付额", httpMethod = "POST")
    public ReturnJson manyYearInfo(@RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return paymentOrderManyService.getYear(merchantId);
    }
}
