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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 商户信息
 * 前端控制器
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
    @Resource
    private MerchantService merchantService;

    @Resource
    private LinkmanService linkmanService;

    @Resource
    private AddressService addressService;



    @ApiOperation("商户列表")
    @GetMapping(value = "/getIdAndName")
    public ReturnJson getIdAndName() {
       return merchantService.getIdAndName();
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

    @PostMapping("/merchantInfo")
    @ApiOperation(value = "获取商户信息", notes = "获取商户信息", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "merchantId", value = "商户ID", required = true)
    })
    public ReturnJson merchantInfo(@NotBlank(message = "商户ID不能为空！") @RequestParam String merchantId) {
        return merchantService.merchantInfo(merchantId);
    }

    @PostMapping("/addOrUpdataLinkman")
    @ApiOperation(value = "添加或修改联系人", notes = "添加或修改联系人", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "linkman", value = "联系人信息", required = true, dataType = "Linkman")
    })
    public ReturnJson addOrUpdataLinkman(@RequestBody Linkman linkman) {
        return linkmanService.addOrUpdataLinkman(linkman);
    }

    @PostMapping("/updataLinkmanStatus")
    @ApiOperation(value = "停用或启用联系人", notes = "停用或启用联系人", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "linkmanId", value = "联系人ID", required = true),
            @ApiImplicitParam(name = "status", value = "联系人状态", required = true)
    })
    public ReturnJson updataStatus(@NotBlank(message = "联系人ID不能为空！") @RequestParam String linkmanId, @NotNull(message = "状态不能为空！") @RequestParam Integer status) {
        return linkmanService.updataStatus(linkmanId, status);
    }

    @PostMapping("/removeLinkmenById")
    @ApiOperation(value = "删除商户的联系人", notes = "删除商户的联系人", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "linkmanId", value = "联系人ID", required = true)
    })
    public ReturnJson removeLinkmenById(@NotBlank(message = "联系人ID不能为空") @RequestParam String linkmanId) {
        return linkmanService.removeLinkmenById(linkmanId);
    }

    @GetMapping("/getLinkmanAll")
    @ApiOperation(value = "查询商户的联系人", notes = "查询商户的联系人", httpMethod = "GET")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "merchantId", value = "商户ID", required = true)
    })
    public ReturnJson getLinkmanAll(@NotBlank(message = "商户ID不能为空！") @RequestParam String merchantId) {
        return linkmanService.getLinkmanAll(merchantId);
    }

    @GetMapping("/getAddressAll")
    @ApiOperation(value = "查询商户的快递地址信息", notes = "查询商户的快递地址信息", httpMethod = "GET")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "merchantId", value = "商户ID", required = true)
    })
    public ReturnJson getAddressAll(@NotBlank(message = "商户ID") @RequestParam String merchantId) {
        return addressService.getAddressAll(merchantId);
    }

    @PostMapping("/addOrUpdataAddress")
    @ApiOperation(value = "添加或修改快递地址", notes = "添加或修改快递地址", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "address", value = "快递地址信息", required = true, dataType = "Address")
    })
    public ReturnJson addOrUpdataAddress(@RequestBody Address address) {
        return addressService.addOrUpdataAddress(address);
    }

    @PostMapping("/updataAddressStatus")
    @ApiOperation(value = "修改快递地址状态", notes = "修改快递地址状态", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "addressId", value = "快递地址ID", required = true), @ApiImplicitParam(name = "status", value = "快递地址状态", required = true)
    })
    public ReturnJson updataAddressStatus(@NotBlank(message = "地址ID不能为空") @RequestParam String addressId, @NotNull(message = "地址状态不能为空") @RequestParam Integer status) {
        return addressService.updataAddressStatus(addressId, status);
    }

    @PostMapping("/removeAddressById")
    @ApiOperation(value = "删除快递地址", notes = "删除快递地址", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "addressId", value = "快递地址ID", required = true)
    })
    public ReturnJson removeAddressById(@NotBlank(message = "地址ID不能为空") @RequestParam String addressId) {
        return addressService.removeAddressById(addressId);
    }

}
