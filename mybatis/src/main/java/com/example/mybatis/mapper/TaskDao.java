package com.example.mybatis.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.dto.PlatformTaskDto;
import com.example.mybatis.dto.TaskDto;
import com.example.mybatis.dto.TaskListDto;
import com.example.mybatis.entity.Task;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import javax.validation.constraints.Pattern;
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

    int count(TaskListDto taskListDto);

    List<Task> selectLists(TaskListDto taskListDto, RowBounds rowBounds);

    //更具任务id删除任务
    int delete(String id);

    //添加任务信息
    int addTask(TaskDto TaskDto);

    //根据任务id查询任务
    Task setTaskById(String id);

    //查找最新的taskcode
    String getTaskCode();

    //关单
    int closeTask(String taskId);

    //重新开单
    int openTask(String taskId);

    //平台端查询任务详情
    List<Task> getPlatformTaskList(PlatformTaskDto platformTaskDto, RowBounds rowBounds);

    //平台端添加任务信息
    int addPlatformTask(TaskDto TaskDto);

    List<Task> setTask(@Param("merchantId") String merchantId, @Param("industryType")String industryType);
}
