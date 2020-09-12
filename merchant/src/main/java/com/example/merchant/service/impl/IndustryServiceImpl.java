package com.example.merchant.service.impl;

import com.example.common.util.ReturnJson;
import com.example.merchant.service.MerchantService;
import com.example.mybatis.entity.Industry;
import com.example.mybatis.entity.Merchant;
import com.example.mybatis.mapper.IndustryDao;
import com.example.merchant.service.IndustryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 行业表 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class IndustryServiceImpl extends ServiceImpl<IndustryDao, Industry> implements IndustryService {

    @Resource
    private IndustryDao industryDao;

    @Resource
    private MerchantService merchantService;

    @Override
    public ReturnJson getlist(String oneLevel) {
        ReturnJson returnJson=new ReturnJson("查询失败",300);
        String currentUserId = "8982af2d0b664d6a9d62fba790746de2";
        Merchant merchant = merchantService.findByID(currentUserId);
        if (merchant != null){
            List<Industry> industryList=industryDao.getlist(oneLevel);
            returnJson=new ReturnJson("查询成功",industryList,200);
        }else {
            returnJson=new ReturnJson("请先登录",300);
        }
        return returnJson;
    }
}
