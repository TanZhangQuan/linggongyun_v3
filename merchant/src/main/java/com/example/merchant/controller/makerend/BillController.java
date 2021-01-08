package com.example.merchant.controller.makerend;

import com.example.common.util.ReturnJson;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.BillService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Api(value = "小程序账单", tags = "小程序账单")
@RestController
@RequestMapping("/makerend/bill")
@Validated
public class BillController {

    @Resource
    private BillService billService;

    @LoginRequired
    @PostMapping("/getTotalMonthBill")
    @ApiOperation(value = "查询总包月账单", notes = "查询总包月账单", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "year", value = "年份（如2020）", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "月份（1-12月）", paramType = "query", required = true)
    })
    public ReturnJson getTotalMonthBill(@ApiParam(hidden = true) @RequestAttribute(value = "userId", required = false) @NotBlank(message = "您的登录以过期，请重新登录") String workerId, @NotNull @RequestParam(required = false) Integer year, @Min(value = 1, message = "月数不能小于一月") @Max(value = 12, message = "月数不能大于12月") @RequestParam(required = false) Integer month) {
        return billService.getTotalMonthBill(workerId, year, month);
    }

    @LoginRequired
    @PostMapping("/getTotalMonthBillInfo")
    @ApiOperation(value = "查询总包月账单明细", notes = "查询总包月账单明细", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "year", value = "年份（如2020）", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "月份（1-12月）", paramType = "query", required = true)
    })
    public ReturnJson getTotalMonthBillInfo(@ApiParam(hidden = true) @RequestAttribute(value = "userId", required = false) @NotBlank(message = "您的登录以过期，请重新登录") String workerId, @NotNull @RequestParam(required = false) Integer year, @Min(value = 1, message = "月数不能小于一月") @Max(value = 12, message = "月数不能大于12月") @RequestParam(required = false) Integer month) {
        return billService.getTotalMonthBillInfo(workerId, year, month);
    }

    @LoginRequired
    @PostMapping("/getManyMonthBill")
    @ApiOperation(value = "查询众包月账单", notes = "查询众包月账单", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "year", value = "年份（如2020）", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "月份（1-12月）", paramType = "query", required = true)
    })
    public ReturnJson getManyMonthBill(@ApiParam(hidden = true) @RequestAttribute(value = "userId", required = false) @NotBlank(message = "您的登录以过期，请重新登录") String workerId, @NotNull @RequestParam(required = false) Integer year, @Min(value = 1, message = "月数不能小于一月") @Max(value = 12, message = "月数不能大于12月") @RequestParam(required = false) Integer month) {
        return billService.getManyMonthBill(workerId, year, month);
    }

    @LoginRequired
    @PostMapping("/getManyMonthBillInfo")
    @ApiOperation(value = "查询众包月账单明细", notes = "查询众包月账单明细", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "year", value = "年份（如2020）", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "月份（1-12月）", paramType = "query", required = true)
    })
    public ReturnJson getManyMonthBillInfo(@ApiParam(hidden = true) @RequestAttribute(value = "userId", required = false) @NotBlank(message = "您的登录以过期，请重新登录") String workerId, @NotNull @RequestParam(required = false) Integer year, @Min(value = 1, message = "月数不能小于一月") @Max(value = 12, message = "月数不能大于12月") @RequestParam(required = false) Integer month) {
        return billService.getManyMonthBillInfo(workerId, year, month);
    }

    @LoginRequired
    @PostMapping("/getTotalYearBillCount")
    @ApiOperation(value = "查询总包年账单统计", notes = "查询总包年账单统计", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "year", value = "年份（如2020）", paramType = "query", required = true)
    })
    public ReturnJson getTotalYearBillCount(@ApiParam(hidden = true) @RequestAttribute(value = "userId", required = false) @NotBlank(message = "您的登录以过期，请重新登录") String workerId, @NotNull @RequestParam(required = false) Integer year) {
        return billService.getTotalYearBillCount(workerId, year);
    }

    @LoginRequired
    @PostMapping("/getManyYearBillCount")
    @ApiOperation(value = "查询众包年账单统计", notes = "查询众包年账单统计", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "year", value = "年份（如2020）", paramType = "query", required = true)
    })
    public ReturnJson getManyYearBillCount(@ApiParam(hidden = true) @RequestAttribute(value = "userId", required = false) @NotBlank(message = "您的登录以过期，请重新登录") String workerId, @NotNull @RequestParam(required = false) Integer year) {
        return billService.getManyYearBillCount(workerId, year);
    }

    @LoginRequired
    @PostMapping("/queryBillInfo")
    @ApiOperation(value = "查询账单统计详情", notes = "查询账单统计详情", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "ID", required = true),
            @ApiImplicitParam(name = "isNot", value = "0总包1众包，必填", required = true)
    })
    public ReturnJson queryBillInfo(@ApiParam(hidden = true) @RequestAttribute(value = "userId", required = false) @NotBlank(message = "您的登录以过期，请重新登录") String workerId, @NotBlank @RequestParam(required = false) String id, @NotNull(message = "0总包1分包，必填") @RequestParam Integer isNot) {
        return billService.queryBillInfo(workerId, id, isNot);
    }

}
