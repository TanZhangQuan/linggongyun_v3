package com.example.merchant.controller.paas;

import com.example.common.util.ReturnJson;
import com.example.merchant.service.HomePageService;
import com.example.merchant.service.PaymentOrderManyService;
import com.example.merchant.service.PaymentOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/paas/homePage")
@Validated
@Api(value = "平台端首页内容的展示", tags = "平台端首页内容的展示")
public class HomePageControllerPaas {

    @Autowired
    private HomePageService homePageService;

    @Autowired
    private PaymentOrderService paymentOrderService;

    @Autowired
    private PaymentOrderManyService paymentOrderManyService;

    /**
     * 获取首页的基本信息
     *
     * @param managersId
     * @return
     */
    @PostMapping("/homePageInfo")
    @ApiOperation(value = "获取首页基本信息", notes = "获取首页基本信息", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "managersId", value = "用户ID", required = true)})
    public ReturnJson myWorker(@NotBlank(message = "用户不能为空") @RequestParam(required = false) String managersId) {
        return homePageService.getHomePageInofpaas(managersId);
    }

    /**
     * 总包+分包今天的支付额
     *
     * @param merchantId
     * @return
     */
    @PostMapping("/totalDayInfo")
    @ApiOperation(value = "获取总包+分包今天的支付额", notes = "获取总包+分包今天的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "merchantId", value = "用户ID", required = true)})
    public ReturnJson totalDayInfo(@NotBlank(message = "用户Id不能为空") @RequestParam(required = false) String merchantId) {
        return paymentOrderService.getDayPaas(merchantId);
    }

    /**
     * 总包+分包本周的支付额
     *
     * @param merchantId
     * @return
     */
    @PostMapping("/totalWeekInfo")
    @ApiOperation(value = "获取总包+分包本周的支付额", notes = "获取总包+分包本周的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "merchantId", value = "用户ID", required = true)})
    public ReturnJson totalWeekInfo(@NotBlank(message = "用户Id不能为空") @RequestParam(required = false) String merchantId) {
        System.out.println(merchantId);
        return paymentOrderService.getWeekPaas(merchantId);
    }

    /**
     * 总包+分包本月的支付额
     *
     * @param merchantId
     * @return
     */
    @PostMapping("/totalMonthInfo")
    @ApiOperation(value = "获取总包+分包本月的支付额", notes = "获取总包+分包本月的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "merchantId", value = "用户ID", required = true)})
    public ReturnJson totalMonthInfo(@NotBlank(message = "用户Id不能为空") @RequestParam(required = false) String merchantId) {
        return paymentOrderService.getMonthPaas(merchantId);
    }

    /**
     * 总包+分包今年的支付额
     *
     * @param merchantId
     * @return
     */
    @PostMapping("/totalYearInfo")
    @ApiOperation(value = "获取总包+分包今年的支付额", notes = "获取总包+分包今年的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "merchantId", value = "用户ID", required = true)})
    public ReturnJson totalYearInfo(@NotBlank(message = "用户Id不能为空") @RequestParam(required = false) String merchantId) {
        return paymentOrderService.getYearPaas(merchantId);
    }

    /**
     * 众包今天的支付额
     *
     * @param merchantId
     * @return
     */
    @PostMapping("/manyDayInfo")
    @ApiOperation(value = "获取众包今天的支付额", notes = "获取众包今天的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "merchantId", value = "用户ID", required = true)})
    public ReturnJson manyDayInfo(@NotBlank(message = "用户Id不能为空") @RequestParam(required = false) String merchantId) {
        return paymentOrderManyService.getDayPaas(merchantId);
    }

    /**
     * 众包本周的支付额
     *
     * @param merchantId
     * @return
     */
    @PostMapping("/manyWeekInfo")
    @ApiOperation(value = "获取众包本周的支付额", notes = "获取众包本周的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "merchantId", value = "用户ID", required = true)})
    public ReturnJson manyWeekInfo(@NotBlank(message = "用户Id不能为空") @RequestParam(required = false) String merchantId) {
        return paymentOrderManyService.getWeekPaas(merchantId);
    }

    /**
     * 众包本月的支付额
     *
     * @param merchantId
     * @return
     */
    @PostMapping("/manyMonthInfo")
    @ApiOperation(value = "获取众包本月的支付额", notes = "获取众包本月的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "merchantId", value = "用户ID", required = true)})
    public ReturnJson manyMonthInfo(@NotBlank(message = "用户Id不能为空") @RequestParam(required = false) String merchantId) {
        return paymentOrderManyService.getMonthPaas(merchantId);
    }

    /**
     * 众包今年的支付额
     *
     * @param merchantId
     * @return
     */
    @PostMapping("/manyYearInfo")
    @ApiOperation(value = "获取众包今年的支付额", notes = "获取众包今年的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "merchantId", value = "用户ID", required = true)})
    public ReturnJson manyYearInfo(@NotBlank(message = "用户Id不能为空") @RequestParam(required = false) String merchantId) {
        return paymentOrderManyService.getYearPaas(merchantId);
    }
}
