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
    @ApiOperation(value = "账号密码登录", notes = "账号密码登录", httpMethod = "POST")
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
    @ApiOperation(value = "登出", notes = "登出", httpMethod = "POST")
    public ReturnJson regulatorLogout(@ApiParam(hidden = true) @RequestAttribute(value = "userId", required = false) String regulatorId) {
        return regulatorService.regulatorLogout(regulatorId);
    }
}
