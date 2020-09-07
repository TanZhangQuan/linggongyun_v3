package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.example.common.util.PageData;
import com.example.mybatis.entity.Task;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 任务表 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface TaskDao extends BaseMapper<Task> {

    int count(PageData pageData);

    List<Task> selectList(PageData pageData);

    int delete(PageData pageData);

}
