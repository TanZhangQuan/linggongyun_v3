package com.example.merchant.controller.regulator;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.regulator.RegulatorMerchantDto;
import com.example.merchant.service.RegulatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


@Api(value = "监管部门中心的商户监管", tags = "监管部门中心的商户监管")
@RestController
@RequestMapping("/regulator/regulatorMerchant")
@Validated
public class RegulatorMerchantController {
    @Resource
    private RegulatorService regulatorService;

    @PostMapping("/getCountRegulatorMerchant")
    @ApiOperation(value = "统计所监管的商户的流水", notes = "统计所监管的商户的流水", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "regulatorId", value = "监管部门的ID", required = true)})
    public ReturnJson getCountRegulatorMerchant(@NotBlank(message = "管理部门ID不能为空！") @RequestParam String regulatorId) {
        return regulatorService.getCountRegulatorMerchant(regulatorId);
    }

    @PostMapping("/getRegulatorMerchant")
    @ApiOperation(value = "按条件查询所监管的商户", notes = "按条件查询所监管的商户", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "regulatorMerchantDto", value = "查询所监管的商户条件", required = true, dataType = "RegulatorMerchantDto")})
    public ReturnJson getRegulatorMerchant(@Valid @RequestBody RegulatorMerchantDto regulatorMerchantDto) {
        return regulatorService.getRegulatorMerchant(regulatorMerchantDto);
    }

    @PostMapping("/exportRegulatorMerchant")
    @ApiOperation(value = "导出所监管的商户", notes = "导出所监管的商户", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "companyIds", value = "商户编号字符集，每个商户编号之间用英文逗号隔开", required = true),
            @ApiImplicitParam(name = "regulatorId", value = "管理部门ID", required = true)})
    public ReturnJson exportRegulatorMerchant(@NotBlank(message = "导出的商户不能为空！") @RequestParam String companyIds, @NotBlank(message = "管理部门ID不能为空！") @RequestParam String regulatorId, HttpServletResponse response) {
        ReturnJson returnJson = regulatorService.exportRegulatorMerchant(companyIds, regulatorId, response);
        if (returnJson.getCode() == 300) {
            return returnJson;
        }
        return null;
    }

    @PostMapping("/getRegulatorMerchantParticulars")
    @ApiOperation(value = "查询所监管的公司详情", notes = "查询所监管的公司详情", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "companyId", value = "公司ID", required = true),
            @ApiImplicitParam(name = "regulatorId", value = "管理部门ID", required = true)})
    public ReturnJson getRegulatorMerchantParticulars(@NotBlank(message = "公司ID不能为空！") @RequestParam String companyId, @NotBlank(message = "管理部门ID不能为空！") @RequestParam String regulatorId) {
        return regulatorService.getRegulatorMerchantParticulars(companyId, regulatorId);
    }
}
