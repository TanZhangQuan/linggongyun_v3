package com.example.merchant.controller.merchant;

import com.example.common.util.ReturnJson;
import com.example.merchant.service.CrowdSourcingInvoiceService;
import com.example.merchant.service.PaymentOrderManyService;
import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.entity.ApplicationCrowdSourcing;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "商户端众包发票关操作接口", tags = {"商户端众包发票关操作接口"})
@RestController
@RequestMapping("/merchant/crowdSourcing")
public class CrowdSourcingMerchantController {

    @Resource
    private PaymentOrderManyService paymentOrderManyService;

    @Resource
    private CrowdSourcingInvoiceService crowdSourcingInvoiceService;

    @RequiresPermissions("crowd_sourcing_invoice")
    @ApiOperation("众包发票列表")
    @PostMapping(value = "/getListCSIByID")
    public ReturnJson getListCSIByID(TobeinvoicedDto tobeinvoicedDto) {
        return paymentOrderManyService.getListCSIByID(tobeinvoicedDto);
    }

    @RequiresPermissions("crowd_sourcing_invoice")
    @ApiOperation("众包支付信息")
    @GetMapping(value = "/getPayOrderManyById")
    public ReturnJson getPayOrderManyById(String id) {
        return paymentOrderManyService.getPayOrderManyById(id);
    }

    @ApiOperation("众包支付信息,创客支付明细")
    @GetMapping(value = "/getInvoiceDetailsByPayId")
    public ReturnJson getInvoiceDetailsByPayId(String id, Integer pageNo,Integer pageSize) {
        return paymentOrderManyService.getInvoiceDetailsByPayId(id, pageNo,pageSize);
    }

    @ApiOperation("众包支付信息,申请开票")
    @GetMapping(value = "/addCrowdSourcingInvoice")
    public ReturnJson addCrowdSourcingInvoice(ApplicationCrowdSourcing applicationCrowdSourcing) {
        return crowdSourcingInvoiceService.addCrowdSourcingInvoice(applicationCrowdSourcing);
    }

    @ApiOperation("众包支付信息,已开票页面")
    @GetMapping(value = "/getCrowdSourcingInfo")
    public ReturnJson getCrowdSourcingInfo(TobeinvoicedDto tobeinvoicedDto) {
        return crowdSourcingInvoiceService.getCrowdSourcingInfo(tobeinvoicedDto);
    }

    @ApiOperation("众包发票列表,发票信息")
    @PostMapping(value = "/getInvoiceById")
    public ReturnJson getInvoiceById(String csiId) {
        return crowdSourcingInvoiceService.getInvoiceById(csiId);
    }


}
