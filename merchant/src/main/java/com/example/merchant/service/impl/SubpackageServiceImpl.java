package com.example.merchant.service.impl;


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

    @Override
    public ReturnJson getSummaryList(TobeinvoicedDto tobeinvoicedDto) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        RowBounds rowBounds = new RowBounds((tobeinvoicedDto.getPageNo() - 1) * 9, 9);
        List<SubpackageVo> list = subpackageDao.getSummaryList(tobeinvoicedDto, rowBounds);
        if (list != null && list.size() > 0) {
            returnJson = new ReturnJson("查询成功", list, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getSummary(String invoiceId) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        SubpackageVo subpackage = subpackageDao.getSummary(invoiceId);
        if (subpackage != null) {
            returnJson = new ReturnJson("查询成功", subpackage, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getSubpackageInfoById(String invoiceId) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        SubpackageInfoVo subpackageInfo = subpackageDao.getSubpackageInfoById(invoiceId);
        if (subpackageInfo != null) {
            returnJson = new ReturnJson("查询成功", subpackageInfo, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getListByInvoiceId(String invoiceId, Integer pageNo) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        RowBounds rowBounds = new RowBounds((pageNo - 1) * 3, 3);
        List<InvoiceDetailsVo> list = subpackageDao.getListById(invoiceId, rowBounds);
        if (list != null && list.size() > 0) {
            returnJson = new ReturnJson("查询成功", list, 200);
        }
        return returnJson;

    }
}
