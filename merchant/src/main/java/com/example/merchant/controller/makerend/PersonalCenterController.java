package com.example.merchant.controller.makerend;

import com.example.common.util.ReturnJson;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.PersonalCenterService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @LoginRequired
     public ReturnJson personageInfo(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String workerId){
        return personalCenterService.personageInfo(workerId);
    }
}
