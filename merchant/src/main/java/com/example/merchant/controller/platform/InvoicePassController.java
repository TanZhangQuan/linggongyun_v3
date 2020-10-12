package com.example.merchant.controller.platform;


import com.example.common.util.ReturnJson;
import com.example.merchant.dto.MakerInvoiceDto;
import com.example.merchant.dto.MakerTotalInvoiceDto;
import com.example.merchant.service.InvoiceService;
import com.example.merchant.service.MakerInvoiceService;
import com.example.merchant.service.MakerTotalInvoiceService;
import com.example.mybatis.dto.AddInvoiceDto;
import com.example.mybatis.dto.TobeinvoicedDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 发票相关 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(tags = "平台端总包+分包发票",value = "平台端总包+分包发票")
@RestController
@RequestMapping("/platform/invoice")
public class InvoicePassController {

    private static Logger logger = LoggerFactory.getLogger(InvoicePassController.class);

    @Resource
    private InvoiceService invoiceService;
    @Autowired
    private MakerTotalInvoiceService makerTotalInvoiceService;
    @Autowired
    private MakerInvoiceService makerInvoiceService;

    @ApiOperation("总包开票，待开票")
    @PostMapping("/getPlaInvoiceList")
    public ReturnJson getPlaInvoiceList(TobeinvoicedDto tobeinvoicedDto){
        ReturnJson returnJson=new ReturnJson("查询失败",300);
        try{
            returnJson=invoiceService.getPlaInvoiceList(tobeinvoicedDto);
        }catch (Exception err){
            logger.error("出现异常错误",err);
        }
        return returnJson;
    }

    @ApiOperation("总包开票，详情数据")
    @PostMapping("/getPlaInvoiceInfo")
    public ReturnJson getPlaInvoiceInfo(String applicationID){
        ReturnJson returnJson=new ReturnJson("查询失败",300);
        try{
            returnJson=invoiceService.getPlaInvoiceInfo(applicationID);
        }catch (Exception err){
            logger.error("出现异常错误",err);
        }
        return returnJson;
    }


    @ApiOperation("总包开票")
    @PostMapping("/saveInvoice")
    public ReturnJson saveInvoice(AddInvoiceDto addInvoiceDto){
        ReturnJson returnJson=new ReturnJson("添加失败",300);
        try{
            returnJson=invoiceService.saveInvoice(addInvoiceDto);
        }catch (Exception err){
            logger.error("出现异常错误",err);
        }
        return returnJson;
    }

    @ApiOperation("总包开票，以开票")
    @PostMapping("/listInvoiceQuery")
    public ReturnJson listInvoiceQuery(TobeinvoicedDto tobeinvoicedDto){
        ReturnJson returnJson=new ReturnJson("查询失败",300);
        try{
            returnJson=invoiceService.getListInvoicequery(tobeinvoicedDto);
        }catch (Exception err){
            logger.error("出现异常错误",err);
        }
        return returnJson;
    }

    @ApiOperation("分包开票，待开票")
    @PostMapping("/listSubQuery")
    public ReturnJson listSubQuery(TobeinvoicedDto tobeinvoicedDto){
        ReturnJson returnJson=new ReturnJson("查询失败",300);
        try{
            returnJson=invoiceService.getListSubQuery(tobeinvoicedDto);
        }catch (Exception err){
            logger.error("出现异常错误",err);
        }
        return returnJson;
    }

    @ApiOperation("分包开票，开票清单明细信息")
    @PostMapping("/invoiceListQuery")
    public ReturnJson invoiceListQuery(String invoiceId,String companySNames,String platformServiceProviders){
        ReturnJson returnJson=new ReturnJson("查询失败",300);
        try{
            returnJson=invoiceService.getInvoiceListQuery(invoiceId, companySNames, platformServiceProviders);
        }catch (Exception err){
            logger.error("出现异常错误",err);
        }
        return returnJson;
    }

    @ApiOperation("汇总代开")
    @PostMapping("/saveOrUpdateMakerTotalInvoice")
    public ReturnJson saveOrUpdateMakerTotalInvoice(MakerTotalInvoiceDto makerTotalInvoiceDto){
        ReturnJson returnJson=new ReturnJson("查询失败",300);
        try{
            returnJson=makerTotalInvoiceService.saveOrUpdateMakerTotalInvoice(makerTotalInvoiceDto);
        }catch (Exception err){
            logger.error("出现异常错误",err);
        }
        return returnJson;
    }

    @ApiOperation("门征单开,开票清单明细信息")
    @PostMapping("/getPaymentInventory")
    public ReturnJson getPaymentInventory(String invoiceId){
        ReturnJson returnJson=new ReturnJson("查询失败",300);
        try{
            returnJson=makerInvoiceService.getPaymentInventory(invoiceId);
        }catch (Exception err){
            logger.error("出现异常错误",err);
        }
        return returnJson;
    }

    @ApiOperation("门征单开,开票如果未开票先上传发票进这里开一张发票")
    @PostMapping("/saveMakerInvoice")
    public ReturnJson saveMakerInvoice(MakerInvoiceDto makerInvoiceDto){
        ReturnJson returnJson=new ReturnJson("操作失败",300);
        try{
            returnJson=makerInvoiceService.saveMakerInvoice(makerInvoiceDto);
        }catch (Exception err){
            logger.error("出现异常错误",err);
        }
        return returnJson;
    }


}