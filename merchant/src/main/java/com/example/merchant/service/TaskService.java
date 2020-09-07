package com.example.merchant.service;

import com.example.common.util.PageData;
import com.example.mybatis.entity.Task;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 任务表 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface TaskService extends IService<Task> {

    List<Task> selectList(PageData pageData);

    int count(PageData pageData);

    int delete (PageData pageData);
}
