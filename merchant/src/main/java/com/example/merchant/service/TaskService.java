package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.mybatis.dto.PlatformTaskDto;
import com.example.mybatis.dto.TaskDto;
import com.example.mybatis.dto.TaskListDto;
import com.example.mybatis.entity.Task;
import org.apache.ibatis.session.RowBounds;

/**
 * <p>
 * 任务表 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface TaskService extends IService<Task> {


    int count(TaskListDto taskListDto);

    ReturnJson selectList(TaskListDto taskListDto);

    ReturnJson delete (String id);

    ReturnJson saveTask(TaskDto taskDto,String userId);

    ReturnJson setTaskById(String id);

    String getTaskCode();

    ReturnJson close(String taskId);

    ReturnJson openTask(String taskId);

    ReturnJson getPlatformTaskList(PlatformTaskDto platformTaskDto);

    ReturnJson savePlatformTask(TaskDto taskDto);

    ReturnJson updatePlatfromTask(TaskDto taskDto);

    ReturnJson setTask(String industryType);

    ReturnJson taskDetails(String taskId);

    ReturnJson orderGrabbing(String taskId,String workerId);

    ReturnJson myTask(String workerId,String status);

    /**
     * 获取任务列表
     * @return
     */
    ReturnJson getindustryType();

}
