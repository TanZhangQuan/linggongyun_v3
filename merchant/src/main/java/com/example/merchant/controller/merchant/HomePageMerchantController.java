package com.example.merchant.controller.merchant;

import com.example.common.util.ReturnJson;
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
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "companyId", value = "商户公司ID", required = true)
    })
    public ReturnJson myWorker(@NotBlank(message = "商户公司ID不能为空") @RequestParam(required = false) String companyId) {
        return homePageService.getHomePageInof(companyId);
    }

    @PostMapping("/totalDayInfo")
    @ApiOperation(value = "获取总包+分包今天的支付额", notes = "获取总包+分包今天的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "companyId", value = "商户的公司ID")
    })
    public ReturnJson totalDayInfo(@NotBlank(message = "商户的公司ID不能为空") @RequestParam String companyId) {
        return paymentOrderService.getDay(companyId);
    }

    @PostMapping("/totalWeekInfo")
    @ApiOperation(value = "获取总包+分包本周的支付额", notes = "获取总包+分包本周的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "companyId", value = "商户的公司ID", required = true)
    })
    public ReturnJson totalWeekInfo(@NotBlank(message = "商户的公司ID不能为空") @RequestParam(required = false) String companyId) {
        return paymentOrderService.getWeek(companyId);
    }

    @PostMapping("/totalMonthInfo")
    @ApiOperation(value = "获取总包+分包本月的支付额", notes = "获取总包+分包本月的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "companyId", value = "商户的公司ID", required = true)
    })
    public ReturnJson totalMonthInfo(@NotBlank(message = "商户的公司ID不能为空") @RequestParam String companyId) {
        return paymentOrderService.getMonth(companyId);
    }

    @PostMapping("/totalYearInfo")
    @ApiOperation(value = "获取总包+分包全年的支付额", notes = "获取总包+分包全年的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "companyId", value = "商户的公司ID", required = true)
    })
    public ReturnJson totalYearInfo(@NotBlank(message = "商户的公司ID不能为空") @RequestParam(required = false) String companyId) {
        return paymentOrderService.getYear(companyId);
    }

    @PostMapping("/manyDayInfo")
    @ApiOperation(value = "获取众包今天的支付额", notes = "获取众包今天的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "companyId", value = "商户的公司ID", required = true)
    })
    public ReturnJson manyDayInfo(@NotBlank(message = "商户的公司ID不能为空") @RequestParam(required = false) String companyId) {
        return paymentOrderManyService.getDay(companyId);
    }

    @PostMapping("/manyWeekInfo")
    @ApiOperation(value = "获取众包本周的支付额", notes = "获取众包本周的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "companyId", value = "商户的公司ID", required = true)
    })
    public ReturnJson manyWeekInfo(@NotBlank(message = "商户的公司ID不能为空") @RequestParam(required = false) String companyId) {
        return paymentOrderManyService.getWeek(companyId);
    }

    @PostMapping("/manyMonthInfo")
    @ApiOperation(value = "获取众包本月的支付额", notes = "获取众包本月的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "companyId", value = "商户的公司ID", required = true)
    })
    public ReturnJson manyMonthInfo(@NotBlank(message = "商户的公司ID不能为空") @RequestParam(required = false) String companyId) {
        return paymentOrderManyService.getMonth(companyId);
    }

    @PostMapping("/manyYearInfo")
    @ApiOperation(value = "获取众包全年的支付额", notes = "获取众包全年的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "companyId", value = "商户的公司ID", required = true)})
    public ReturnJson manyYearInfo(@NotBlank(message = "商户的公司ID不能为空") @RequestParam(required = false) String companyId) {
        return paymentOrderManyService.getYear(companyId);
    }
}
