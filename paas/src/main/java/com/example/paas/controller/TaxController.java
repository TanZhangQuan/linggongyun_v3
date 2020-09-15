package com.example.paas.controller;


import com.example.common.util.ReturnJson;
import com.example.paas.service.TaxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 合作园区信息 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/paas/tax")
@Validated
@Api(value = "平台服务商", tags = "平台服务商")
public class TaxController {

    @Autowired
    private TaxService taxService;

    @GetMapping("/getTaxAll")
    @ApiOperation(value = "获取商户可用的平台服务商", notes = "获取商户可用的平台服务商", httpMethod = "GET")
    @ApiImplicitParams(value={@ApiImplicitParam(name="merchantId",value = "商户ID",required = true)})
    public ReturnJson getTaxAll(@NotBlank(message = "商户ID不能为空") @RequestParam(required = false) String merchantId){
        return taxService.getTaxAll(merchantId);
    }
}
