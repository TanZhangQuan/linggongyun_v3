package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.UpdateApplication;
import com.example.merchant.service.InvoiceApplicationService;
import com.example.merchant.vo.merchant.GoApplicationInvoiceVO;
import com.example.merchant.vo.merchant.InvoiceApplicationVO;
import com.example.merchant.vo.merchant.QueryApplicationInvoiceVO;
import com.example.mybatis.dto.ApplicationPaymentDTO;
import com.example.mybatis.dto.InvoiceApplicationDTO;
import com.example.mybatis.entity.InvoiceApplication;
import com.example.mybatis.entity.PaymentOrder;
import com.example.mybatis.mapper.InvoiceApplicationDao;
import com.example.mybatis.mapper.MerchantDao;
import com.example.mybatis.mapper.PaymentOrderDao;
import com.example.mybatis.mapper.TaxDao;
import com.example.mybatis.vo.BillingInfoVO;
import com.example.mybatis.vo.PaymentOrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceApplicationServiceImpl extends ServiceImpl<InvoiceApplicationDao, InvoiceApplication> implements InvoiceApplicationService {

    @Resource
    private InvoiceApplicationDao invoiceApplicationDao;

    @Resource
    private PaymentOrderDao paymentOrderDao;

    @Resource
    private MerchantDao merchantDao;

    @Resource
    private TaxDao taxDao;

    @Override
    public int updateById(String applicationId, Integer state) {
        InvoiceApplication invoiceApplication = new InvoiceApplication();
        invoiceApplication.setApplicationState(state);
        invoiceApplication.setId(applicationId);
        return invoiceApplicationDao.updateById(invoiceApplication);
    }

    /**
     * 添加开票申请
     *
     * @param invoiceApplicationDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson addInvApplication(InvoiceApplicationDTO invoiceApplicationDto) {
        ReturnJson returnJson = new ReturnJson("添加失败", 300);
        InvoiceApplication invoiceApplication = new InvoiceApplication();
        IdentifierGenerator identifierGenerator = new DefaultIdentifierGenerator();
        BeanUtils.copyProperties(invoiceApplicationDto, invoiceApplication);
        invoiceApplication.setApplicationState(1);
        int num = invoiceApplicationDao.insert(invoiceApplication);
        if (num > 0) {
            String[] paymentOrderIds = invoiceApplicationDto.getPaymentOrderId().split(",");
            for (int i = 0; i < paymentOrderIds.length; i++) {
                ApplicationPaymentDTO applicationPaymentDto = new ApplicationPaymentDTO();
                applicationPaymentDto.setId(identifierGenerator.nextId(new Object()).toString());
                applicationPaymentDto.setInvoiceApplicationId(invoiceApplication.getId());
                applicationPaymentDto.setPaymentOrderId(paymentOrderIds[i]);
                this.addApplicationPay(applicationPaymentDto);
                PaymentOrder paymentOrder = paymentOrderDao.selectById(paymentOrderIds[i]);
                paymentOrder.setIsInvoice(1);
                paymentOrderDao.updateById(paymentOrder);
            }

            return ReturnJson.success("添加成功", 200);
        }
        return returnJson;
    }

    @Override
    public int addApplicationPay(ApplicationPaymentDTO applicationPaymentDto) {
        return invoiceApplicationDao.addApplicationPay(applicationPaymentDto);
    }

    @Override
    public ReturnJson goInvApplication(String payIds, String merchantId) {
        GoApplicationInvoiceVO goApplicationInvoiceVo = new GoApplicationInvoiceVO();
        String[] paymentOrderIds = payIds.split(",");
        PaymentOrder paymentOrderOne = paymentOrderDao.selectById(paymentOrderIds[0]);
        List<PaymentOrderVO> paymentOrderVOList = new ArrayList<>();
        for (int i = 0; i < paymentOrderIds.length; i++) {
            PaymentOrderVO paymentOrder = paymentOrderDao.getPaymentOrderById(paymentOrderIds[i]);
            paymentOrderVOList.add(paymentOrder);
        }
        for (int i = 0; i < paymentOrderVOList.size(); i++) {
            if (!paymentOrderVOList.get(0).getPlatformServiceProvider().equals(paymentOrderVOList.get(i).getPlatformServiceProvider())) {
                return ReturnJson.error("合并开票失败，合并开票必须选择同一个服务商");
            }
        }
        goApplicationInvoiceVo.setPaymentOrderVoList(paymentOrderVOList);
        List<BillingInfoVO> billingInfoVOList = new ArrayList<>();
        for (int i = 0; i < paymentOrderIds.length; i++) {
            BillingInfoVO billingInfo = paymentOrderDao.getBillingInfo(paymentOrderIds[i]);
            billingInfoVOList.add(billingInfo);
        }
        goApplicationInvoiceVo.setBillingInfoVoList(billingInfoVOList);
        goApplicationInvoiceVo.setBuyerVo(merchantDao.getBuyerById(paymentOrderOne.getCompanyId()));
        goApplicationInvoiceVo.setSellerVo(taxDao.getSellerById(paymentOrderOne.getTaxId()));
        return ReturnJson.success(goApplicationInvoiceVo);
    }

    @Override
    public ReturnJson updateApplication(UpdateApplication updateApplication, String userId) {
        InvoiceApplication invoiceApplication = invoiceApplicationDao.selectById(updateApplication.getId());
        if (invoiceApplication != null) {
            BeanUtils.copyProperties(updateApplication, invoiceApplication);
            invoiceApplication.setApplicationPerson(userId);
            invoiceApplicationDao.updateById(invoiceApplication);
            return ReturnJson.success("修改成功");
        }
        return ReturnJson.error("没有对应的开票申请！");
    }

    @Override
    public ReturnJson queryApplicationInfo(String applicationId, String userId) {
        QueryApplicationInvoiceVO queryApplicationInvoiceVo = new QueryApplicationInvoiceVO();
        List<PaymentOrderVO> paymentOrderVOList = paymentOrderDao.queryPaymentOrderInfo(applicationId);
        queryApplicationInvoiceVo.setPaymentOrderVoList(paymentOrderVOList);
        List<BillingInfoVO> billingInfoVOList = new ArrayList<>();
        for (int i = 0; i < paymentOrderVOList.size(); i++) {
            BillingInfoVO billingInfo = paymentOrderDao.getBillingInfo(paymentOrderVOList.get(i).getId());
            billingInfoVOList.add(billingInfo);
        }
        queryApplicationInvoiceVo.setBillingInfoVoList(billingInfoVOList);
        PaymentOrder paymentOrderOne = paymentOrderDao.selectById(paymentOrderVOList.get(0).getId());
        queryApplicationInvoiceVo.setBuyerVo(merchantDao.getBuyerById(paymentOrderOne.getCompanyId()));
        queryApplicationInvoiceVo.setSellerVo(taxDao.getSellerById(paymentOrderOne.getTaxId()));
        InvoiceApplication invoiceApplication=invoiceApplicationDao.selectById(applicationId);
        InvoiceApplicationVO invoiceApplicationVo=new InvoiceApplicationVO();
        BeanUtils.copyProperties(invoiceApplication,invoiceApplicationVo);
        queryApplicationInvoiceVo.setInvoiceApplicationVo(invoiceApplicationVo);
        return ReturnJson.success(queryApplicationInvoiceVo);
    }

}
