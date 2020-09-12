package com.example.merchant.service.impl;

import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.Worker;
import com.example.mybatis.mapper.WorkerDao;
import com.example.merchant.service.WorkerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatis.po.WorkerPo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 创客表 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class WorkerServiceImpl extends ServiceImpl<WorkerDao, Worker> implements WorkerService {

    @Resource
    private WorkerDao workerDao;


    @Override
    public ReturnJson getWorkerByTaskId(String taskId, Integer offset) {
        ReturnJson returnJson=new ReturnJson("查询失败",300);
        RowBounds rowBounds= new RowBounds(offset*9,9);
        List<WorkerPo> poList=workerDao.getWorkerByTaskId(taskId, rowBounds);
        if (poList!=null){
            returnJson=new ReturnJson("查询成功",poList,200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getCheckByTaskId(String taskId, Integer offset) {
        ReturnJson returnJson=new ReturnJson("验收查询失败",300);
        RowBounds rowBounds= new RowBounds(offset*9,9);
        List<WorkerPo> poList=workerDao.getCheckByTaskId(taskId, rowBounds);
        if (poList!=null){
            returnJson=new ReturnJson("验收查询成功",poList,200);
        }
        return returnJson;
    }
}
