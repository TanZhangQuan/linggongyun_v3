package com.example.merchant.controller.merchant;

import com.example.common.util.ReturnJson;
import com.example.merchant.service.CrowdSourcingInvoiceService;
import com.example.merchant.service.PaymentOrderManyService;
import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.entity.ApplicationCrowdSourcing;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    private static Logger logger = LoggerFactory.getLogger(CrowdSourcingMerchantController.class);

//    @RequiresRoles("admin")
    @RequiresPermissions("crowd_sourcing_invoice")
    @ApiOperation("众包发票列表")
    @PostMapping(value = "/getListCSIByID")
    public ReturnJson getListCSIByID(TobeinvoicedDto tobeinvoicedDto) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        try {
            returnJson = paymentOrderManyService.getListCSIByID(tobeinvoicedDto);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }

    @RequiresRoles("caiwu")
    @ApiOperation("众包支付信息")
    @GetMapping(value = "/getPayOrderManyById")
    public ReturnJson getPayOrderManyById(String id) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        try {
            returnJson = paymentOrderManyService.getPayOrderManyById(id);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }

    @ApiOperation("众包支付信息,创客支付明细")
    @GetMapping(value = "/getInvoiceDetailsByPayId")
    public ReturnJson getInvoiceDetailsByPayId(String id,Integer pageNo) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        try {
            returnJson = paymentOrderManyService.getInvoiceDetailsByPayId(id,pageNo);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }

    @ApiOperation("众包支付信息,申请开票")
    @GetMapping(value = "/addCrowdSourcingInvoice")
    public ReturnJson addCrowdSourcingInvoice(ApplicationCrowdSourcing applicationCrowdSourcing) {
        ReturnJson returnJson = new ReturnJson("添加失败", 300);
        try {
            returnJson = crowdSourcingInvoiceService.addCrowdSourcingInvoice(applicationCrowdSourcing);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }

    @ApiOperation("众包支付信息,已开票页面")
    @GetMapping(value = "/getCrowdSourcingInfo")
    public ReturnJson getCrowdSourcingInfo(TobeinvoicedDto tobeinvoicedDto) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        try {
            returnJson = crowdSourcingInvoiceService.getCrowdSourcingInfo(tobeinvoicedDto);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }


    @ApiOperation("众包发票列表,发票信息")
    @PostMapping(value = "/getInvoiceById")
    public ReturnJson getInvoiceById(String csiId) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        try {
            returnJson = crowdSourcingInvoiceService.getInvoiceById(csiId);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }


}
