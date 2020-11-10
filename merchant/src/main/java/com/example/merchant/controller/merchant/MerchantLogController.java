package com.example.merchant.controller.merchant;

import com.example.common.util.ReturnJson;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.MerchantService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Api(value = "商户端登录接口", tags = {"商户端登录接口"})
@RestController
@RequestMapping("/merchant/login")
@Validated
public class MerchantLogController {
    @Resource
    private MerchantService merchantService;

    @PostMapping("/login")
    @ApiOperation(value = "账号密码登录", notes = "账号密码登录", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "username", value = "登录账号", required = true),
            @ApiImplicitParam(name = "password", value = "登录密码", required = true)})
    public ReturnJson merchantLogin(@NotBlank(message = "用户名不能为空") @RequestParam(required = false) String username,
                                    @NotBlank(message = "密码不能为空") @RequestParam(required = false) String password, HttpServletResponse response) {
        return merchantService.merchantLogin(username, password, response);
    }

    @PostMapping("/senSMS")
    @ApiOperation(value = "发送验证码", notes = "发送验证码", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "mobileCode", value = "手机号码", required = true)})
    public ReturnJson senSMS(@NotBlank(message = "手机号不能为空") @RequestParam(required = false) String mobileCode) {
        return merchantService.senSMS(mobileCode);
    }

    @PostMapping("/merchantLogout")
    @ApiOperation(value = "登出", notes = "登出", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "merchantId", value = "登录的商户id", required = true)})
    public ReturnJson merchantLogout(@NotNull(message = "商户id不能为空") @RequestParam(required = false) String merchantId) {
        return merchantService.logout(merchantId);
    }

    @PostMapping("/updataPassWord")
    @ApiOperation(value = "修改或忘记密码", notes = "修改或忘记密码", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "loginMobile", value = "登录用的手机号码", required = true),
            @ApiImplicitParam(name = "checkCode", value = "验证码", required = true), @ApiImplicitParam(name = "newPassWord", value = "新密码", required = true)})
    public ReturnJson updataPassWord(@NotBlank(message = "手机号不能为空") @RequestParam(required = false) String loginMobile,
                                     @NotBlank(message = "验证码不能为空") @RequestParam(required = false) String checkCode,
                                     @NotBlank(message = "新密码不能为空") @RequestParam(required = false) String newPassWord) {
        return merchantService.updataPassWord(loginMobile, checkCode, newPassWord);
    }

    @PostMapping("/loginMobile")
    @ApiOperation(value = "手机号登录", notes = "手机号登录", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "loginMobile", value = "登录用的手机号码", required = true),
            @ApiImplicitParam(name = "checkCode", value = "验证码", required = true)
    })
    public ReturnJson loginMobile(@NotBlank(message = "手机号不能为空") @RequestParam(required = false) String loginMobile,
                                  @NotBlank(message = "验证码不能为空") @RequestParam(required = false) String checkCode, HttpServletResponse resource) {

        return merchantService.loginMobile(loginMobile, checkCode, resource);
    }


    @PostMapping("/getmerchantCustomizedInfo")
    @ApiOperation(value = "获取当前用用户信息", notes = "获取当前用用户信息", httpMethod = "POST")
    @LoginRequired
    public ReturnJson getmerchantCustomizedInfo(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String merchantId) {
        return merchantService.getmerchantCustomizedInfo(merchantId);
    }
}
