package com.example.merchant.controller.platform;


import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.*;
import com.example.merchant.exception.CommonException;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.AddressService;
import com.example.merchant.service.LinkmanService;
import com.example.merchant.service.MerchantService;
import com.example.mybatis.entity.Address;
import com.example.mybatis.entity.Linkman;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
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
@Api(value = "平台端商户相关操作接口", tags = {"平台端商户相关操作接口"})
@RestController
@RequestMapping("/platform/merchant")
@Validated
public class MerchantPaasController {

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

    @PostMapping("/getMerchantList")
    @LoginRequired
    @ApiOperation(value = "获取已签约的商户", notes = "获取已签约的商户", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "merchantId", value = "商户ID"),
            @ApiImplicitParam(name = "merchantName", value = "商户名称"),
            @ApiImplicitParam(name = "linkMobile", value = "商户的联系人电话"),
            @ApiImplicitParam(name = "pageNo", value = "页数"),
            @ApiImplicitParam(name = "pageSize", value = "一页的条数")
    })
    public ReturnJson getMerchantList(@RequestAttribute("userId") @ApiParam(hidden = true) String managersId,  String merchantId, String merchantName, String linkMobile, @RequestParam(defaultValue = "1") Integer pageNo,
                                      @RequestParam(defaultValue = "10") Integer pageSize) throws CommonException {
        return merchantService.getMerchantList(managersId, merchantId, merchantName, linkMobile, 1, pageNo, pageSize);
    }

    @PostMapping("/getAuditMerchantList")
    @LoginRequired
    @ApiOperation(value = "获取待审核的商户", notes = "获取待审核的商户", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "merchantId", value = "商户ID", paramType = "query"),
            @ApiImplicitParam(name = "merchantName", value = "商户名称", paramType = "query"),
            @ApiImplicitParam(name = "linkMobile", value = "商户的联系人电话", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "页数"),
            @ApiImplicitParam(name = "pageSize", value = "一页的条数")
    })
    public ReturnJson getAuditMerchantList(@RequestAttribute("userId") @ApiParam(hidden = true) String managersId, String merchantId, String merchantName, String linkMobile, @RequestParam(defaultValue = "1") Integer pageNo,
                                           @RequestParam(defaultValue = "10") Integer pageSize) throws CommonException {
        return merchantService.getMerchantList(managersId, merchantId, merchantName, linkMobile, 0, pageNo, pageSize);
    }

    @PostMapping("/auditMerchant")
    @ApiOperation(value = "审核商户", notes = "审核商户", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "companyId", value = "企业ID", required = true)
    })
    public ReturnJson auditMerchant(@NotBlank(message = "商户公司ID不能为空！") @RequestParam(required = false) String companyId) {
        return merchantService.auditMerchant(companyId);
    }

    @PostMapping("/removeMerchant")
    @ApiOperation(value = "删除商户", notes = "删除商户", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "companyId", value = "商户公司ID", required = true)
    })
    public ReturnJson removeMerchant(@NotBlank(message = "商户公司ID不能为空！") @RequestParam(required = false) String companyId) {
        return merchantService.removeMerchant(companyId);
    }

    @PostMapping("/merchantInfo")
    @ApiOperation(value = "获取商户信息", notes = "获取商户信息", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "merchantId", value = "商户ID", required = true)
    })
    public ReturnJson merchantInfo(@NotBlank(message = "商户ID不能为空！") @RequestParam(required = false) String merchantId) {
        return merchantService.merchantInfoPaas(merchantId);
    }

    @PostMapping("/addMerchant")
    @ApiOperation(value = "添加商户", notes = "添加商户", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "companyDto", value = "商户ID", required = true, dataType = "CompanyDto")
    })
    public ReturnJson addMerchant(@Valid @RequestBody CompanyDto companyDto) throws Exception {
        return merchantService.addMerchant(companyDto);
    }

    @PostMapping("/getMerchantPaymentList")
    @ApiOperation(value = "获取商户的支付列表", notes = "获取商户的支付列表", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "merchantId", value = "商户ID", required = true),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页的条数", required = true)
    })
    public ReturnJson getMerchantPaymentList(@NotBlank(message = "商户ID不能为空！") @RequestParam(required = false) String merchantId, @RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        return merchantService.getMerchantPaymentList(merchantId, pageNo, pageSize);
    }

    @PostMapping("/getMerchantPaymentInfo")
    @ApiOperation(value = "获取商户的支付详情", notes = "获取商户的支付详情", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentOrderId", value = "支付订单ID", required = true),
            @ApiImplicitParam(name = "packgeStatus", value = "合作类型0总包，1众包", required = true)
    })
    public ReturnJson getMerchantPaymentInfo(@NotBlank(message = "支付订单ID不能为空") @RequestParam(required = false) String paymentOrderId, @NotNull(message = "合作类型不能为空！") @RequestParam(required = false) Integer packgeStatus) {
        return merchantService.getMerchantPaymentInfo(paymentOrderId, packgeStatus);
    }

    @PostMapping("/getMerchantPaymentInventory")
    @ApiOperation(value = "获取商户的支付清单列表", notes = "获取商户的支付清单列表", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentOrderId", value = "支付订单ID", required = true),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页的条数", required = true)
    })
    public ReturnJson getMerchantPaymentInventory(@NotBlank(message = "商户ID不能为空！") @RequestParam(required = false) String paymentOrderId, @RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        return merchantService.getMerchantPaymentInventory(paymentOrderId, pageNo, pageSize);
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
    public ReturnJson updataStatus(@NotBlank(message = "联系人ID不能为空！") @RequestParam(required = false) String linkmanId, @NotNull(message = "状态不能为空！") @RequestParam(required = false) Integer status) {
        return linkmanService.updataStatus(linkmanId, status);
    }

    @PostMapping("/removeLinkmenById")
    @ApiOperation(value = "删除商户的联系人", notes = "删除商户的联系人", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "linkmanId", value = "联系人ID", required = true)
    })
    public ReturnJson removeLinkmenById(@NotBlank(message = "联系人ID不能为空") @RequestParam(required = false) String linkmanId) {
        return linkmanService.removeLinkmenById(linkmanId);
    }

    @GetMapping("/getLinkmanAll")
    @ApiOperation(value = "查询商户的联系人", notes = "查询商户的联系人", httpMethod = "GET")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "merchantId", value = "商户ID", required = true)
    })
    public ReturnJson getLinkmanAll(@NotBlank(message = "商户ID不能为空！") @RequestParam(required = false) String merchantId) {
        return linkmanService.getLinkmanAll(merchantId);
    }


    @GetMapping("/getAddressAll")
    @ApiOperation(value = "查询商户的快递地址信息", notes = "查询商户的快递地址信息", httpMethod = "GET")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "merchantId", value = "商户ID", required = true)
    })
    public ReturnJson getAddressAll(@NotBlank(message = "商户ID") @RequestParam(required = false) String merchantId) {
        return addressService.getAddressAll(merchantId);
    }

    @PostMapping("/addOrUpdataAddress")
    @ApiOperation(value = "添加或修改快递地址", notes = "添加或修改快递地址", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "address", value = "快递地址信息", required = true, dataType = "Address")
    })
    public ReturnJson addOrUpdataAddress(@RequestBody AddressDto addressDto, String merchantId) {
        return addressService.addOrUpdataAddress(addressDto,merchantId);
    }

    @PostMapping("/updataAddressStatus")
    @ApiOperation(value = "修改快递地址状态", notes = "修改快递地址状态", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "addressId", value = "快递地址ID", required = true),
            @ApiImplicitParam(name = "status", value = "快递地址状态", required = true)
    })
    public ReturnJson updataAddressStatus(@NotBlank(message = "地址ID不能为空") @RequestParam(required = false) String addressId, @NotNull(message = "地址状态不能为空") @RequestParam(required = false) Integer status) {
        return addressService.updataAddressStatus(addressId, status);
    }

    @PostMapping("/removeAddressById")
    @ApiOperation(value = "删除快递地址", notes = "删除快递地址", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "addressId", value = "快递地址ID", required = true)
    })
    public ReturnJson removeAddressById(@NotBlank(message = "地址ID不能为空") @RequestParam(required = false) String addressId) {
        return addressService.removeAddressById(addressId);
    }

    @PostMapping("/updataPassWord")
    @ApiOperation(value = "修改或忘记密码", notes = "修改或忘记密码", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "loginMobile", value = "登录用的手机号码", required = true),
            @ApiImplicitParam(name = "checkCode", value = "验证码", required = true),
            @ApiImplicitParam(name = "newPassWord", value = "新密码", required = true)
    })
    public ReturnJson updataPassWord(@NotBlank(message = "手机号不能为空") @RequestParam(required = false) String loginMobile, @NotBlank(message = "验证码不能为空") @RequestParam(required = false) String checkCode, @NotBlank(message = "新密码不能为空") @RequestParam(required = false) String newPassWord) {
        return merchantService.updataPassWord(loginMobile, checkCode, newPassWord);
    }

    @GetMapping("/queryAgent")
    @ApiOperation(value = "代理商列表", notes = "代理商列表", httpMethod = "GET")
    public ReturnJson queryAgent() {
        return merchantService.queryAgent();
    }

    @GetMapping("/queryCompanyInfoById")
    @ApiOperation(value = "公司的信息", notes = "公司的信息", httpMethod = "GET")
    public ReturnJson queryCompanyInfoById(String merchantId) {
        return merchantService.queryCompanyInfoById(merchantId);
    }

    @PostMapping("/updateCompanyInfo")
    @ApiOperation(value = "修改公司的信息", notes = "修改或忘记密码", httpMethod = "POST")
    public ReturnJson updateCompanyInfo(@RequestBody UpdateCompanyDto updateCompanyDto) {
        return merchantService.updateCompanyInfo(updateCompanyDto);
    }
}
