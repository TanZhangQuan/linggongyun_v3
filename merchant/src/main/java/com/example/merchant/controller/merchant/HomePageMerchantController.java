package com.example.merchant.controller.merchant;

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
@RequestMapping("/merchant/homePage")
@Validated
@Api(value = "商户端首页内容的展示", tags = "商户端首页内容的展示")
public class HomePageMerchantController {

    @Autowired
    private HomePageService homePageService;

    @Autowired
    private PaymentOrderService paymentOrderService;

    @Autowired
    private PaymentOrderManyService paymentOrderManyService;


    /**
     * 获取首页的基本信息
     *
     * @param companyId
     * @return
     */
    @PostMapping("/homePageInfo")
    @ApiOperation(value = "获取首页基本信息", notes = "获取首页基本信息", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "companyId", value = "商户公司ID", required = true)})
    public ReturnJson myWorker(@NotBlank(message = "商户公司ID不能为空") @RequestParam(required = false) String companyId) {
        return homePageService.getHomePageInof(companyId);
    }

    /**
     * 总包+分包今天的支付额
     *
     * @param companyId
     * @return
     */
    @PostMapping("/totalDayInfo")
    @ApiOperation(value = "获取总包+分包今天的支付额", notes = "获取总包+分包今天的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "companyId", value = "商户的公司ID")})
    public ReturnJson totalDayInfo(@NotBlank(message = "商户的公司ID不能为空") @RequestParam String companyId) {
        return paymentOrderService.getDay(companyId);
    }

    /**
     * 总包+分包本周的支付额
     *
     * @param companyId
     * @return
     */
    @PostMapping("/totalWeekInfo")
    @ApiOperation(value = "获取总包+分包本周的支付额", notes = "获取总包+分包本周的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "companyId", value = "商户的公司ID", required = true)})
    public ReturnJson totalWeekInfo(@NotBlank(message = "商户的公司ID不能为空") @RequestParam(required = false) String companyId) {
        return paymentOrderService.getWeek(companyId);
    }

    /**
     * 总包+分包本月的支付额
     *
     * @param companyId
     * @return
     */
    @PostMapping("/totalMonthInfo")
    @ApiOperation(value = "获取总包+分包本月的支付额", notes = "获取总包+分包本月的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "companyId", value = "商户的公司ID", required = true)})
    public ReturnJson totalMonthInfo(@NotBlank(message = "商户的公司ID不能为空") @RequestParam String companyId) {
        return paymentOrderService.getMonth(companyId);
    }

    /**
     * 总包+分包今年的支付额
     *
     * @param companyId
     * @return
     */
    @PostMapping("/totalYearInfo")
    @ApiOperation(value = "获取总包+分包全年的支付额", notes = "获取总包+分包全年的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "companyId", value = "商户的公司ID", required = true)})
    public ReturnJson totalYearInfo(@NotBlank(message = "商户的公司ID不能为空") @RequestParam(required = false) String companyId) {
        return paymentOrderService.getYear(companyId);
    }

    /**
     * 众包今天的支付额
     *
     * @param companyId
     * @return
     */
    @PostMapping("/manyDayInfo")
    @ApiOperation(value = "获取众包今天的支付额", notes = "获取众包今天的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "companyId", value = "商户的公司ID", required = true)})
    public ReturnJson manyDayInfo(@NotBlank(message = "商户的公司ID不能为空") @RequestParam(required = false) String companyId) {
        return paymentOrderManyService.getDay(companyId);
    }

    /**
     * 众包本周的支付额
     *
     * @param companyId
     * @return
     */
    @PostMapping("/manyWeekInfo")
    @ApiOperation(value = "获取众包本周的支付额", notes = "获取众包本周的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "companyId", value = "商户的公司ID", required = true)})
    public ReturnJson manyWeekInfo(@NotBlank(message = "商户的公司ID不能为空") @RequestParam(required = false) String companyId) {
        return paymentOrderManyService.getWeek(companyId);
    }

    /**
     * 众包本月的支付额
     *
     * @param companyId
     * @return
     */
    @PostMapping("/manyMonthInfo")
    @ApiOperation(value = "获取众包本月的支付额", notes = "获取众包本月的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "companyId", value = "商户的公司ID", required = true)})
    public ReturnJson manyMonthInfo(@NotBlank(message = "商户的公司ID不能为空") @RequestParam(required = false) String companyId) {
        return paymentOrderManyService.getMonth(companyId);
    }

    /**
     * 众包今年的支付额
     *
     * @param companyId
     * @return
     */
    @PostMapping("/manyYearInfo")
    @ApiOperation(value = "获取众包全年的支付额", notes = "获取众包全年的支付额", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "companyId", value = "商户的公司ID", required = true)})
    public ReturnJson manyYearInfo(@NotBlank(message = "商户的公司ID不能为空") @RequestParam(required = false) String companyId) {
        return paymentOrderManyService.getYear(companyId);
    }
}
