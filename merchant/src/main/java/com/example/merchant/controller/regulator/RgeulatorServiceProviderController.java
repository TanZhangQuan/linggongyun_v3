package com.example.merchant.controller.regulator;

import com.example.common.enums.UnionpayBankType;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.regulator.PayInfoDTO;
import com.example.merchant.exception.CommonException;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.CompanyUnionpayService;
import com.example.merchant.service.RegulatorTaxService;
import com.example.mybatis.dto.RegulatorTaxDTO;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Api(value = "监管部门中心的服务商监管", tags = "监管部门中心的服务商监管")
@RestController
@RequestMapping("/regulator/rgeulatorServiceProvider")
@Validated
public class RgeulatorServiceProviderController {

    @Resource
    private RegulatorTaxService regulatorTaxService;

    @Resource
    private CompanyUnionpayService companyUnionpayService;

    @PostMapping("/getListServiceProvider")
    @LoginRequired
    @ApiOperation(value = "查询服务商列表", notes = "查询服务商列表")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "regulatorTaxDto", value = "监管服务商查询", dataType = "RegulatorTaxDTO", required = true)})
    public ReturnJson getListServiceProvider(@Valid @RequestBody RegulatorTaxDTO regulatorTaxDto, @ApiParam(hidden = true) @RequestAttribute(value = "userId", required = false) String regulatorId) {
        return regulatorTaxService.listTax(regulatorTaxDto, regulatorId);
    }

    @PostMapping("getServiceProvider")
    @ApiOperation(value = "查询服务商信息", notes = "查询服务商信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taxId", value = "查询服务商信息", required = true)})
    public ReturnJson getListServiceProvider(@NotBlank(message = "taxId不能为空") @RequestParam(required = false) String taxId) {
        return regulatorTaxService.getTax(taxId);
    }

    @PostMapping("/exportRegulatorTax")
    @ApiOperation(value = "导出所监管的服务商", notes = "导出所监管的服务商")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taxIds", value = "服务商ID字符串，每个服务商ID之间用英文逗号隔开", required = true)})
    public ReturnJson exportRegulatorTax(@NotBlank(message = "服务商Id不能为空！") @RequestParam String taxIds,  HttpServletResponse response) throws CommonException {
        ReturnJson returnJson = regulatorTaxService.batchExportTax(taxIds, response);
        if (returnJson.getCode() == 300) {
            return returnJson;
        }
        return null;
    }

    @PostMapping("/getPayInfo")
    @ApiOperation(value = "支付订单信息", notes = "支付订单信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "payInfoDto", value = "支付订单信息查询", dataType = "PayInfoDTO", required = true)})
    public ReturnJson getPayInfo(@Valid @RequestBody PayInfoDTO payInfoDto) {
        return regulatorTaxService.getPayInfo(payInfoDto);
    }


    @PostMapping("/exportRegulatorPayInfo")
    @ApiOperation(value = "导出所监管的服务商下的支付订单信息", notes = "导出所监管的服务商下的支付订单信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "paymentOrderIds", value = "支付订单ID字符串，每个支付订单ID之间用英文逗号隔开", required = true)})
    public ReturnJson exportRegulatorPayInfo(@NotBlank(message = "支付订单Id不能为空！") @RequestParam String paymentOrderIds,  HttpServletResponse response) throws CommonException {
        ReturnJson returnJson = regulatorTaxService.batchExportPayInfo(paymentOrderIds, response);
        if (returnJson.getCode() == 300) {
            return returnJson;
        }
        return null;
    }

    @PostMapping("/getPaymentInventory")
    @ApiOperation(value = "查询支付订单的支付明细", notes = "查询支付订单的支付明细")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "paymentOrderId", value = "支付订单ID", required = true),
            @ApiImplicitParam(name = "pageNo", value = "当前页数"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示的条数")})
    public ReturnJson getPaymentInventory(@NotBlank(message = "支付ID不能为空！") @RequestParam String paymentOrderId, @NotNull(message = "当前页数不能为空！") @RequestParam(defaultValue = "1") Integer pageNo, @NotNull(message = "每页显示的条数不能为空！") @RequestParam(defaultValue = "10") Integer pageSize) {
        return regulatorTaxService.getPaymentInventoryInfo(paymentOrderId, pageNo, pageSize);
    }

    @PostMapping("/homeFourData")
    @LoginRequired
    @ApiOperation(value = "监管服务商流水信息", notes = "监管服务商流水信息")
    public ReturnJson getHomeFourData(@ApiParam(hidden = true) @RequestAttribute(value = "userId", required = false) String regulatorId) {
        return regulatorTaxService.homeFourData(regulatorId);
    }

    @PostMapping("/getPaymentOrderInfo")
    @ApiOperation(value = "查询支付订单的信息", notes = "查询支付订单的信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "paymentOrderId", value = "支付订单ID", required = true),
            @ApiImplicitParam(name = "type", value = "支付订单类型", required = true)})
    public ReturnJson getPaymentOrderInfo(@NotBlank(message = "支付订单ID不能为空！") @RequestParam String paymentOrderId,@NotNull(message = "支付订单类型不能为空！") @RequestParam Integer type) {
        return regulatorTaxService.getPaymentOrderInfo(paymentOrderId,type);
    }

    @GetMapping("/queryCompanyUnionpayBalance")
    @ApiOperation(value = "查询商户相应银联的余额详情", notes = "查询商户相应银联的余额详情")
    @LoginRequired
    public ReturnJson queryCompanyUnionpayDetail(@ApiParam(value = "商户ID") @NotBlank(message = "请选择商户") @RequestParam(required = false) String merchantId,
                                                 @ApiParam(value = "服务商ID") @NotBlank(message = "请选择服务商") @RequestParam(required = false) String taxId,
                                                 @ApiParam(value = "银行类型") @NotNull(message = "请选择银行类型") @RequestParam(required = false) UnionpayBankType unionpayBankType) throws Exception {
        return companyUnionpayService.queryCompanyUnionpayDetail(merchantId, taxId, unionpayBankType);
    }

}
