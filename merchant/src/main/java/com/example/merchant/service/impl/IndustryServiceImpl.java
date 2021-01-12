package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.IndustryService;
import com.example.mybatis.entity.Industry;
import com.example.mybatis.mapper.IndustryDao;
import com.example.mybatis.vo.IndustryVO;
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
    public ReturnJson getlist() {
        List<IndustryVO> industryList = industryDao.getlist();
        return ReturnJson.success(industryList);
    }
}
