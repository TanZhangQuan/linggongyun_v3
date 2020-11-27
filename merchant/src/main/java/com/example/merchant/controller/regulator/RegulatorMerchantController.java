package com.example.merchant.controller.regulator;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.regulator.RegulatorMerchantDto;
import com.example.merchant.dto.regulator.RegulatorMerchantPaymentOrderDto;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.RegulatorService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Api(value = "监管部门中心的商户监管", tags = "监管部门中心的商户监管")
@RestController
@RequestMapping("/regulator/regulatorMerchant")
@Validated
public class RegulatorMerchantController {
    @Resource
    private RegulatorService regulatorService;

    @PostMapping("/getCountRegulatorMerchant")
    @ApiOperation(value = "统计所监管的商户的流水", notes = "统计所监管的商户的流水", httpMethod = "POST")
    @LoginRequired
    public ReturnJson getCountRegulatorMerchant(@NotBlank(message = "管理部门ID不能为空！") @ApiParam(hidden = true) @RequestAttribute(value = "userId", required = false) String regulatorId) {
        return regulatorService.getCountRegulatorMerchant(regulatorId);
    }

    @PostMapping("/getRegulatorMerchant")
    @ApiOperation(value = "按条件查询所监管的商户", notes = "按条件查询所监管的商户", httpMethod = "POST")
    @LoginRequired
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "regulatorMerchantDto", value = "查询所监管的商户条件", required = true, dataType = "RegulatorMerchantDto")
    })
    public ReturnJson getRegulatorMerchant(@RequestBody RegulatorMerchantDto regulatorMerchantDto, @ApiParam(hidden = true) @RequestAttribute(value = "userId", required = false) String regulatorId) {
        return regulatorService.getRegulatorMerchant(regulatorMerchantDto, regulatorId);
    }

    @PostMapping("/exportRegulatorMerchant")
    @ApiOperation(value = "导出所监管的商户", notes = "导出所监管的商户", httpMethod = "POST")
    @LoginRequired
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "companyIds", value = "商户编号字符集，每个商户编号之间用英文逗号隔开", required = true)})
    public ReturnJson exportRegulatorMerchant(@NotBlank(message = "导出的商户不能为空！") @RequestParam(required = false) String companyIds, @ApiParam(hidden = true) @RequestAttribute(value = "userId", required = false) String regulatorId, HttpServletResponse response) {
        ReturnJson returnJson = regulatorService.exportRegulatorMerchant(companyIds, regulatorId, response);
        if (returnJson.getCode() == 300) {
            return returnJson;
        }
        return null;
    }

    @PostMapping("/getRegulatorMerchantParticulars")
    @ApiOperation(value = "查询所监管的公司详情", notes = "查询所监管的公司详情", httpMethod = "POST")
    @LoginRequired
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "companyId", value = "公司ID", required = true)})
    public ReturnJson getRegulatorMerchantParticulars(@NotBlank(message = "公司ID不能为空！") @RequestParam(required = false) String companyId, @ApiParam(hidden = true) @RequestAttribute(value = "userId", required = false) String regulatorId) {
        return regulatorService.getRegulatorMerchantParticulars(companyId, regulatorId);
    }

    @PostMapping("/getRegulatorMerchantPaymentOrder")
    @ApiOperation(value = "查询所监管商户的支付订单", notes = "查询所监管商户的支付订单", httpMethod = "POST")
    @LoginRequired
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "regulatorMerchantPaymentOrderDto", value = "查询所监管商户的支付订单参数", required = true, dataType = "RegulatorMerchantPaymentOrderDto")
    })
    public ReturnJson getRegulatorMerchantPaymentOrder(@Valid @RequestBody RegulatorMerchantPaymentOrderDto regulatorMerchantPaymentOrderDto, @ApiParam(hidden = true) @RequestAttribute(value = "userId", required = false) String regulatorId) {
        return regulatorService.getRegulatorMerchantPaymentOrder(regulatorMerchantPaymentOrderDto, regulatorId);
    }

    @PostMapping("/exportRegulatorMerchantPaymentOrder")
    @ApiOperation(value = "导出所监管商户的支付订单", notes = "导出所监管商户的支付订单", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentOrderIds", value = "支付订单ID字符集，每个支付订单ID之间用英文逗号隔开", required = true)
    })
    public ReturnJson exportRegulatorMerchantPaymentOrder(@NotBlank(message = "导出的数据不能为空！") @RequestParam(required = false) String paymentOrderIds, HttpServletResponse response) {
        return regulatorService.exportRegulatorMerchantPaymentOrder(paymentOrderIds, response);
    }

    @PostMapping("/getPaymentOrderInfo")
    @ApiOperation(value = "查询支付订单详情", notes = "查询支付订单详情", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentId", value = "支付订单ID", required = true),
            @ApiImplicitParam(name = "packageStatus", value = "合作类型", required = true)
    })
    public ReturnJson getPaymentOrderInfo(@NotBlank(message = "支付订单ID不能为空！") @RequestParam(required = false) String paymentId, @NotNull(message = "合作类型不能为空！") @RequestParam(required = false) Integer packageStatus) {
        return regulatorService.getPaymentOrderInfo("", paymentId, packageStatus);
    }

    @PostMapping("/getPaymentInventory")
    @ApiOperation(value = "查询支付订单的支付明细", notes = "查询支付订单的支付明细", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentOrderId", value = "支付订单ID", required = true),
            @ApiImplicitParam(name = "page", value = "当前页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示的条数", required = true)
    })
    public ReturnJson getPaymentInventory(@NotBlank(message = "支付订单ID不能为空！") @RequestParam(required = false) String paymentOrderId, @NotNull(message = "当前页数不能为空！") @RequestParam(defaultValue = "1") Integer page, @NotNull(message = "每页显示的条数不能为空！") @RequestParam(defaultValue = "10") Integer pageSize) {
        return regulatorService.getPaymentInventory(paymentOrderId, page, pageSize);
    }

}
