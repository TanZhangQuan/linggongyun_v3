package com.example.mybatis.mapper;

import com.example.mybatis.entity.Worker;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.po.WorkerPo;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * <p>
 * 创客表 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface WorkerDao extends BaseMapper<Worker> {

    /**
     * 已接单创客明细
     * @return
     */
    List<WorkerPo> getWorkerByTaskId(String taskId,RowBounds rowBounds);

    /**
     * 验收已接单创客明细
     * @param taskId
     * @param rowBounds
     * @return
     */
    List<WorkerPo> getCheckByTaskId(String taskId,RowBounds rowBounds);

}
