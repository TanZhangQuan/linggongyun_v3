package com.example.merchant.service.impl;

import com.example.common.util.PageData;
import com.example.mybatis.entity.Industry;
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

    @Override
    public List<Industry> getlist(PageData pageData) {
        return industryDao.getlist(pageData);
    }
}
