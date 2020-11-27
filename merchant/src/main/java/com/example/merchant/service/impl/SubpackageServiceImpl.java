package com.example.merchant.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.SubpackageService;
import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.mapper.SubpackageDao;
import com.example.mybatis.vo.InvoiceDetailsVo;
import com.example.mybatis.vo.SubpackageInfoVo;
import com.example.mybatis.vo.SubpackageVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SubpackageServiceImpl implements SubpackageService {

    @Resource
    private SubpackageDao subpackageDao;

    /**
     * 汇总代开已开票
     *
     * @param tobeinvoicedDto
     * @return
     */
    @Override
    public ReturnJson getSummaryList(TobeinvoicedDto tobeinvoicedDto) {
        Page page = new Page(tobeinvoicedDto.getPageNo(), tobeinvoicedDto.getPageSize());
        IPage<SubpackageVo> list = subpackageDao.getSummaryList(page, tobeinvoicedDto);
        return ReturnJson.success(list);
    }

    /**
     * 汇总代开,支付信息，税价总和
     *
     * @param invoiceId
     * @return
     */
    @Override
    public ReturnJson getSummary(String invoiceId) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        SubpackageVo subpackage = subpackageDao.getSummary(invoiceId);
        if (subpackage != null) {
            returnJson = new ReturnJson("查询成功", subpackage, 200);
        }
        return returnJson;
    }

    /**
     * 汇总代开,发票信息
     *
     * @param invoiceId
     * @return
     */
    @Override
    public ReturnJson getSubpackageInfoById(String invoiceId) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        SubpackageInfoVo subpackageInfo = subpackageDao.getSubpackageInfoById(invoiceId);
        if (subpackageInfo != null) {
            returnJson = new ReturnJson("查询成功", subpackageInfo, 200);
        }
        return returnJson;
    }

    /**
     * 汇总代开,发票信息,创客到手明细
     *
     * @param invoiceId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public ReturnJson getListByInvoiceId(String invoiceId, Integer pageNo, Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        IPage<InvoiceDetailsVo> list = subpackageDao.getListById(page, invoiceId);
        return ReturnJson.success(list);

    }
}
