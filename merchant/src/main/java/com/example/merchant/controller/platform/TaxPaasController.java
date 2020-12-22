package com.example.merchant.controller.platform;


import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.AddInvoiceCatalogDto;
import com.example.merchant.dto.platform.TaxDto;
import com.example.merchant.dto.platform.TaxListDto;
import com.example.merchant.service.TaxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "平台端服务商管理", tags = "平台端服务商管理")
@RestController
@RequestMapping("/platform/tax")
@Validated
public class TaxPaasController {

    @Resource
    private TaxService taxService;

    @GetMapping("/getTaxAll")
    @ApiOperation(value = "获取商户可用的平台服务商(平台端帮助商户创建支付订单时，通过选择的商户获取商户的服务商)", notes = "获取商户可用的平台服务商(平台端帮助商户创建支付订单时，通过选择的商户获取商户的服务商)", httpMethod = "GET")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "companyId", value = "商户的企业ID", required = true), @ApiImplicitParam(name = "packageStatus", value = "合作类型不能为空，0为总包，1为众包（建立支付订单通过支付订单的类型自动获取,不是选择）", required = true)})
    public ReturnJson getTaxAll(@NotNull(message = "商户ID不能为空") @RequestParam(required = false) String companyId, @NotBlank(message = "合作类型不能为空，0为总包，1为众包") @RequestParam(required = false) Integer packageStatus) {
        return taxService.getTaxPaasAll(companyId, packageStatus);
    }

    @GetMapping("/getCatalogAll")
    @ApiOperation(value = "获取服务商的开票类目", notes = "获取服务商的开票类目", httpMethod = "GET")
    public ReturnJson getCatalogAll() {
        return taxService.getCatalogAll();
    }

    @PostMapping("/saveCatalog")
    @ApiOperation(value = "添加开票类目", notes = "添加开票类目", httpMethod = "POST")
    public ReturnJson saveCatalog(@Valid @RequestBody AddInvoiceCatalogDto addInvoiceCatalogDto) {
        return taxService.saveCatalog(addInvoiceCatalogDto);
    }

    @PostMapping("/saveTax")
    @ApiOperation(value = "添加或修改平台服务商", notes = "添加或修改平台服务商", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taxDto", value = "平台服务商的信息", required = true, dataType = "TaxDto")})
    public ReturnJson saveTax(@Valid @RequestBody TaxDto taxDto) throws Exception {
        return taxService.saveTax(taxDto);
    }

    @PostMapping("/getTaxList")
    @ApiOperation(value = "查询服务商列表", notes = "查询服务商列表", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taxListDto", value = "查询条件", dataType = "TaxListDto", paramType = "body")})
    public ReturnJson getTaxList(@Valid @RequestBody TaxListDto taxListDto) {
        return taxService.getTaxList(taxListDto);
    }

    @GetMapping("/getTaxInfo")
    @ApiOperation(value = "查询服务商详情", notes = "查询服务商详情", httpMethod = "GET")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taxId", value = "服务商ID")})
    public ReturnJson getTaxInfo(@NotBlank(message = "服务商ID不能为空！") @RequestParam String taxId) {
        return taxService.getTaxInfo(taxId);
    }

    @PostMapping("/transactionRecordCount")
    @ApiOperation(value = "查询服务商交易流水统计", notes = "查询服务商交易流水统计", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taxId", value = "服务商ID")})
    public ReturnJson transactionRecordCount(@NotBlank(message = "服务商ID不能为空！") @RequestParam String taxId) {
        return taxService.transactionRecordCount(taxId);
    }

    @PostMapping("/transactionRecord")
    @ApiOperation(value = "查询服务商交易流水", notes = "查询服务商交易流水", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taxId", value = "服务商ID"), @ApiImplicitParam(name = "pageNo", value = "页数"), @ApiImplicitParam(name = "pageSize", value = "一页的条数")})
    public ReturnJson transactionRecord(@NotBlank(message = "服务商ID不能为空！") @RequestParam String taxId, @RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        return taxService.transactionRecord(taxId, pageNo, pageSize);
    }

    @GetMapping("/queryTaxPaasList")
    @ApiOperation(value = "查询服务商列表（添加商户）", notes = "查询服务商列表（添加商户）", httpMethod = "GET")
    public ReturnJson getTaxPaasList() {
        return taxService.getTaxPaasList();
    }
}
