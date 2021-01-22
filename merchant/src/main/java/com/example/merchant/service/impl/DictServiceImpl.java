package com.example.merchant.service.impl;

import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.Dict;
import com.example.mybatis.mapper.DictDao;
import com.example.merchant.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatis.vo.DictVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author xjw
 * @since 2021-01-21
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictDao, Dict> implements DictService {

    @Resource
    private DictDao dictDao;

    @Override
    public ReturnJson queryDictValues(String code) {
        List<String> list = dictDao.getDictValues(code);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson queryDictValueAndKey() {
        List<DictVO> list = dictDao.getDictValueAndKey();
        return ReturnJson.success(list);
    }
}
