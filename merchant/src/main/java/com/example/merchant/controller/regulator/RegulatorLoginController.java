package com.example.merchant.controller.regulator;

import com.example.common.util.ReturnJson;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.RegulatorService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

@Api(value = "监管部门中心的登录", tags = "监管部门中心的登录")
@RestController
@RequestMapping("/regulator/regulatorLogin")
@Validated
public class RegulatorLoginController {

    @Autowired
    private RegulatorService regulatorService;

    @PostMapping("/regulatorLogin")
    @ApiOperation(value = "账号密码登录", notes = "账号密码登录")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "username", value = "登录账号", required = true),
            @ApiImplicitParam(name = "password", value = "登录密码", required = true)
    })
    public ReturnJson regulatorLogin(@NotBlank(message = "用户名不能为空") @RequestParam(required = false) String username,
                                    @NotBlank(message = "密码不能为空") @RequestParam(required = false) String password, HttpServletResponse response) {
        return regulatorService.regulatorLogin(username, password, response);
    }

    @PostMapping("/regulatorLogout")
    @LoginRequired
    @ApiOperation(value = "登出", notes = "登出")
    public ReturnJson regulatorLogout(@ApiParam(hidden = true) @RequestAttribute(value = "userId", required = false) String regulatorId) {
        return regulatorService.regulatorLogout(regulatorId);
    }

    @PostMapping("/getRegulatorInfo")
    @LoginRequired
    @ApiOperation(value = "获取监管人员信息", notes = "获取监管人员信息")
    public ReturnJson getRegulatorInfo(@ApiParam(hidden = true) @RequestAttribute(value = "userId", required = false) String regulatorId) {
        return regulatorService.getRegulatorInfo(regulatorId);
    }

    @PostMapping("/updateHeadPortrait")
    @ApiOperation(value = "修改头像", notes = "修改头像")
    @LoginRequired
    public ReturnJson updateHeadPortrait(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String customizedId,
                                         @RequestParam(name = "headPortrait") String headPortrait) {
        return regulatorService.updateHeadPortrait(customizedId, headPortrait);
    }
}
