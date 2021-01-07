package com.example.mybatis.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.dto.PlatformTaskDTO;
import com.example.mybatis.dto.TaskDTO;
import com.example.mybatis.dto.TaskListDTO;
import com.example.mybatis.entity.Task;
import com.example.mybatis.vo.AssociatedTasksVO;
import com.example.mybatis.vo.TaskInfoVO;
import com.example.mybatis.vo.TaskWorkerVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 任务表 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface TaskDao extends BaseMapper<Task> {

    IPage<Task> selectLists(Page page, @Param("taskListDto") TaskListDTO taskListDto, String userId);

    /**
     * 更具任务id删除任务
     *
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 根据任务id查询任务
     *
     * @param id
     * @return
     */
    Task setTaskById(String id);

    /**
     * 查找最新的taskcode
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
    int closeTask(String taskId);

    /**
     * 重新开单
     *
     * @param taskId
     * @return
     */
    int openTask(String taskId);

    /**
     * 平台端查询任务详情
     *
     * @param page
     * @param platformTaskDto
     * @return
     */
    IPage<Task> getPlatformTaskList(Page page, @Param("platformTaskDto") PlatformTaskDTO platformTaskDto);

    IPage<TaskInfoVO> setTask(Page page, @Param("industryType") String s);

    List<TaskWorkerVO> getAllTask();

    IPage<AssociatedTasksVO> getAssociatedTasks(Page page,String merchantId, Integer cooperateMode);
}
