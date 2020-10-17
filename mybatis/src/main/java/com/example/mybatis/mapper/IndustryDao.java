package com.example.mybatis.mapper;

import com.example.mybatis.entity.Industry;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.vo.IndustryVo;

import java.util.List;

/**
 * <p>
 * 行业表 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface IndustryDao extends BaseMapper<Industry> {

    List<IndustryVo> getlist();
}
