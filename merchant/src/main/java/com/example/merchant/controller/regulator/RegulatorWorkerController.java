package com.example.merchant.controller.regulator;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.regulator.RegulatorWorkerDto;
import com.example.merchant.service.RegulatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


@Api(value = "监管部门中心的创客监管", tags = "监管部门中心的创客监管")
@RestController
@RequestMapping("/regulator/regulatorWorker")
@Validated
public class RegulatorWorkerController {

    @Autowired
    private RegulatorService regulatorService;

    @PostMapping("/getRegulatorWorker")
    @ApiOperation(value = "获取所监管的创客的流水统计", notes = "获取所监管的创客的流水统计", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "regulatorWorkerDto", value = "监管部门的信息", required = true, dataType = "RegulatorWorkerDto")})
    public ReturnJson getRegulatorWorker(@Valid @RequestBody RegulatorWorkerDto regulatorWorkerDto) {
        return regulatorService.getRegulatorWorker(regulatorWorkerDto);
    }

    @PostMapping("/exportRegulatorWorker")
    @ApiOperation(value = "导出所监管的创客的流水统计", notes = "导出所监管的创客的流水统计", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "workerIds", value = "创客ID字符串，每个创客ID之间用英文逗号隔开", required = true),
            @ApiImplicitParam(name = "regulatorId", value = "管理部门ID", required = true)})
    public void exportRegulatorWorker(@NotBlank(message = "创客ID不能为空！") @RequestParam String workerIds, @NotBlank(message = "管理部门ID不能为空！") @RequestParam String regulatorId, HttpServletResponse response) {
        regulatorService.exportRegulatorWorker(workerIds, regulatorId, response);
    }
}
