package com.example.merchant.controller.merchant;

import com.example.common.util.ReturnJson;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.HomePageService;
import com.example.merchant.service.PaymentOrderManyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "商户端首页内容的展示", tags = "商户端首页内容的展示")
@RestController
@RequestMapping("/merchant/homePage")
@Validated
public class HomePageMerchantController {

    @Resource
    private HomePageService homePageService;

    @Resource
    private PaymentOrderManyService paymentOrderManyService;

    @PostMapping("/homePageInfo")
    @ApiOperation(value = "获取首页基本信息", notes = "获取首页基本信息")
    @LoginRequired
    public ReturnJson myWorker(@RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return homePageService.getHomePageInfo(merchantId);
    }

    @PostMapping("/totalDayInfo")
    @LoginRequired
    @ApiOperation(value = "获取总包+分包今天的支付额", notes = "获取总包+分包今天的支付额")
    public ReturnJson totalDayInfo(@RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return homePageService.getTodayById(merchantId);
    }

    @PostMapping("/totalWeekInfo")
    @LoginRequired
    @ApiOperation(value = "获取总包+分包本周的支付额", notes = "获取总包+分包本周的支付额")
    public ReturnJson totalWeekInfo(@RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return homePageService.getWeekTradeById(merchantId);
    }

    @PostMapping("/totalMonthInfo")
    @LoginRequired
    @ApiOperation(value = "获取总包+分包本月的支付额", notes = "获取总包+分包本月的支付额")
    public ReturnJson totalMonthInfo(@RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return homePageService.getMonthTradeById(merchantId);
    }

    @PostMapping("/totalYearInfo")
    @LoginRequired
    @ApiOperation(value = "获取总包+分包全年的支付额", notes = "获取总包+分包全年的支付额")
    public ReturnJson totalYearInfo(@RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return homePageService.getYearTradeById(merchantId);
    }

    @PostMapping("/manyDayInfo")
    @LoginRequired
    @ApiOperation(value = "获取众包今天的支付额", notes = "获取众包今天的支付额")
    public ReturnJson manyDayInfo(@RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return paymentOrderManyService.getDay(merchantId);
    }

    @PostMapping("/manyWeekInfo")
    @LoginRequired
    @ApiOperation(value = "获取众包本周的支付额", notes = "获取众包本周的支付额")
    public ReturnJson manyWeekInfo(@RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return paymentOrderManyService.getWeek(merchantId);
    }

    @PostMapping("/manyMonthInfo")
    @LoginRequired
    @ApiOperation(value = "获取众包本月的支付额", notes = "获取众包本月的支付额")
    public ReturnJson manyMonthInfo(@RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return paymentOrderManyService.getMonth(merchantId);
    }

    @PostMapping("/manyYearInfo")
    @LoginRequired
    @ApiOperation(value = "获取众包全年的支付额", notes = "获取众包全年的支付额")
    public ReturnJson manyYearInfo(@RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return paymentOrderManyService.getYear(merchantId);
    }

}
