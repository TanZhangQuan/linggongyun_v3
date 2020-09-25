package com.example.merchant.controller.platform;


import com.example.common.util.ReturnJson;
import com.example.merchant.dto.RegulatorDto;
import com.example.merchant.service.RegulatorService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 监管部门 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-25
 */
@RestController
@RequestMapping("/platform/regulator")
@Api(value = "平台端监管部门管理", tags = "平台端监管部门管理")
@Validated
public class RegulatorController {
    @Autowired
    private RegulatorService regulatorService;


    @PostMapping("/addRegulator")
    @ApiOperation(value = "添加监管部门", notes = "添加监管部门", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "regulatorDto", value = "监管部门的信息", required = true, dataType = "RegulatorDto")})
    public ReturnJson addRegulator(RegulatorDto regulatorDto){
        return regulatorService.addRegulator(regulatorDto);
    }

    @PostMapping("/updateRegulator")
    @ApiOperation(value = "编辑监管部门", notes = "编辑监管部门", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "regulatorDto", value = "监管部门的信息", required = true, dataType = "RegulatorDto")})
    public ReturnJson updateRegulator(RegulatorDto regulatorDto){
        return regulatorService.updateRegulator(regulatorDto);
    }

    @PostMapping("/getByRegulatorId")
    @ApiOperation(value = "按ID查询监管部门", notes = "按ID查询监管部门", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "regulatorId", value = "监管部门的ID", required = true)})
    public ReturnJson getByRegulatorId(@NotNull(message = "监管部门的ID不能为空！") Long regulatorId){
        return regulatorService.getByRegulatorId(regulatorId);
    }
}
