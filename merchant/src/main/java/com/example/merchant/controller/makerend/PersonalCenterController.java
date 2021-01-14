package com.example.merchant.controller.makerend;

import com.example.common.util.ReturnJson;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.PersonalCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "小程序个人信息", tags = "小程序个人信息")
@RestController
@RequestMapping("/makerend/personalCenter")
@Validated
public class PersonalCenterController {

    @Resource
    private PersonalCenterService personalCenterService;

    @PostMapping("/personageInfo")
    @ApiOperation(value = "个人信息", notes = "个人信息")
    @LoginRequired
     public ReturnJson personageInfo(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String workerId){
        return personalCenterService.personageInfo(workerId);
    }
}
