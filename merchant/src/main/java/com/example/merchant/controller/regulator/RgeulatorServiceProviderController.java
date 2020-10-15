package com.example.merchant.controller.regulator;

import com.example.common.util.ReturnJson;
import com.example.merchant.service.RegulatorTaxService;
import com.example.mybatis.dto.RegulatorTaxDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Api(value = "监管部门中心的服务商监管", tags = "监管部门中心的服务商监管")
@RestController
@RequestMapping("/regulator/rgeulatorServiceProvider")
@Validated
public class RgeulatorServiceProviderController {

    @Autowired
    private RegulatorTaxService regulatorTaxService;

    @PostMapping("getListServiceProvider")
    @ApiOperation(value = "查询服务商列表", notes = "查询服务商列表", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "regulatorTaxDto", value = "监管服务商查询", dataType = "RegulatorTaxDto", required = true)})
    public ReturnJson getListServiceProvider(@Valid @RequestBody RegulatorTaxDto regulatorTaxDto) {
        return regulatorTaxService.listTax(regulatorTaxDto);
    }

    @PostMapping("getServiceProvider")
    @ApiOperation(value = "查询服务商信息", notes = "查询服务商信息", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taxId", value = "查询服务商信息", required = true)})
    public ReturnJson getListServiceProvider(@NotBlank(message = "taxId不能为空") @RequestParam(required = false) String taxId) {
        return regulatorTaxService.getTax(taxId);
    }


}
