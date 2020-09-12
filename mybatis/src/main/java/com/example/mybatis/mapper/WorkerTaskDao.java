package com.example.mybatis.mapper;

import com.example.mybatis.dto.WorkerTaskDto;
import com.example.mybatis.entity.WorkerTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface WorkerTaskDao extends BaseMapper<WorkerTask> {

    //派单给指定的创客
    int addWorkerTask(WorkerTask workerTask);

    //剔除创客
    int eliminateWorker(String workerId);

    //修改验收金额
    int updateCheckMoney(Double money,String id);
}
