package com.example.merchant.controller.paas;


import com.example.common.util.ReturnJson;
import com.example.merchant.service.TaxService;
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
@Api(value = "平台端服务商管理", tags = "平台端服务商管理")
public class TaxControllerPaas {

    @Autowired
    private TaxService taxService;

    @GetMapping("/getTaxAll")
    @ApiOperation(value = "获取商户可用的平台服务商", notes = "获取商户可用的平台服务商", httpMethod = "GET")
    @ApiImplicitParams(value={@ApiImplicitParam(name="merchantId",value = "商户ID",required = true)})
    public ReturnJson getTaxAll(@NotBlank(message = "商户ID不能为空") @RequestParam(required = false) String merchantId, @NotBlank(message = "包的类型不能为空，0为总包，1为众包") @RequestParam(required = false) Integer packageStatus){
        return taxService.getTaxAll(merchantId, packageStatus);
    }
}
