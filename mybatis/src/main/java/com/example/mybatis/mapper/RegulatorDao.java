package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.entity.Regulator;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 监管部门 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-25
 */
public interface RegulatorDao extends BaseMapper<Regulator> {
    IPage<Regulator> selectRegulator(Page page, @Param("regulatorName") String regulatorName, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
