package com.example.merchant.controller.platform;


import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.RegulatorDto;
import com.example.merchant.dto.platform.RegulatorQueryDto;
import com.example.merchant.dto.platform.RegulatorTaxDto;
import com.example.merchant.service.RegulatorService;
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
import java.util.List;

/**
 * <p>
 * 监管部门 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-25
 */
@Api(value = "平台端监管部门管理", tags = "平台端监管部门管理")
@RestController
@RequestMapping("/platform/regulator")
@Validated
public class RegulatorController {

    @Resource
    private RegulatorService regulatorService;


    @PostMapping("/addRegulator")
    @ApiOperation(value = "添加监管部门", notes = "添加监管部门", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "regulatorDto", value = "监管部门的信息", required = true, dataType = "RegulatorDto")})
    public ReturnJson addRegulator(@Valid @RequestBody RegulatorDto regulatorDto) {
        return regulatorService.addRegulator(regulatorDto);
    }

    @PostMapping("/updateRegulator")
    @ApiOperation(value = "编辑监管部门", notes = "编辑监管部门", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "regulatorDto", value = "监管部门的信息", required = true, dataType = "RegulatorDto")})
    public ReturnJson updateRegulator(@Valid @RequestBody RegulatorDto regulatorDto) {
        return regulatorService.updateRegulator(regulatorDto);
    }

    @PostMapping("/getByRegulatorId")
    @ApiOperation(value = "按ID查询监管部门", notes = "按ID查询监管部门", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "regulatorId", value = "监管部门的ID", required = true)})
    public ReturnJson getByRegulatorId(@NotNull(message = "监管部门的ID不能为空！") @RequestParam(required = false) Long regulatorId) {
        return regulatorService.getByRegulatorId(regulatorId);
    }

    @PostMapping("/getRegulatorQuery")
    @ApiOperation(value = "按条件查询监管部门", notes = "按条件查询监管部门", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "regulatorQueryDto", value = "查询监管部门的查询条件", dataType = "RegulatorQueryDto", required = true)})
    public ReturnJson getRegulatorQuery(@RequestBody RegulatorQueryDto regulatorQueryDto) {
        return regulatorService.getRegulatorQuery(regulatorQueryDto);
    }

    @GetMapping("/getTaxAll")
    @ApiOperation(value = "查询所以服务商", notes = "查询所以服务商", httpMethod = "GET")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "page", value = "当前页数", required = true), @ApiImplicitParam(name = "pageSize", value = "一页的条数", required = true)})
    public ReturnJson getTaxAll(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {
        return regulatorService.getTaxAll(page, pageSize);
    }

    @PostMapping("/addRegulatorTax")
    @ApiOperation(value = "添加监管服务商", notes = "添加监管服务商", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "regulatorTaxDtos", value = "监管服务商的信息", required = true, allowMultiple = true, dataType = "RegulatorTaxDto")})
    public ReturnJson addReulatorTax(@Valid @RequestBody List<RegulatorTaxDto> regulatorTaxDtos) {
        return regulatorService.addRegulatorTax(regulatorTaxDtos);
    }

    @PostMapping("/getRegulatorPaymentCount")
    @ApiOperation(value = "查询监管部门监管的服务商交易统计", notes = "查询监管部门监管的服务商交易统计", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "regulatorId", value = "监管部门的ID", required = true)})
    public ReturnJson getRegultorPaymentCount(@NotBlank(message = "监管部门不能为空！") @RequestParam(required = false) String regulatorId) {
        return regulatorService.getRegultorPaymentCount(regulatorId);
    }

    @PostMapping("/getRegulatorTaxAll")
    @ApiOperation(value = "查看监管服务商", notes = "查看监管服务商", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "regulatorId", value = "监管部门ID", required = true), @ApiImplicitParam(name = "page", value = "当前页数", required = true), @ApiImplicitParam(name = "pageSize", value = "一页的条数", required = true)})
    public ReturnJson getRegulatorTaxAll(@NotBlank(message = "监管部门ID不能为空！") @RequestParam(required = false) String regulatorId, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {
        return regulatorService.getRegulatorTaxAll(regulatorId, page, pageSize);
    }

    @PostMapping("/getRegulatorTaxPaymentList")
    @ApiOperation(value = "查看监管服务商的成交明细", notes = "查看监管服务商的成交明细", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taxId", value = "监管服务商ID", required = true), @ApiImplicitParam(name = "page", value = "当前页数", required = true), @ApiImplicitParam(name = "pageSize", value = "一页的条数", required = true)})
    public ReturnJson getRegulatorTaxPaymentList(@NotBlank(message = "监管服务商ID不能为空！") @RequestParam(required = false) String taxId, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {
        return regulatorService.getRegulatorTaxPaymentList(taxId, page, pageSize);
    }

    @PostMapping("/getPaymentInfo")
    @ApiOperation(value = "查看成交订单", notes = "查看成交订单", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "paymentOrderId", value = "成交订单ID", required = true), @ApiImplicitParam(name = "packageStatus", value = "合作类型", required = true)})
    public ReturnJson getPaymentInfo(@NotBlank(message = "成交订单ID不能为空！") @RequestParam(required = false) String paymentOrderId, @NotNull(message = "合作类型不能为空！") @RequestParam(required = false) Integer packageStatus) {
        return regulatorService.getPaymentOrderInfo(null,paymentOrderId, packageStatus);
    }

    @PostMapping("/getPaymentInventoryInfo")
    @ApiOperation(value = "查看支付清单", notes = "查看支付清单", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "paymentOrderId", value = "成交订单ID", required = true), @ApiImplicitParam(name = "page", value = "当前页数", required = true), @ApiImplicitParam(name = "pageSize", value = "一页的条数", required = true)})
    public ReturnJson getPaymentInventoryInfo(@NotBlank(message = "成交订单ID不能为空！") @RequestParam(required = false) String paymentOrderId, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {
        return regulatorService.getPaymentInventoryInfo(paymentOrderId, page, pageSize);
    }

}
