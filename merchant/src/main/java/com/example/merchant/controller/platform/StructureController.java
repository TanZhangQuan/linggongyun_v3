package com.example.merchant.controller.platform;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.AgentInfoDTO;
import com.example.merchant.dto.platform.ManagersDTO;
import com.example.merchant.exception.CommonException;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.AchievementStatisticsService;
import com.example.merchant.service.AgentTaxService;
import com.example.merchant.service.MerchantService;
import com.example.merchant.service.StructureService;
import io.swagger.annotations.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Api(value = "平台端组织结构", tags = "平台端组织结构")
@RestController
@RequestMapping("/platform/struture")
@Validated
public class StructureController {

    @Resource
    private StructureService structureService;

    @Resource
    private MerchantService merchantService;

    @Resource
    private AgentTaxService agentTaxService;

    @Resource
    private AchievementStatisticsService achievementStatisticsService;

    @PostMapping("/addSalesMan")
    @ApiOperation(value = "添加业务员or编辑业务员", notes = "添加或修改业务员")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "managersDto", value = "业务员的信息", required = true, dataType = "ManagersDTO")
    })
    public ReturnJson addSalesMan(@Valid @RequestBody ManagersDTO managersDto) {
        return structureService.addSalesMan(managersDto);
    }

    @PostMapping("/findBySalesManId")
    @ApiOperation(value = "按ID查找业务员(编辑业务员时用来获取业务员信息)", notes = "按ID查找业务员(编辑业务员时用来获取业务员信息)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "managersId", value = "业务员的ID", required = true)
    })
    public ReturnJson findBySalesManId(@NotBlank(message = "业务员ID不能为空！") @RequestParam String managersId) {
        return structureService.findBySalesManId(managersId);
    }

    @PostMapping("/getSalesManAll")
    @ApiOperation(value = "查询所有业务员", notes = "查询所有业务员")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "一页的条数", required = true)
    })
    public ReturnJson getSalesManAll(@RequestParam(defaultValue = "1") Integer pageNo,
                                     @RequestParam(defaultValue = "1") Integer pageSize) {
        return structureService.getSalesManAll(pageNo, pageSize);
    }

    @PostMapping("/removeSalesMan")
    @ApiOperation(value = "删除业务员", notes = "删除业务员")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "salesManId", value = "业务员ID", required = true)
    })
    public ReturnJson removeSalesMan(@NotBlank(message = "业务员ID不能为空！") @RequestParam String salesManId, HttpServletRequest request) throws CommonException {
        return structureService.removeSalesMan(salesManId);
    }

    @PostMapping("/getSalesManPaymentListCount")
    @ApiOperation(value = "业务员的流水统计", notes = "业务员的流水统计")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "salesManId", value = "业务员ID", required = true)
    })
    public ReturnJson getSalesManPaymentListCount(@NotBlank(message = "业务员ID不能为空！") @RequestParam String salesManId, HttpServletRequest request) throws CommonException {
        return structureService.getSalesManPaymentListCount(salesManId);
    }

    @PostMapping("/getSalesManPaymentList")
    @ApiOperation(value = "业务员的流水", notes = "业务员的流水")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "salesManId", value = "业务员ID", required = true),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "一页的条数", required = true)
    })
    public ReturnJson getSalesManPaymentList(@NotBlank(message = "业务员ID不能为空！") @RequestParam String salesManId,
                                             @RequestParam(defaultValue = "1") Integer pageNo,
                                             @RequestParam(defaultValue = "1") Integer pageSize) throws CommonException {
        return structureService.getSalesManPaymentList(salesManId, pageNo, pageSize);
    }

    @PostMapping("/getSalesManPaymentInfo")
    @ApiOperation(value = "查询支付订单详情", notes = "查询支付订单详情")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentOrderId", value = "支付订单", required = true),
            @ApiImplicitParam(name = "packageStatus", value = "合作类型", required = true)
    })
    public ReturnJson getSalesManPaymentInfo(@NotBlank(message = "支付订单ID不能为空！") @RequestParam String paymentOrderId,
                                             @RequestParam Integer packageStatus) {
        return structureService.getSalesManPaymentInfo(paymentOrderId, packageStatus);
    }

    @PostMapping("/getPaymentInventory")
    @ApiOperation(value = "获取支付清单列表", notes = "获取支付清单列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentOrderId", value = "支付订单ID", required = true),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页的条数", required = true)
    })
    public ReturnJson getPaymentInventory(@NotBlank(message = "支付订单ID不能为空！") @RequestParam String paymentOrderId,
                                          @RequestParam(defaultValue = "1") Integer pageNo,
                                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return structureService.getPaymentInventory(paymentOrderId, pageNo, pageSize);
    }

    @GetMapping("/queryTaxPackage")
    @ApiOperation(value = "查询服务商总包或众包合作信息(代理创建时使用)", notes = "查询服务商总包或众包合作信息(代理创建时使用)")
    @LoginRequired
    public ReturnJson queryTaxPackage(@ApiParam(value = "服务商ID") @NotBlank(message = "请选择服务商") @RequestParam(required = false) String taxId,
                                      @ApiParam(value = "总包或众包") @NotNull(message = "请选择总包或众包") @Range(min = 0, max = 1, message = "请选择正确的合作类型") @RequestParam(required = false) Integer packageStatus) {
        return merchantService.queryTaxPackage(taxId, packageStatus);
    }

    @GetMapping("/queryAgentPackage")
    @ApiOperation(value = "查询代理商总包或众包合作信息(代理商编辑时使用)", notes = "查询代理商总包或众包合作信息(代理商编辑时使用)")
    @LoginRequired
    public ReturnJson queryAgentPackage(@ApiParam(value = "服务商ID") @NotBlank(message = "请选择服务商") @RequestParam(required = false) String taxId,
                                        @ApiParam(value = "代理商ID") @NotBlank(message = "请选择代理商") @RequestParam(required = false) String agentId,
                                        @ApiParam(value = "总包或众包") @NotNull(message = "请选择总包或众包") @Range(min = 0, max = 1, message = "请选择正确的合作类型") @RequestParam(required = false) Integer packageStatus) {
        return agentTaxService.queryAgentPackage(taxId, agentId, packageStatus);
    }


    @PostMapping("/addAgent")
    @ApiOperation(value = "添加代理商or编辑代理商", notes = "添加代理商or编辑代理商")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "agentInfoDto", value = "代理商的信息", required = true, dataType = "AgentInfoDTO")
    })
    public ReturnJson addAgent(@Valid @RequestBody AgentInfoDTO agentInfoDto) throws Exception {
        return structureService.addAgent(agentInfoDto);
    }

    @PostMapping("/getAgentAll")
    @ApiOperation(value = "查询所有代理商", notes = "查询所有代理商")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "一页的条数", required = true)
    })
    public ReturnJson getAgentAll(@RequestParam(defaultValue = "1") Integer pageNo,
                                  @RequestParam(defaultValue = "1") Integer pageSize) {
        return structureService.getAgentAll(pageNo, pageSize);
    }

    @PostMapping("/findByAgentId")
    @ApiOperation(value = "按ID查找代理商(编辑代理商时用来获取代理商信息)", notes = "按ID查找代理商(编辑业务员时用来获取代理商信息)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "agentId", value = "代理商的ID", required = true)
    })
    public ReturnJson findByAgentId(@NotBlank(message = "代理商ID不能为空！") @RequestParam String agentId) {
        return structureService.findByAgentId(agentId);
    }

    @PostMapping("/removeAgent")
    @ApiOperation(value = "删除代理商", notes = "删除代理商")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "AgentId", value = "代理商ID", required = true)
    })
    public ReturnJson removeAgent(@NotBlank(message = "代理商ID不能为空！") @RequestParam String AgentId) {
        return structureService.removeAgent(AgentId);
    }

    @PostMapping("/getAgentPaymentListCount")
    @ApiOperation(value = "代理商的流水统计", notes = "代理商的流水统计")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "agentId", value = "代理商ID", required = true)
    })
    public ReturnJson getAgentPaymentListCount(@NotBlank(message = "代理商ID不能为空！") @RequestParam String agentId,
                                               HttpServletRequest request) throws CommonException {
        return structureService.getSalesManPaymentListCount(agentId);
    }

    @PostMapping("/getAgentPaymentList")
    @ApiOperation(value = "代理商的流水", notes = "代理商的流水")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "agentId", value = "代理商ID", required = true),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true), @ApiImplicitParam(name = "pageSize", value = "一页的条数", required = true)
    })
    public ReturnJson getAgentPaymentList(@NotBlank(message = "代理商ID不能为空！") @RequestParam String agentId,
                                          @RequestParam(defaultValue = "1") Integer pageNo,
                                          @RequestParam(defaultValue = "1") Integer pageSize) throws CommonException {
        return structureService.getSalesManPaymentList(agentId, pageNo, pageSize);
    }

    @GetMapping("querySalesman")
    @ApiOperation(value = "查询平台业务专员", notes = "查询平台业务专员")
    @LoginRequired
    public ReturnJson querySalesman(@RequestAttribute("userId") @ApiParam(hidden = true) String userId) {
        return structureService.querySalesman(userId);
    }


    @GetMapping("salesmanAndAgentStatistics")
    @ApiOperation(value = "平台业务员或代理商佣金统计", notes = "平台业务员或代理商佣金统计")
    @LoginRequired
    public ReturnJson salesmanCommissionStatistics(@RequestAttribute("userId") @ApiParam(hidden = true) String userId,
                                                   @RequestParam(defaultValue = "1") Integer pageNo,
                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                   @ApiParam(value = "业务员佣金统计传2，代理商佣金统计传1") @RequestParam Integer objectType,
                                                   @RequestParam String time) {
        return structureService.salesmanAndAgentStatistics(userId, objectType, time, pageNo, pageSize);
    }

    @GetMapping("totalSalesmanSAndAgentStatistics")
    @ApiOperation(value = "平台业务员或代理商总佣金记录", notes = "平台业务员或代理商总佣金记录")
    @LoginRequired
    public ReturnJson totalSalesmanStatistics(@RequestAttribute("userId") @ApiParam(hidden = true) String userId,
                                              @ApiParam(value = "业务员佣金统计传2，代理商佣金统计传1") @RequestParam Integer objectType) {
        return structureService.totalSalesmanAndAgentStatistics(userId, objectType);
    }


    @GetMapping("salesmanAndAgentStatisticsDetail")
    @ApiOperation(value = "平台业务员或代理商佣金记录详情", notes = "平台业务员或代理商佣金记录详情")
    @LoginRequired
    public ReturnJson salesmanAndAgentStatisticsDetail(@RequestParam String managersId,
                                                       @RequestParam String achievementStatisticsId) {
        return structureService.salesmanSAndAgentStatisticsDetail(managersId, achievementStatisticsId);
    }


    @GetMapping("salesmanSAndAgentDetail")
    @ApiOperation(value = "平台业务员或代理商总包众包订单详情", notes = "平台业务员或代理商总包众包订单详情")
    @LoginRequired
    public ReturnJson salesmanSAndAgentDetail(@RequestParam String managersId,
                                              @RequestParam(defaultValue = "1") Integer pageNo,
                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                              @ApiParam(value = "0代表直客，1代表代理商的") @RequestParam Integer customerType) {
        return structureService.salesmanSAndAgentDetail(managersId, customerType, pageNo, pageSize);
    }

    @PostMapping("/commissionSettlement")
    @ApiOperation(value = "业务员或代理商结算", notes = "业务员或代理商结算")
    public ReturnJson commissionSettlement(@NotBlank(message = "佣金Id不能为空") @RequestParam String achievementStatisticsId) {
        return achievementStatisticsService.commissionSettlement(achievementStatisticsId);
    }


}
