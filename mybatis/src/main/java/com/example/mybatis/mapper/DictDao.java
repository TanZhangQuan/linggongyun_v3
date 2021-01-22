package com.example.mybatis.mapper;

import com.example.mybatis.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.vo.DictVO;

import java.util.List;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author xjw
 * @since 2021-01-21
 */
public interface DictDao extends BaseMapper<Dict> {
    /**
     * 更具code 获取values
     *
     * @param code
     * @return
     */
    List<String> getDictValues(String code);

    /**
     * 获取字典code
     *
     * @return
     */
    List<DictVO> getDictValueAndKey();
}
