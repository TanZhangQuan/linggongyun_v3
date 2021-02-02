package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.makerend.QueryMissionHall;
import com.example.merchant.dto.merchant.AddTaskDTO;
import com.example.merchant.exception.CommonException;
import com.example.mybatis.dto.PlatformTaskDTO;
import com.example.mybatis.dto.TaskDTO;
import com.example.mybatis.dto.TaskListDTO;
import com.example.mybatis.entity.Task;

/**
 * <p>
 * 任务表 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface TaskService extends IService<Task> {


    /**
     * 任务列表
     *
     * @param taskListDto
     * @return
     */
    ReturnJson selectList(TaskListDTO taskListDto, String userId);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    ReturnJson delete(String id);

    /**
     * 添加任务
     *
     * @param addTaskDto
     * @param userId
     * @return
     */
    ReturnJson saveTask(AddTaskDTO addTaskDto, String userId) throws CommonException;

    /**
     * 查看任务详情
     *
     * @param id
     * @return
     */
    ReturnJson setTaskById(String id);

    /**
     * 任务编码
     *
     * @return
     */
    String getTaskCode();

    /**
     * 关单
     *
     * @param taskId
     * @return
     */
    ReturnJson close(String taskId);

    /**
     * 重新开启任务
     *
     * @param taskId
     * @return
     */
    ReturnJson openTask(String taskId);

    /**
     * 平台任务列表
     *
     * @param platformTaskDto
     * @param userId
     * @return
     */
    ReturnJson getPlatformTaskList(PlatformTaskDTO platformTaskDto,String userId);

    /**
     * 平台任务添加
     *
     * @param taskDto
     * @return
     */
    ReturnJson savePlatformTask(TaskDTO taskDto);

    /**
     * 编辑任务
     *
     * @param taskDto
     * @return
     */
    ReturnJson updatePlatfromTask(TaskDTO taskDto);

    /**
     * 小程序任务大厅
     *
     * @param queryMissionHall
     * @return
     */
    ReturnJson setTask(QueryMissionHall queryMissionHall);

    /**
     * 查询任务详情
     *
     * @param taskId
     * @return
     */
    ReturnJson taskDetails(String taskId);

    /**
     * 抢单
     *
     * @param taskId
     * @param workerId
     * @return
     */
    ReturnJson orderGrabbing(String taskId, String workerId) throws CommonException;

    /**
     * 我的任务,某个创客的所有任务
     *
     * @param workerId 创客id
     * @param status   任务状态
     * @return
     */
    ReturnJson myTask(String workerId, String status);

    /**
     * 根据创客ID筛选创客未接单的任务
     *
     * @return
     */
    ReturnJson getAllTask(String workerId);

}
