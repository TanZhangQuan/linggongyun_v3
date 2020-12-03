package com.example.merchant.controller.platform;

import com.example.common.util.ReturnJson;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.ManagersService;
import io.swagger.annotations.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Api(value = "平台端登录接口", tags = {"平台端登录接口"})
@RestController
@RequestMapping("/platform/managers")
@Validated
public class ManagersPaasController {

    @Resource
    private ManagersService managersService;

    @PostMapping("/passLogin")
    @ApiOperation(value = "账号密码登录", notes = "账号密码登录", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "username", value = "登录账号", required = true),
            @ApiImplicitParam(name = "password", value = "登录密码", required = true)
    })
    public ReturnJson managersLogin(@NotBlank(message = "用户名不能为空") @RequestParam(required = false) String username,
                                    @NotBlank(message = "密码不能为空") @RequestParam(required = false) String password, HttpServletResponse response) {
        return managersService.managersLogin(username, password, response);
    }

    @PostMapping("/senSMS")
    @ApiOperation(value = "发送手机验证码", notes = "发送手机验证码", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "mobileCode", value = "手机号码", required = true)
    })
    public ReturnJson managersSenSMS(@NotBlank(message = "请输入手机号") @Length(min = 11, max = 11, message = "请输入11位手机号")
                                     @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码") @RequestParam(required = false) String mobileCode) {

        return managersService.senSMS(mobileCode);
    }

    @PostMapping("/mobileLogin")
    @ApiOperation(value = "手机验证码登录", notes = "手机验证码登录", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "mobileCode", value = "手机号码", required = true),
            @ApiImplicitParam(name = "checkCode", value = "验证码", required = true)
    })
    public ReturnJson managersMobileLogin(@NotBlank(message = "请输入手机号")
                                          @Length(min = 11, max = 11, message = "请输入11位手机号")
                                          @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
                                          @RequestParam(required = false) String mobileCode, @NotBlank(message = "验证码不能为空！") @RequestParam(required = false) String checkCode, HttpServletResponse resource) {

        return managersService.loginMobile(mobileCode, checkCode, resource);
    }

    @PostMapping("/getCustomizedInfo")
    @ApiOperation(value = "获取当前用用户信息", notes = "获取当前用用户信息", httpMethod = "POST")
    @LoginRequired
    public ReturnJson getCustomizedInfo(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String merchantId) {
        return managersService.getCustomizedInfo(merchantId);
    }

    @PostMapping("/managerLogout")
    @ApiOperation(value = "登出", notes = "登出", httpMethod = "POST")
    @LoginRequired
    public ReturnJson managerLogout(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String merchantId) {
        return managersService.logout(merchantId);
    }

    @PostMapping("/updataPassWord")
    @ApiOperation(value = "修改或忘记密码", notes = "修改或忘记密码", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "loginMobile", value = "登录用的手机号码", required = true),
            @ApiImplicitParam(name = "checkCode", value = "验证码", required = true), @ApiImplicitParam(name = "newPassWord", value = "新密码", required = true)})
    public ReturnJson updataPassWord(@NotBlank(message = "手机号不能为空") @RequestParam(required = false) String loginMobile,
                                     @NotBlank(message = "验证码不能为空") @RequestParam(required = false) String checkCode,
                                     @NotBlank(message = "新密码不能为空") @RequestParam(required = false) String newPassWord) {
        return managersService.updataPassWord(loginMobile, checkCode, newPassWord);
    }
}
