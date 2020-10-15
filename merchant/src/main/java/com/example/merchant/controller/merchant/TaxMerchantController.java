package com.example.merchant.controller.merchant;


import com.example.common.util.ReturnJson;
import com.example.merchant.service.TaxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
    @ApiOperation(value = "获取商户可用的平台服务商", notes = "获取商户可用的平台服务商", httpMethod = "GET")
    @ApiImplicitParams(value={
            @ApiImplicitParam(name="companyId",value = "商户的公司ID",required = true),
            @ApiImplicitParam(name="packageStatus",value = "合作类型0为总包，1为众包",required = true)
    })
    public ReturnJson getTaxAll(@NotBlank(message = "商户ID不能为空") @RequestParam(required = false) String companyId,  @NotNull(message = "包的类型不能为空，0为总包，1为众包") @RequestParam(required = false) Integer packageStatus){
        return taxService.getTaxAll(companyId,packageStatus);
    }
}
