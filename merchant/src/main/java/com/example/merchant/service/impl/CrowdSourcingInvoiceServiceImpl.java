package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.CrowdSourcingInvoiceService;
import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.entity.ApplicationCrowdSourcing;
import com.example.mybatis.mapper.CrowdSourcingInvoiceDao;
import com.example.mybatis.vo.CrowdSourcingInfoVo;
import com.example.mybatis.vo.InvoiceInformationVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CrowdSourcingInvoiceServiceImpl implements CrowdSourcingInvoiceService {

    @Resource
    private CrowdSourcingInvoiceDao crowdSourcingInvoiceDao;

    /**
     * 众包开票申请
     *
     * @param applicationCrowdSourcing
     * @return
     */
    @Override
    public ReturnJson addCrowdSourcingInvoice(ApplicationCrowdSourcing applicationCrowdSourcing) {
        ReturnJson returnJson = new ReturnJson("添加错误", 300);
        IdentifierGenerator identifierGenerator = new DefaultIdentifierGenerator();
        applicationCrowdSourcing.setId(identifierGenerator.nextId(new Object()).toString());
        int num = crowdSourcingInvoiceDao.addCrowdSourcingInvoice(applicationCrowdSourcing);
        if (num > 0) {
            returnJson = new ReturnJson("添加成功", 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getCrowdSourcingInfo(TobeinvoicedDto tobeinvoicedDto) {
        ReturnJson returnJson = new ReturnJson("查询成功", 300);
        RowBounds rowBounds = new RowBounds((tobeinvoicedDto.getPageNo() - 1) * 9, 9);
        List<CrowdSourcingInfoVo> vos = crowdSourcingInvoiceDao.getCrowdSourcingInfo(tobeinvoicedDto, rowBounds);
        if (vos != null){
            returnJson = new ReturnJson("查询成功", vos,300);
        }
            return returnJson;
    }

    @Override
    public ReturnJson getInvoiceById(String csiId) {
        ReturnJson returnJson = new ReturnJson("查询成功", 300);
        InvoiceInformationVo vo = crowdSourcingInvoiceDao.getInvoiceById(csiId);
        if (vo != null){
            returnJson = new ReturnJson("查询成功",vo,300);
        }
        return returnJson;
    }
}
