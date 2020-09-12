package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.Worker;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatis.po.WorkerPo;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 创客表 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface WorkerService extends IService<Worker> {

    ReturnJson getWorkerByTaskId(String taskId, Integer offset);

    ReturnJson getCheckByTaskId(String taskId, Integer offset);
}
