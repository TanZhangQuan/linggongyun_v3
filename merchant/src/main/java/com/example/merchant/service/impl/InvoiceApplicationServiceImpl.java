package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.InvoiceApplicationService;
import com.example.mybatis.dto.ApplicationPaymentDto;
import com.example.mybatis.dto.InvoiceApplicationDto;
import com.example.mybatis.entity.InvoiceApplication;
import com.example.mybatis.mapper.InvoiceApplicationDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class InvoiceApplicationServiceImpl extends ServiceImpl<InvoiceApplicationDao, InvoiceApplication> implements InvoiceApplicationService {

    @Resource
    private InvoiceApplicationDao invoiceApplicationDao;

    @Override
    public int updateById(String applicationId, Integer state) {
        InvoiceApplication invoiceApplication=new InvoiceApplication();
        invoiceApplication.setApplicationState(state);
        invoiceApplication.setId(applicationId);
        return invoiceApplicationDao.updateById(invoiceApplication);
    }

    /**
     * 添加开票申请
     * @param invoiceApplicationDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson addInvApplication(InvoiceApplicationDto invoiceApplicationDto) {
        ReturnJson returnJson = new ReturnJson("添加失败", 300);

        IdentifierGenerator identifierGenerator = new DefaultIdentifierGenerator();
        invoiceApplicationDto.setId(identifierGenerator.nextId(new Object()).toString());
        int num = invoiceApplicationDao.addInvApplication(invoiceApplicationDto);
        if (num > 0) {
            String[] paymentOrderIds = invoiceApplicationDto.getPaymentOrderId().split(",");
            for (int i = 0; i < paymentOrderIds.length; i++) {
                ApplicationPaymentDto applicationPaymentDto = new ApplicationPaymentDto();
                applicationPaymentDto.setId(identifierGenerator.nextId(new Object()).toString());
                applicationPaymentDto.setInvoiceApplicationId(invoiceApplicationDto.getId());
                applicationPaymentDto.setPaymentOrderId(paymentOrderIds[i]);
                int nums = this.addApplicationPay(applicationPaymentDto);
                if (nums > 0) {
                    returnJson = new ReturnJson("添加成功", 200);
                }
            }
        }
        return returnJson;
    }

    /**
     * 支付&申请对应
     * @param applicationPaymentDto
     * @return
     */
    @Override
    public int addApplicationPay(ApplicationPaymentDto applicationPaymentDto) {
        return invoiceApplicationDao.addApplicationPay(applicationPaymentDto);
    }
}
