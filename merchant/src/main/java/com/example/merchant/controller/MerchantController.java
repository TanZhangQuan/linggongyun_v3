package com.example.merchant.controller;


<<<<<<< HEAD
import com.example.common.util.ReturnJson;
import com.example.merchant.service.MerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
=======
import com.example.common.ReturnJson;
import com.example.merchant.service.CompanyInfoService;
import com.example.merchant.service.MerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
>>>>>>> e313d3f739bfa1db8fe37f7b824cc242965cb147
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD
import javax.annotation.Resource;
=======
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
>>>>>>> e313d3f739bfa1db8fe37f7b824cc242965cb147

/**
 * <p>
 * 商户信息
 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "商户相关操作接口", tags = {"商户相关操作接口"})
@RestController
@RequestMapping("/merchant")
@Validated
@Api(value = "商户登录及操作", tags = "商户登录及操作")
public class MerchantController {
    @Autowired
    private MerchantService merchantService;

    @Autowired
    private CompanyInfoService companyInfoService;

    @PostMapping("/login")
    @ApiOperation(value = "账号密码登录", notes = "账号密码登录", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="username",value = "登录账号",required = true),
            @ApiImplicitParam(name="password",value = "登录密码",required = true)})
    public ReturnJson merchantLogin(@NotBlank(message = "用户名不能为空") String username, @NotBlank(message = "密码不能为空") String password , HttpServletResponse response){
        return  merchantService.merchantLogin(username, password,response);
    }


    @PostMapping("/senSMS")
    @ApiOperation(value = "发送验证码", notes = "发送验证码", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="mobileCode",value = "手机号码",required = true)})
    public ReturnJson senSMS(@NotBlank(message = "手机号不能为空") @RequestParam(required = false) String mobileCode){
        return merchantService.senSMS(mobileCode);
    }


<<<<<<< HEAD
    private static Logger logger= LoggerFactory.getLogger(MerchantController.class);

    @Resource
    private MerchantService merchantService;

    @ApiOperation("商户列表")
    @GetMapping(value = "/getIdAndName")
    public ReturnJson getIdAndName(){
        ReturnJson returnJson=new ReturnJson("查询失败",300);
        try {
            returnJson = merchantService.getIdAndName();
        }catch (Exception e){
            logger.error("出现异常错误",e);
        }
        return returnJson;
    }

=======
    @PostMapping("/loginMobile")
    @ApiOperation(value = "手机号登录", notes = "手机号登录", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="loginMobile",value = "登录用的手机号码",required = true),
            @ApiImplicitParam(name="checkCode",value = "验证码",required = true)})
    public ReturnJson loginMobile(@NotBlank(message = "手机号不能为空") String loginMobile, @NotBlank(message = "验证码不能为空") String checkCode, HttpServletResponse resource){
        return merchantService.loginMobile(loginMobile, checkCode, resource);
    }
>>>>>>> e313d3f739bfa1db8fe37f7b824cc242965cb147
}
