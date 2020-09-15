package com.example.paas.controller;

import com.example.common.util.ReturnJson;
import com.example.paas.service.HomePageService;
import com.example.paas.service.PaymentOrderManyService;
import com.example.paas.service.PaymentOrderService;
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
@RequestMapping("/merchant/homePage")
@Validated
@Api(value = "首页内容的展示", tags = "首页内容的展示")
public class HomePageController {

    @Autowired
    private HomePageService homePageService;

    @Autowired
    private PaymentOrderService paymentOrderService;

    @Autowired
    private PaymentOrderManyService paymentOrderManyService;

    /**
     * 获取首页的基本信息
      * @param merchantId
     * @return
     */
    @PostMapping("/merchant/homePageInfo")
    @ApiOperation(value = "获取首页基本信息", notes = "获取首页基本信息", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="merchantId",value = "用户ID",required = true)})
    public ReturnJson myWorker(@NotBlank(message = "用户不能为空") @RequestParam(required = false) String merchantId){
        return homePageService.getHomePageInof(merchantId);
    }

    /**
     * 总包+分包今天的支付额
     * @param merchantId
     * @return
     */
    @PostMapping("/totalDayInfo")
    @ApiOperation(value = "获取总包+分包今天的支付额", notes = "获取总包+分包今天的支付额", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="merchantId",value = "用户ID",required = true)})
    public ReturnJson totalDayInfo(@NotBlank(message = "用户Id不能为空") @RequestParam(required = false) String merchantId){
        return paymentOrderService.getDay(merchantId);
    }

    /**
     * 总包+分包本周的支付额
     * @param merchantId
     * @return
     */
    @PostMapping("/totalWeekInfo")
    @ApiOperation(value = "获取总包+分包本周的支付额", notes = "获取总包+分包本周的支付额", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="merchantId",value = "用户ID",required = true)})
    public ReturnJson totalWeekInfo(@NotBlank(message = "用户Id不能为空") @RequestParam(required = false) String merchantId){
        System.out.println(merchantId);
        return paymentOrderService.getWeek(merchantId);
    }

    /**
     * 总包+分包本月的支付额
     * @param merchantId
     * @return
     */
    @PostMapping("/totalMonthInfo")
    @ApiOperation(value = "获取总包+分包本月的支付额", notes = "获取总包+分包本月的支付额", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="merchantId",value = "用户ID",required = true)})
    public ReturnJson totalMonthInfo(@NotBlank(message = "用户Id不能为空") @RequestParam(required = false) String merchantId){
        return paymentOrderService.getMonth(merchantId);
    }

    /**
     * 总包+分包今年的支付额
     * @param merchantId
     * @return
     */
    @PostMapping("/totalYearInfo")
    @ApiOperation(value = "获取总包+分包今年的支付额", notes = "获取总包+分包今年的支付额", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="merchantId",value = "用户ID",required = true)})
    public ReturnJson totalYearInfo(@NotBlank(message = "用户Id不能为空") @RequestParam(required = false) String merchantId){
        return paymentOrderService.getYear(merchantId);
    }

    /**
     * 众包今天的支付额
     * @param merchantId
     * @return
     */
    @PostMapping("/manyDayInfo")
    @ApiOperation(value = "获取众包今天的支付额", notes = "获取众包今天的支付额", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="merchantId",value = "用户ID",required = true)})
    public ReturnJson manyDayInfo(@NotBlank(message = "用户Id不能为空") @RequestParam(required = false) String merchantId){
        return paymentOrderManyService.getDay(merchantId);
    }

    /**
     * 众包本周的支付额
     * @param merchantId
     * @return
     */
    @PostMapping("/manyWeekInfo")
    @ApiOperation(value = "获取众包本周的支付额", notes = "获取众包本周的支付额", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="merchantId",value = "用户ID",required = true)})
    public ReturnJson manyWeekInfo(@NotBlank(message = "用户Id不能为空") @RequestParam(required = false) String merchantId){
        return paymentOrderManyService.getWeek(merchantId);
    }

    /**
     * 众包本月的支付额
     * @param merchantId
     * @return
     */
    @PostMapping("/manyMonthInfo")
    @ApiOperation(value = "获取众包本月的支付额", notes = "获取众包本月的支付额", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="merchantId",value = "用户ID",required = true)})
    public ReturnJson manyMonthInfo(@NotBlank(message = "用户Id不能为空") @RequestParam(required = false) String merchantId){
        return paymentOrderManyService.getMonth(merchantId);
    }

    /**
     * 众包今年的支付额
     * @param merchantId
     * @return
     */
    @PostMapping("/manyYearInfo")
    @ApiOperation(value = "获取众包今年的支付额", notes = "获取众包今年的支付额", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="merchantId",value = "用户ID",required = true)})
    public ReturnJson manyYearInfo(@NotBlank(message = "用户Id不能为空") @RequestParam(required = false) String merchantId){
        return paymentOrderManyService.getYear(merchantId);
    }
}
