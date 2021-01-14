package com.example.merchant.controller.merchant;


import com.example.common.util.ReturnJson;
import com.example.merchant.dto.TaxListDTO;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.TaxService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 合作园区信息 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "商户端平台服务商", tags = "商户端平台服务商")
@RestController
@RequestMapping("/merchant/tax")
@Validated
public class TaxMerchantController {

    @Resource
    private TaxService taxService;

    @GetMapping("/getTaxAll")
    @LoginRequired
    @ApiOperation(value = "获取商户可用的平台服务商", notes = "获取商户可用的平台服务商")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "packageStatus", value = "合作类型0为总包，1为众包", required = true)
    })
    public ReturnJson getTaxAll(@ApiParam(hidden = true) @RequestAttribute(value = "userId") String merchantId, @NotNull(message = "包的类型不能为空，0为总包，1为众包") @RequestParam(required = false) Integer packageStatus) {
        return taxService.getTaxAll(merchantId, packageStatus);
    }

    @PostMapping("/queryTaxAll")
    @LoginRequired
    @ApiOperation(value = "获取商户可用的平台服务商", notes = "获取商户可用的平台服务商")
    public ReturnJson queryTaxAll(@ApiParam(hidden = true) @RequestAttribute(value = "userId") String merchantId, @Valid @RequestBody TaxListDTO taxListDto) {
        return taxService.getTaxList(taxListDto, merchantId);
    }
}
