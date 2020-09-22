package com.example.merchant.controller.platform;


import com.example.common.util.ReturnJson;
import com.example.merchant.dto.TaxDto;
import com.example.merchant.service.TaxService;
import com.example.mybatis.entity.Industry;
import com.example.mybatis.entity.InvoiceCatalog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/platform/tax")
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

    @GetMapping("/getCatalogAll")
    @ApiOperation(value = "获取服务商的开票类目", notes = "获取服务商的开票类目", httpMethod = "GET")
    public ReturnJson getCatalogAll(){
        return taxService.getCatalogAll();
    }

    @RequestMapping("/saveCatalog")
    @ApiOperation(value = "添加开票类目", notes = "添加开票类目", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="invoiceCatalog",value = "开票类目的信息",required = true, dataType = "InvoiceCatalog")})
    public ReturnJson saveCatalog(@RequestBody InvoiceCatalog invoiceCatalog){
        return taxService.saveCatalog(invoiceCatalog);
    }

    @RequestMapping("/saveTax")
    @ApiOperation(value = "添加平台服务商", notes = "添加平台服务商", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="taxDto",value = "平台服务商的信息",required = true,dataType = "TaxDto")})
    public ReturnJson saveTax(@RequestBody TaxDto taxDto){
        return taxService.saveTax(taxDto);
    }

    @RequestMapping("/test")
    @ApiOperation(value = "test", notes = "test", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="industry",value = "平台服务商的信息",required = true,dataType = "Industry")})
    public ReturnJson test(@RequestBody Industry industry){
        int i = 10/0;
        return taxService.test(industry);
    }
}
