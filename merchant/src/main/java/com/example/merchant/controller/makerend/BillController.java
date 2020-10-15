package com.example.merchant.controller.makerend;

import com.example.common.util.ReturnJson;
import com.example.merchant.service.BillService;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Api(value = "小程序账单", tags = "小程序账单")
@RestController
@RequestMapping("/makerend/bill")
@Validated
public class BillController {

    @Resource
    BillService billService;

    @PostMapping("/getTotalMonthBill")
    @ApiOperation(value = "查询总包月账单", notes = "查询总包月账单", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "year", value = "年份（如2020）", paramType = "query",required = true),
            @ApiImplicitParam(name = "month", value = "月份（1-12月）", paramType = "query",required = true)
    })
    public ReturnJson getTotalMonthBill(@NotNull @RequestParam Integer year, @Min (1) @Max (12)@RequestParam Integer month){
        return billService.getTotalMonthBill(year,month);
    }

    @PostMapping("/getTotalMonthBillInfo")
    @ApiOperation(value = "查询总包月账单明细", notes = "查询总包月账单明细", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "year", value = "年份（如2020）", paramType = "query",required = true),
            @ApiImplicitParam(name = "month", value = "月份（1-12月）", paramType = "query",required = true)
    })
    public ReturnJson getTotalMonthBillInfo(@NotNull @RequestParam Integer year, @Min (1) @Max (12)@RequestParam Integer month){
        return billService.getTotalMonthBillInfo(year,month);
    }

    @PostMapping("/getManyMonthBill")
    @ApiOperation(value = "查询众包月账单", notes = "查询众包月账单", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "year", value = "年份（如2020）", paramType = "query",required = true),
            @ApiImplicitParam(name = "month", value = "月份（1-12月）", paramType = "query",required = true)
    })
    public ReturnJson getManyMonthBill(@NotNull @RequestParam Integer year, @Min (1) @Max (12)@RequestParam Integer month){
        return billService.getManyMonthBill(year,month);
    }

    @PostMapping("/getManyMonthBillInfo")
    @ApiOperation(value = "查询众包月账单明细", notes = "查询众包月账单明细", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "year", value = "年份（如2020）", paramType = "query",required = true),
            @ApiImplicitParam(name = "month", value = "月份（1-12月）", paramType = "query",required = true)
    })
    public ReturnJson getManyMonthBillInfo(@NotNull @RequestParam Integer year, @Min (1) @Max (12)@RequestParam Integer month){
        return billService.getManyMonthBillInfo(year,month);
    }

    @PostMapping("/getTotalYearBillCount")
    @ApiOperation(value = "查询总包年账单统计", notes = "查询总包年账单统计", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "year", value = "年份（如2020）", paramType = "query",required = true)
    })
    public ReturnJson getTotalYearBillCount(@NotNull @RequestParam Integer year){
        return billService.getTotalYearBillCount(year);
    }

    @PostMapping("/getManyYearBillCount")
    @ApiOperation(value = "查询众包年账单统计", notes = "查询众包年账单统计", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "year", value = "年份（如2020）", paramType = "query",required = true)
    })
    public ReturnJson getManyYearBillCount(@NotNull @RequestParam Integer year){
        return billService.getManyYearBillCount(year);
    }
}
