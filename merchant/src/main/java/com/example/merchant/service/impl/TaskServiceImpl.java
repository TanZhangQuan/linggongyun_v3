package com.example.merchant.service.impl;

import com.example.common.util.PageData;
import com.example.mybatis.entity.Task;
import com.example.mybatis.mapper.TaskDao;
import com.example.merchant.service.TaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 任务表 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskDao, Task> implements TaskService {

    @Resource
    private TaskDao taskDao;

    @Override
    public List<Task> selectList(PageData pageData) {
        return taskDao.selectList(pageData);
    }

    @Override
    public int count(PageData pageData) {
        return taskDao.count(pageData);
    }

    @Override
    public int delete(PageData pageData) {
        return taskDao.delete(pageData);
    }
}
