package com.example.merchant.controller.regulator;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.regulator.RegulatorWorkerDto;
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


@Api(value = "监管部门中心的创客监管", tags = "监管部门中心的创客监管")
@RestController
@RequestMapping("/regulator/regulatorWorker")
@Validated
public class RegulatorWorkerController {

    @Resource
    private RegulatorService regulatorService;

    @PostMapping("/getRegulatorWorker")
    @ApiOperation(value = "按条件查询所监管的创客", notes = "按条件查询所监管的创客", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "regulatorWorkerDto", value = "监管部门的信息", required = true, dataType = "RegulatorWorkerDto")})
    public ReturnJson getRegulatorWorker(@Valid @RequestBody RegulatorWorkerDto regulatorWorkerDto) {
        return regulatorService.getRegulatorWorker(regulatorWorkerDto);
    }

    @PostMapping("/exportRegulatorWorker")
    @ApiOperation(value = "导出所监管的创客", notes = "导出所监管的创客", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "workerIds", value = "创客ID字符串，每个创客ID之间用英文逗号隔开", required = true),
            @ApiImplicitParam(name = "regulatorId", value = "管理部门ID", required = true)})
    public ReturnJson exportRegulatorWorker(@NotBlank(message = "创客ID不能为空！") @RequestParam String workerIds, @NotBlank(message = "管理部门ID不能为空！") @RequestParam String regulatorId, HttpServletResponse response) {
        ReturnJson returnJson = regulatorService.exportRegulatorWorker(workerIds, regulatorId, response);
        if (returnJson.getCode() == 300) {
            return returnJson;
        }
        return null;
    }

    @PostMapping("/countRegulatorWorker")
    @ApiOperation(value = "获取所监管的创客的流水统计", notes = "获取所监管的创客的流水统计", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "regulatorId", value = "管理部门ID", required = true)})
    public ReturnJson countRegulatorWorker(@NotBlank(message = "管理部门ID不能为空！") @RequestParam String regulatorId) {
        return regulatorService.countRegulatorWorker(regulatorId);
    }

    @PostMapping("/countRegulatorWorkerInfo")
    @ApiOperation(value = "获取所监管的创客的详情", notes = "获取所监管的创客的流水统计", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "regulatorId", value = "管理部门ID", required = true),@ApiImplicitParam(name = "workerId", value = "创客ID", required = true)})
    public ReturnJson countRegulatorWorkerInfo(@NotBlank(message = "管理部门ID不能为空！") @RequestParam String regulatorId, @NotBlank(message = "创客ID不能为空！") @RequestParam String workerId) {
        return regulatorService.countRegulatorWorkerInfo(regulatorId,workerId);
    }

}
