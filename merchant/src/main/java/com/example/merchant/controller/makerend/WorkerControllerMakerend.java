package com.example.merchant.controller.makerend;

import com.example.common.util.ReturnJson;
import com.example.merchant.service.WorkerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Api(value = "小程序创客登录", tags = {"小程序创客登录"})
@RestController
@Validated
@RequestMapping(value = "makerend/worker")
public class WorkerControllerMakerend {

    @Autowired
    private WorkerService workerService;

    @PostMapping("/workerLogin")
    @ApiOperation(value = "账号密码登录", notes = "账号密码登录", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "username", value = "登录账号", required = true),
            @ApiImplicitParam(name = "password", value = "登录密码", required = true)})
    public ReturnJson workerLogin(@NotBlank(message = "用户名不能为空") @RequestParam String username, @NotBlank(message = "密码不能为空") @RequestParam String password, HttpServletResponse response) {
        return workerService.loginWorker(username, password, response);
    }

    @PostMapping("/woekerSenSMS")
    @ApiOperation(value = "发送手机验证码", notes = "发送手机验证码", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "mobileCode", value = "手机号码", required = true)})
    public ReturnJson workerSenSMS(@NotBlank(message = "请输入手机号")
                                   @Length(min = 11, max = 11, message = "请输入11位手机号")
                                   @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
                                   @RequestParam String mobileCode) {
        return workerService.senSMS(mobileCode);
    }

    @PostMapping("/workerMobileLogin")
    @ApiOperation(value = "手机验证码登录", notes = "手机验证码登录", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "mobileCode", value = "手机号码", required = true),
            @ApiImplicitParam(name = "checkCode", value = "验证码", required = true)})
    public ReturnJson workerMobileLogin(@NotBlank(message = "请输入手机号")
                                        @Length(min = 11, max = 11, message = "请输入11位手机号")
                                        @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
                                        @RequestParam String mobileCode, @NotBlank(message = "验证码不能为空！") @RequestParam String checkCode, HttpServletResponse resource) {
        return workerService.loginMobile(mobileCode, checkCode, resource);
    }

    @PostMapping("/updataPassWord")
    @ApiOperation(value = "修改或忘记密码", notes = "修改或忘记密码", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "loginMobile", value = "登录用的手机号码", required = true),
            @ApiImplicitParam(name = "checkCode", value = "验证码", required = true), @ApiImplicitParam(name = "newPassWord", value = "新密码", required = true)})
    public ReturnJson workerupdataPassWord(@NotBlank(message = "手机号不能为空") @RequestParam(required = false) String loginMobile, @NotBlank(message = "验证码不能为空") @RequestParam(required = false) String checkCode, @NotBlank(message = "新密码不能为空") @RequestParam(required = false) String newPassWord) {
        return workerService.updataPassWord(loginMobile, checkCode, newPassWord);
    }

    @PostMapping("/workerWeiXinLogin")
    @ApiOperation(value = "微信登陆", notes = "修改或忘记密码", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "code", value = "微信授权码", required = true),
            @ApiImplicitParam(name = "vi", value = "数据加密时所使用的偏移量", required = true), @ApiImplicitParam(name = "encryptedData", value = "加密数据串", required = true)})
    public ReturnJson workerWeiXinLogin(@NotBlank(message = "微信授权码不能为空") @RequestParam(required = false) String code, @NotBlank(message = "数据加密时所使用的偏移量不能为空") @RequestParam(required = false) String vi, @NotBlank(message = "加密数据串不能为空") @RequestParam(required = false) String encryptedData) {
        return workerService.wxLogin(code, vi, encryptedData);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", notes = "退出登录", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "workerId", value = "创客ID", required = true)})
    public ReturnJson logout(@NotBlank(message = "手机号不能为空") @RequestParam String workerId) {
        return workerService.logout(workerId);
    }
}
