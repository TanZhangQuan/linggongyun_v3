package com.example.merchant.controller;

import com.example.merchant.service.NotifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Api(value = "异步回调相关接口", tags = "异步回调相关接口")
@RestController
@RequestMapping("/notice")
public class NotifyController {

    @Resource
    private NotifyService notifyService;

    @PostMapping("/unionpay/depositNotice")
    @ApiOperation(value = "银联入金回调接收", notes = "银联入金回调接收")
    public String depositNotice(HttpServletRequest request) throws Exception {
        return notifyService.depositNotice(request);
    }

    @PostMapping("/unionpay/txResult")
    @ApiOperation(value = "银联提现到卡回调接收", notes = "银联提现到卡回调接收")
    public String txResult(HttpServletRequest request) throws Exception {
        return notifyService.txResult(request);
    }

    @PostMapping("/unionpay/transferResult")
    @ApiOperation(value = "银联内部转账回调接收(清分接口，会员间交易)", notes = "银联内部转账回调接收(清分接口，会员间交易)")
    public String transferResult(HttpServletRequest request) throws Exception {
        return notifyService.transferResult(request);
    }

}
