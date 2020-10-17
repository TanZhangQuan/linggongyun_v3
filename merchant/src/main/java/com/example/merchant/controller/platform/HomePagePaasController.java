package com.example.merchant.controller.platform;

import com.example.common.util.ReturnJson;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.HomePageService;
import com.example.merchant.service.PaymentOrderManyService;
import com.example.merchant.service.PaymentOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

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
    public ReturnJson myWorker(HttpServletRequest request) throws CommonException {
        return homePageService.getHomePageInofpaas(request);
    }

    @PostMapping("/totalDayInfo")
    @ApiOperation(value = "获取总包+分包今天的支付额", notes = "获取总包+分包今天的支付额", httpMethod = "POST")
    public ReturnJson totalDayInfo(HttpServletRequest request) throws CommonException {
        return paymentOrderService.getDayPaas(request);
    }

    @PostMapping("/totalWeekInfo")
    @ApiOperation(value = "获取总包+分包本周的支付额", notes = "获取总包+分包本周的支付额", httpMethod = "POST")
    public ReturnJson totalWeekInfo(HttpServletRequest request) throws CommonException {
        return paymentOrderService.getWeekPaas(request);
    }

    @PostMapping("/totalMonthInfo")
    @ApiOperation(value = "获取总包+分包本月的支付额", notes = "获取总包+分包本月的支付额", httpMethod = "POST")
    public ReturnJson totalMonthInfo(HttpServletRequest request) throws CommonException {
        return paymentOrderService.getMonthPaas(request);
    }

    @PostMapping("/totalYearInfo")
    @ApiOperation(value = "获取总包+分包全年的支付额", notes = "获取总包+分包全年的支付额", httpMethod = "POST")
    public ReturnJson totalYearInfo(HttpServletRequest request) throws CommonException {
        return paymentOrderService.getYearPaas(request);
    }

    @PostMapping("/manyDayInfo")
    @ApiOperation(value = "获取众包今天的支付额", notes = "获取众包今天的支付额", httpMethod = "POST")
    public ReturnJson manyDayInfo(HttpServletRequest request) throws CommonException {
        return paymentOrderManyService.getDayPaas(request);
    }

    @PostMapping("/manyWeekInfo")
    @ApiOperation(value = "获取众包本周的支付额", notes = "获取众包本周的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "merchantId", value = "用户ID", required = true)
    })
    public ReturnJson manyWeekInfo(HttpServletRequest request) throws CommonException {
        return paymentOrderManyService.getWeekPaas(request);
    }

    @PostMapping("/manyMonthInfo")
    @ApiOperation(value = "获取众包本月的支付额", notes = "获取众包本月的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "merchantId", value = "用户ID", required = true)
    })
    public ReturnJson manyMonthInfo(HttpServletRequest request) throws CommonException {
        return paymentOrderManyService.getMonthPaas(request);
    }

    @PostMapping("/manyYearInfo")
    @ApiOperation(value = "获取众包全年的支付额", notes = "获取众包全年的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "merchantId", value = "用户ID", required = true)
    })
    public ReturnJson manyYearInfo(HttpServletRequest request) throws CommonException {
        return paymentOrderManyService.getYearPaas(request);
    }
}
