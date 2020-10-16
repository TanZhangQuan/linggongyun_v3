package com.example.merchant.controller.platform;

import com.example.common.util.ReturnJson;
import com.example.merchant.service.ManagersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ReturnJson managersLogin(@NotBlank(message = "用户名不能为空") @RequestParam(required = false) String username, @NotBlank(message = "密码不能为空") @RequestParam(required = false) String password, HttpServletResponse response) {
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
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "token值", value = "token值", required = true)})
    public ReturnJson getCustomizedInfo(@NotBlank(message = "customizedId不能为空！") @RequestParam String token) {
        return managersService.getCustomizedInfo(token);
    }

    @PostMapping("/managerLogout")
    @ApiOperation(value = "登出", notes = "登出", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "merchantId", value = "登录的商户id", required = true)})
    public ReturnJson managerLogout(@NotNull(message = "商户id不能为空") @RequestParam(required = false) String merchantId) {
        return managersService.logout(merchantId);
    }
}
