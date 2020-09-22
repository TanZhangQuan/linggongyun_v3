package com.example.merchant.service.impl;


import com.example.common.util.ReturnJson;
import com.example.merchant.service.InvoiceCatalogService;
import com.example.mybatis.entity.InvoiceCatalog;
import com.example.mybatis.mapper.InvoiceCatalogDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InvoiceCatalogServiceImpl implements InvoiceCatalogService {
    @Resource
    private InvoiceCatalogDao invoiceCatalogDao;

    @Override
    public ReturnJson getListInv(String id) {
        ReturnJson returnJson=new ReturnJson("查询错误",300);
        List<InvoiceCatalog> list=invoiceCatalogDao.getListInv(id);
        if (list!=null&&list.size()>0){
            returnJson=new ReturnJson("查询成功",list,200);
        }
        return returnJson;
    }
}
