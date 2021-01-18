package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.entity.CompanyWorker;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-23
 */
public interface CompanyWorkerDao extends BaseMapper<CompanyWorker> {

    Integer selectWorkerCount(List<String> merchantIds,Integer type);
}
