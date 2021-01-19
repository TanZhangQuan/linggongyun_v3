package com.example.merchant.controller.merchant;

import com.example.common.enums.UnionpayBankType;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.AddPaymentOrderManyDTO;
import com.example.merchant.dto.merchant.PaymentOrderManyPayDTO;
import com.example.merchant.dto.merchant.PaymentOrderMerchantDTO;
import com.example.merchant.exception.CommonException;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.CompanyUnionpayService;
import com.example.merchant.service.MerchantService;
import com.example.merchant.service.PaymentOrderManyService;
import com.example.mybatis.entity.Merchant;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 众包支付单信息
 * 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "商户端众包支付管理", tags = "商户端众包支付管理")
@RestController
@RequestMapping("/merchant/paymentOrderMany")
@Validated
public class PaymentOrderManyMerchantController {

    @Resource
    private MerchantService merchantService;

    @Resource
    private CompanyUnionpayService companyUnionpayService;

    @Resource
    private PaymentOrderManyService paymentOrderManyService;

    @PostMapping("/getPaymentOrderManyAll")
    @LoginRequired
    @ApiOperation(value = "查询众包订单", notes = "查询众包订单")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentOrderMerchantDto", value = "查询条件", required = true, dataType = "PaymentOrderMerchantDTO")
    })
    public ReturnJson getPaymentOrderManyAll(@ApiParam(hidden = true) @RequestAttribute("userId") String merchantId, @Valid @RequestBody PaymentOrderMerchantDTO paymentOrderMerchantDto) {
        return paymentOrderManyService.getPaymentOrderMany(merchantId, paymentOrderMerchantDto);
    }

    @GetMapping("/getPaymentOrderManyInfo")
    @ApiOperation(value = "查询众包支付订单详情", notes = "查询众包支付订单详情")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "支付订单ID", required = true)
    })
    public ReturnJson getPaymentOrderManyInfo(@NotBlank(message = "支付订单ID不能为空") @RequestParam(required = false) String id) {
        return paymentOrderManyService.getPaymentOrderManyInfo(id);
    }

    @PostMapping("/saveOrUpdata")
    @ApiOperation(value = "创建或修改众包支付订单", notes = "创建或修改众包支付订单")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "addPaymentOrderManyDto", value = "添加众包支付订单的内容", required = true, dataType = "AddPaymentOrderManyDTO")
    })
    @LoginRequired
    public ReturnJson saveOrUpdataPaymentOrderMany(@Valid @RequestBody AddPaymentOrderManyDTO addPaymentOrderManyDto, @ApiParam(hidden = true) @RequestAttribute("userId") String merchantId) throws CommonException {
        return paymentOrderManyService.saveOrUpdataPaymentOrderMany(addPaymentOrderManyDto, merchantId);
    }

    @PostMapping("/paymentOrderManyPay")
    @ApiOperation(value = "商户众包支付", notes = "商户众包支付")
    public ReturnJson paymentOrderManyPay(@Valid @RequestBody PaymentOrderManyPayDTO paymentOrderManyPayDTO) throws Exception {
        return paymentOrderManyService.paymentOrderManyPay(paymentOrderManyPayDTO);
    }

    @GetMapping("/queryCompanyUnionpayBalance")
    @ApiOperation(value = "查询商户相应银联的余额详情", notes = "查询商户相应银联的余额详情")
    @LoginRequired
    public ReturnJson queryCompanyUnionpayDetail(@ApiParam(value = "商户ID", hidden = true) @RequestAttribute(value = "userId") String merchantId,
                                                 @ApiParam(value = "服务商ID") @NotBlank(message = "请选择服务商") @RequestParam(required = false) String taxId,
                                                 @ApiParam(value = "银联银行类型") @NotNull(message = "请选择银联银行类型") @RequestParam(required = false) UnionpayBankType unionpayBankType) throws Exception {

        Merchant merchant = merchantService.getById(merchantId);
        if (merchant == null) {
            return ReturnJson.error("商户员工不存在");
        }

        return companyUnionpayService.queryCompanyUnionpayDetail(merchant.getCompanyId(), taxId, unionpayBankType);
    }

}
