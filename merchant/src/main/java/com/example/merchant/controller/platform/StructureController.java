package com.example.merchant.controller.platform;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.AgentInfoDto;
import com.example.merchant.dto.platform.ManagersDto;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.StructureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Api(value = "平台端组织结构", tags = "平台端组织结构")
@RestController
@RequestMapping("/platform/struture")
@Validated
public class StructureController {

    @Resource
    private StructureService structureService;

    @PostMapping("/addSalesMan")
    @ApiOperation(value = "添加业务员", notes = "添加或修改业务员", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "managersDto", value = "业务员的信息", required = true, dataType = "ManagersDto")
    })
    public ReturnJson addSalesMan(@Valid @RequestBody ManagersDto managersDto) {
        return structureService.addSalesMan(managersDto);
    }

    @PostMapping("/updateSalesMan")
    @ApiOperation(value = "编辑业务员", notes = "编辑业务员", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "managersDto", value = "业务员的信息", required = true, dataType = "ManagersDto")
    })
    public ReturnJson updateSalesMan(@Valid @RequestBody ManagersDto managersDto) {
        return structureService.updateSalesMan(managersDto);
    }

    @PostMapping("/findBySalesManId")
    @ApiOperation(value = "按ID查找业务员(编辑业务员时用来获取业务员信息)", notes = "按ID查找业务员(编辑业务员时用来获取业务员信息)", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "managersId", value = "业务员的ID", required = true)
    })
    public ReturnJson findBySalesManId(@NotBlank(message = "业务员ID不能为空！") @RequestParam String managersId) {
        return structureService.findBySalesManId(managersId);
    }

    @PostMapping("/getSalesManAll")
    @ApiOperation(value = "查询所以业务员", notes = "查询所以业务员", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page", value = "当前页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "一页的条数", required = true)
    })
    public ReturnJson getSalesManAll(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "1") Integer pageSize) {
        return structureService.getSalesManAll(page, pageSize);
    }

    @PostMapping("/removeSalesMan")
    @ApiOperation(value = "删除业务员", notes = "删除业务员", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "salesManId", value = "业务员ID", required = true)
    })
    public ReturnJson removeSalesMan(@NotBlank(message = "业务员ID不能为空！") @RequestParam String salesManId,HttpServletRequest request) throws CommonException {
        return structureService.removeSalesMan(salesManId);
    }

    @PostMapping("/getSalesManPaymentListCount")
    @ApiOperation(value = "业务员的流水统计", notes = "业务员的流水统计", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "salesManId", value = "业务员ID", required = true)
    })
    public ReturnJson getSalesManPaymentListCount(@NotBlank(message = "业务员ID不能为空！") @RequestParam String salesManId, HttpServletRequest request) throws CommonException {
        return structureService.getSalesManPaymentListCount(salesManId);
    }

    @PostMapping("/getSalesManPaymentList")
    @ApiOperation(value = "业务员的流水", notes = "业务员的流水", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "salesManId", value = "业务员ID", required = true),
            @ApiImplicitParam(name = "page", value = "当前页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "一页的条数", required = true)
    })
    public ReturnJson getSalesManPaymentList(@NotBlank(message = "业务员ID不能为空！") @RequestParam String salesManId, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "1") Integer pageSize) throws CommonException {
        return structureService.getSalesManPaymentList(salesManId, page, pageSize);
    }

    @PostMapping("/getSalesManPaymentInfo")
    @ApiOperation(value = "查询支付订单详情", notes = "查询支付订单详情", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentOrderId", value = "支付订单", required = true),
            @ApiImplicitParam(name = "packageStatus", value = "合作类型", required = true)
    })
    public ReturnJson getSalesManPaymentInfo(@NotBlank(message = "支付订单ID不能为空！") @RequestParam String paymentOrderId, @RequestParam Integer packageStatus) {
        return structureService.getSalesManPaymentInfo(paymentOrderId, packageStatus);
    }

    @PostMapping("/getPaymentInventory")
    @ApiOperation(value = "获取支付清单列表", notes = "获取支付清单列表", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentOrderId", value = "支付订单ID", required = true),
            @ApiImplicitParam(name = "page", value = "当前页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页的条数", required = true)
    })
    public ReturnJson getPaymentInventory(@NotBlank(message = "支付订单ID不能为空！") @RequestParam String paymentOrderId, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {
        return structureService.getPaymentInventory(paymentOrderId, page, pageSize);
    }

    @PostMapping("/addAgent")
    @ApiOperation(value = "添加代理商", notes = "添加代理商", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "agentInfoDto", value = "代理商的信息", required = true, dataType = "AgentInfoDto")
    })
    public ReturnJson addAgent(@Valid @RequestBody AgentInfoDto agentInfoDto) {
        return structureService.addAgent(agentInfoDto);
    }

    @PostMapping("/updataAgent")
    @ApiOperation(value = "编辑代理商", notes = "编辑代理商", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "agentInfoDto", value = "代理商的信息", required = true, dataType = "AgentInfoDto")
    })
    public ReturnJson updataAgent(@Valid @RequestBody AgentInfoDto agentInfoDto) {
        return structureService.updataAgent(agentInfoDto);
    }

    @PostMapping("/getAgentAll")
    @ApiOperation(value = "查询所以代理商", notes = "查询所以代理商", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page", value = "当前页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "一页的条数", required = true)
    })
    public ReturnJson getAgentAll(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "1") Integer pageSize) {
        return structureService.getAgentAll(page, pageSize);
    }

    @PostMapping("/findByAgentId")
    @ApiOperation(value = "按ID查找代理商(编辑代理商时用来获取代理商信息)", notes = "按ID查找代理商(编辑业务员时用来获取代理商信息)   ", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "agentId", value = "代理商的ID", required = true)
    })
    public ReturnJson findByAgentId(@NotBlank(message = "代理商ID不能为空！") @RequestParam String agentId) {
        return structureService.findByAgentId(agentId);
    }

    @PostMapping("/removeAgent")
    @ApiOperation(value = "删除代理商", notes = "删除代理商", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "AgentId", value = "代理商ID", required = true)
    })
    public ReturnJson removeAgent(@NotBlank(message = "代理商ID不能为空！") @RequestParam String AgentId) {
        return structureService.removeAgent(AgentId);
    }

    @PostMapping("/getAgentPaymentListCount")
    @ApiOperation(value = "代理商的流水统计", notes = "代理商的流水统计", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "agentId", value = "代理商ID", required = true)
    })
    public ReturnJson getAgentPaymentListCount(@NotBlank(message = "代理商ID不能为空！") @RequestParam String agentId,HttpServletRequest request) throws CommonException {
        return structureService.getSalesManPaymentListCount(agentId);
    }

    @PostMapping("/getAgentPaymentList")
    @ApiOperation(value = "代理商的流水", notes = "代理商的流水", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "agentId", value = "代理商ID", required = true),
            @ApiImplicitParam(name = "page", value = "当前页数", required = true), @ApiImplicitParam(name = "pageSize", value = "一页的条数", required = true)
    })
    public ReturnJson getAgentPaymentList(@NotBlank(message = "代理商ID不能为空！") @RequestParam String agentId, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "1") Integer pageSize) throws CommonException {
        return structureService.getSalesManPaymentList(agentId, page, pageSize);
    }
}
