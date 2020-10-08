package com.example.merchant.controller.merchant;


import com.example.common.util.ReturnJson;
import com.example.merchant.service.AddressService;
import com.example.merchant.service.LinkmanService;
import com.example.merchant.service.MerchantService;
import com.example.mybatis.entity.Address;
import com.example.mybatis.entity.Linkman;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 商户信息
 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "商户端商户相关操作接口", tags = {"商户端商户相关操作接口"})
@RestController
@RequestMapping("/merchant/merchant")
@Validated
public class MerchantMerchantController {
    @Autowired
    private MerchantService merchantService;

    @Autowired
    private LinkmanService linkmanService;

    @Autowired
    private AddressService addressService;

    @PostMapping("/login")
    @ApiOperation(value = "账号密码登录", notes = "账号密码登录", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="username",value = "登录账号",required = true),
            @ApiImplicitParam(name="password",value = "登录密码",required = true)})
    public ReturnJson merchantLogin(@NotBlank(message = "用户名不能为空")  @RequestParam(required = false) String username, @NotBlank(message = "密码不能为空") @RequestParam(required = false) String password , HttpServletResponse response){
        return  merchantService.merchantLogin(username, password,response);
    }


    @PostMapping("/senSMS")
    @ApiOperation(value = "发送验证码", notes = "发送验证码", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="mobileCode",value = "手机号码",required = true)})
    public ReturnJson senSMS(@NotBlank(message = "手机号不能为空") @RequestParam(required = false) String mobileCode){
        return merchantService.senSMS(mobileCode);
    }


    private static Logger logger= LoggerFactory.getLogger(MerchantMerchantController.class);



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

    @PostMapping("/loginMobile")
    @ApiOperation(value = "手机号登录", notes = "手机号登录", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="loginMobile",value = "登录用的手机号码",required = true),
            @ApiImplicitParam(name="checkCode",value = "验证码",required = true)})
    public ReturnJson loginMobile(@NotBlank(message = "手机号不能为空") @RequestParam(required = false) String loginMobile, @NotBlank(message = "验证码不能为空") @RequestParam(required = false) String checkCode, HttpServletResponse resource){
        return merchantService.loginMobile(loginMobile, checkCode, resource);
    }


    @PostMapping("/merchantInfo")
    @ApiOperation(value = "获取商户信息", notes = "获取商户信息", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="merchantId",value = "商户ID",required = true)})
    public ReturnJson merchantInfo(@NotBlank(message = "商户ID不能为空！") @RequestParam String merchantId){
        return merchantService.merchantInfo(merchantId);
    }

    @PostMapping("/addOrUpdataLinkman")
    @ApiOperation(value = "添加或修改联系人", notes = "添加或修改联系人", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="linkman",value = "联系人信息",required = true,dataType = "Linkman")})
    public ReturnJson addOrUpdataLinkman(@RequestBody Linkman linkman){
        return linkmanService.addOrUpdataLinkman(linkman);
    }

    @PostMapping("/updataLinkmanStatus")
    @ApiOperation(value = "停用或启用联系人", notes = "停用或启用联系人", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="linkmanId",value = "联系人ID",required = true),@ApiImplicitParam(name="status",value = "联系人状态",required = true)})
    public ReturnJson updataStatus(@NotBlank(message = "联系人ID不能为空！") @RequestParam String linkmanId, @NotNull(message = "状态不能为空！") @RequestParam Integer status) {
        return linkmanService.updataStatus(linkmanId, status);
    }

    @PostMapping("/removeLinkmenById")
    @ApiOperation(value = "删除商户的联系人", notes = "删除商户的联系人", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="linkmanId",value = "联系人ID",required = true)})
    public ReturnJson removeLinkmenById(@NotBlank(message = "联系人ID不能为空") @RequestParam String linkmanId){
        return linkmanService.removeLinkmenById(linkmanId);
    }

    @GetMapping("/getLinkmanAll")
    @ApiOperation(value = "查询商户的联系人", notes = "查询商户的联系人", httpMethod = "GET")
    @ApiImplicitParams(value={@ApiImplicitParam(name="merchantId",value = "商户ID",required = true)})
    public ReturnJson getLinkmanAll(@NotBlank(message = "商户ID不能为空！") @RequestParam String merchantId){
        return linkmanService.getLinkmanAll(merchantId);
    }

    @GetMapping("/getAddressAll")
    @ApiOperation(value = "查询商户的快递地址信息", notes = "查询商户的快递地址信息", httpMethod = "GET")
    @ApiImplicitParams(value={@ApiImplicitParam(name="merchantId",value = "商户ID",required = true)})
    public ReturnJson getAddressAll(@NotBlank(message = "商户ID") @RequestParam String merchantId){
        return addressService.getAddressAll(merchantId);
    }

    @PostMapping("/addOrUpdataAddress")
    @ApiOperation(value = "添加或修改快递地址", notes = "添加或修改快递地址", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="address",value = "快递地址信息",required = true,dataType = "Address")})
    public ReturnJson addOrUpdataAddress(@RequestBody Address address){
        return addressService.addOrUpdataAddress(address);
    }

    @PostMapping("/updataAddressStatus")
    @ApiOperation(value = "修改快递地址状态", notes = "修改快递地址状态", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="addressId",value = "快递地址ID",required = true),@ApiImplicitParam(name="status",value = "快递地址状态",required = true)})
    public ReturnJson updataAddressStatus(@NotBlank(message = "地址ID不能为空") @RequestParam String addressId, @NotNull(message = "地址状态不能为空") @RequestParam Integer status){
        return addressService.updataAddressStatus(addressId, status);
    }

    @PostMapping("/removeAddressById")
    @ApiOperation(value = "删除快递地址", notes = "删除快递地址", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="addressId",value = "快递地址ID",required = true)})
    public ReturnJson removeAddressById(@NotBlank(message = "地址ID不能为空") @RequestParam String addressId){
        return addressService.removeAddressById(addressId);
    }

    @PostMapping("/updataPassWord")
    @ApiOperation(value = "修改或忘记密码", notes = "修改或忘记密码", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="loginMobile",value = "登录用的手机号码",required = true),
            @ApiImplicitParam(name="checkCode",value = "验证码",required = true),@ApiImplicitParam(name="newPassWord",value = "新密码",required = true)})
    public ReturnJson updataPassWord(@NotBlank(message = "手机号不能为空") @RequestParam(required = false) String loginMobile, @NotBlank(message = "验证码不能为空") @RequestParam(required = false) String checkCode, @NotBlank(message = "新密码不能为空") @RequestParam(required = false)String newPassWord){
        return merchantService.updataPassWord(loginMobile, checkCode, newPassWord);
    }

}
