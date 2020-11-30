package com.example.merchant.controller.regulator;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.regulator.RegulatorWorkerDto;
import com.example.merchant.dto.regulator.RegulatorWorkerPaymentDto;
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


@Api(value = "监管部门中心的创客监管", tags = "监管部门中心的创客监管")
@RestController
@RequestMapping("/regulator/regulatorWorker")
@Validated
public class RegulatorWorkerController {

    @Resource
    private RegulatorService regulatorService;

    @PostMapping("/getRegulatorWorker")
    @LoginRequired
    @ApiOperation(value = "按条件查询所监管的创客", notes = "按条件查询所监管的创客", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "regulatorWorkerDto", value = "监管部门的信息", required = true, dataType = "RegulatorWorkerDto")})
    public ReturnJson getRegulatorWorker(@RequestBody RegulatorWorkerDto regulatorWorkerDto, @ApiParam(hidden = true) @RequestAttribute(value = "userId", required = false) String regulatorId) {
        return regulatorService.getRegulatorWorker(regulatorWorkerDto, regulatorId);
    }

    @PostMapping("/exportRegulatorWorker")
    @LoginRequired
    @ApiOperation(value = "导出所监管的创客", notes = "导出所监管的创客", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "workerIds", value = "创客ID字符串，每个创客ID之间用英文逗号隔开", required = true)})
    public ReturnJson exportRegulatorWorker(@NotBlank(message = "创客ID不能为空！") @RequestParam(required = false) String workerIds, @ApiParam(hidden = true) @RequestAttribute(value = "userId", required = false) String regulatorId, HttpServletResponse response) {
        ReturnJson returnJson = regulatorService.exportRegulatorWorker(workerIds, regulatorId, response);
        if (returnJson.getCode() == 300) {
            return returnJson;
        }
        return null;
    }

    @PostMapping("/countRegulatorWorker")
    @LoginRequired
    @ApiOperation(value = "获取所监管的创客的流水统计", notes = "获取所监管的创客的流水统计", httpMethod = "POST")
    public ReturnJson countRegulatorWorker(@ApiParam(hidden = true) @RequestAttribute(value = "userId", required = false) String regulatorId) {
        return regulatorService.countRegulatorWorker(regulatorId);
    }

    @PostMapping("/countRegulatorWorkerInfo")
    @LoginRequired
    @ApiOperation(value = "获取所监管的创客的详情", notes = "获取所监管的创客的流水统计", httpMethod = "POST")
    @ApiImplicitParams(value = { @ApiImplicitParam(name = "workerId", value = "创客ID", required = true)})
    public ReturnJson countRegulatorWorkerInfo(@ApiParam(hidden = true) @RequestAttribute(value = "userId", required = false) String regulatorId, @NotBlank(message = "创客ID不能为空！") @RequestParam(required = false) String workerId) {
        return regulatorService.countRegulatorWorkerInfo(regulatorId, workerId);
    }

    @PostMapping("/getRegulatorWorkerPaymentInfo")
    @LoginRequired
    @ApiOperation(value = "查询所监管创客的支付明细", notes = "查询所监管创客的支付明细", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "regulatorWorkerPaymentDto", value = "监管部门查询所监管创客的支付明细查询参数", dataType = "RegulatorWorkerPaymentDto", required = true)})
    public ReturnJson getRegulatorWorkerPaymentInfo(@Valid @RequestBody RegulatorWorkerPaymentDto regulatorWorkerPaymentDto, @ApiParam(hidden = true) @RequestAttribute(value = "userId", required = false) String regulatorId) {
        return regulatorService.getRegulatorWorkerPaymentInfo(regulatorWorkerPaymentDto,regulatorId);
    }

    @PostMapping("/exportRegulatorWorkerPaymentInfo")
    @ApiOperation(value = "导出所监管的创客支付明细", notes = "导出所监管的创客支付明细", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "workerId", value = "创客ID", required = true),
            @ApiImplicitParam(name = "paymentIds", value = "支付订单ID字符集,每个支付订单ID之间用英文逗号隔开", required = true)})
    public ReturnJson exportRegulatorWorkerPaymentInfo(@NotBlank(message = "创客ID不能为空！") @RequestParam(required = false) String workerId, @NotBlank(message = "支付订单ID字符集！") @RequestParam(required = false) String paymentIds, HttpServletResponse response) {
        ReturnJson returnJson = regulatorService.exportRegulatorWorkerPaymentInfo(workerId, paymentIds, response);
        if (returnJson.getCode() == 300) {
            return returnJson;
        }
        return null;
    }

    @PostMapping("/getPaymentOrderInfo")
    @ApiOperation(value = "查询支付订单详情", notes = "查询支付订单详情", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "workerId", value = "创客ID", required = true),
            @ApiImplicitParam(name = "paymentId", value = "支付订单ID", required = true),
            @ApiImplicitParam(name = "packageStatus", value = "合作类型", required = true)})
    public ReturnJson getPaymentOrderInfo(@NotBlank(message = "创客ID不能为空！") @RequestParam(required = false) String workerId, @NotBlank(message = "支付订单ID不能为空！") @RequestParam(required = false) String paymentId, @NotNull(message = "合作类型不能为空！") @RequestParam(required = false) Integer packageStatus) {
        return regulatorService.getPaymentOrderInfo(workerId, paymentId, packageStatus);
    }

    @PostMapping("/getPaymentInventory")
    @ApiOperation(value = "查询支付订单的支付明细", notes = "查询支付订单的支付明细", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "paymentOrderId", value = "支付订单ID", required = true),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示的条数", required = true)})
    public ReturnJson getPaymentInventory(@NotBlank(message = "支付订单ID不能为空！") @RequestParam(required = false) String paymentOrderId, @NotNull(message = "当前页数不能为空！") @RequestParam(defaultValue = "1") Integer pageNo, @NotNull(message = "每页显示的条数不能为空！") @RequestParam(defaultValue = "10") Integer pageSize) {
        return regulatorService.getPaymentInventory(paymentOrderId, pageNo, pageSize);
    }
}
