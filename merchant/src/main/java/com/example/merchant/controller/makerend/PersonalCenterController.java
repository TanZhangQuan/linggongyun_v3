package com.example.merchant.controller.makerend;

import com.example.common.util.ReturnJson;
import com.example.merchant.service.PersonalCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

@Api(value = "小程序个人信息", tags = "小程序个人信息")
@RestController
@RequestMapping("/makerend/personalCenter")
@Validated
public class PersonalCenterController {

    @Resource
    private PersonalCenterService personalCenterService;

    @PostMapping("/personageInfo")
    @ApiOperation(value = "个人信息", notes = "个人信息", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "workerId", value = "创客ID", paramType = "query", required = true)
    })
     public ReturnJson personageInfo(@NotBlank(message = "创客ID不能为空！") @RequestParam(required = false) String workerId){
        return personalCenterService.personageInfo(workerId);
    }
}
