package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.MerchantWorkerService;
import com.example.merchant.service.TaskService;
import com.example.merchant.service.WorkerService;
import com.example.merchant.service.WorkerTaskService;
import com.example.mybatis.entity.MerchantWorker;
import com.example.mybatis.entity.Worker;
import com.example.mybatis.entity.WorkerTask;
import com.example.mybatis.mapper.MerchantWorkerDao;
import com.example.mybatis.mapper.WorkerDao;
import com.example.mybatis.po.WorkerPo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private WorkerDao workerDao;

    @Autowired
    private TaskService taskService;

    @Autowired
    private WorkerTaskService workerTaskService;

    @Autowired
    private MerchantWorkerService merchantWorkerService;

    @Autowired
    private MerchantWorkerDao merchantWorkerDao;

    /**
     * 分页查询商户下的所以创客
     * @param merchantId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public ReturnJson getWorkerAll(String merchantId, Integer page, Integer pageSize) {
        ReturnJson returnJson = new ReturnJson();
        Page<MerchantWorker> pageData = new Page<>(page,pageSize);
        QueryWrapper<MerchantWorker> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchant_id",merchantId);
        Page<MerchantWorker> merchantWorkerPage = merchantWorkerDao.selectPage(pageData, queryWrapper);
        List<MerchantWorker> records = merchantWorkerPage.getRecords();
        if (records != null && records.size() != 0){
            List<String> ids = new ArrayList<>();
            for (MerchantWorker merchantWorker : records){
                ids.add(merchantWorker.getWorkerId());
            }
            List<Worker> workers = workerDao.selectBatchIds(ids);
            returnJson.setData(workers);
        }
        returnJson.setCode(200);
        returnJson.setState("success");
        returnJson.setFinished(true);
        returnJson.setPageSize(pageSize);
        returnJson.setItemsCount((int) merchantWorkerPage.getTotal());
        returnJson.setPageCount((int)merchantWorkerPage.getPages());
        return returnJson;
    }

    /**
     * 按编号、姓名、手机号，查询该商户下的创客
     * @param merchantId
     * @param id
     * @param accountName
     * @param mobileCode
     * @return
     */
    @Override
    public ReturnJson getByIdAndAccountNameAndMobile(String merchantId, String id, String accountName, String mobileCode) {
        List<Worker> workers = workerDao.selectByIdAndAccountNameAndMobile(merchantId, id, accountName, mobileCode);
        return ReturnJson.success(workers);
    }

    /**
     * 查询创客的基本信息
     * @param id
     * @return
     */
    @Override
    public ReturnJson getWorkerInfo(String id) {
        Worker worker = workerDao.selectById(id);
        List<WorkerTask> workerTasks = workerTaskService.list(new QueryWrapper<WorkerTask>().eq("worker_id", worker.getId()));
        List ids = new ArrayList();
        System.out.println(workerTasks.size());
        for (WorkerTask workerTask : workerTasks) {
            ids.add(workerTask.getTaskId());
        }
        List list = taskService.listByIds(ids);
        ReturnJson returnJson = new ReturnJson();
        returnJson.setObj(worker);
        returnJson.setData(list);
        returnJson.setCode(200);
        returnJson.setState("success");
        return returnJson;
    }

    /**
     * 导入创客，当创客没注册时先注册创客（创客登录账号为手机号码，密码为身份证后6为数）
     * @param workers
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson saveWorker(List<Worker> workers, String merchantId) {
        List<MerchantWorker> merchantWorkers = new ArrayList<>();
        List<String> mobileCodes = new ArrayList<>();
        for (Worker worker : workers){
            mobileCodes.add(worker.getMobileCode());
            if (StringUtils.isBlank(worker.getId())){
                this.saveOrUpdate(worker);
            } else {
                MerchantWorker merchantWorker = new MerchantWorker();
                merchantWorker.setMerchantId(merchantId);
                merchantWorker.setWorkerId(worker.getId());
                merchantWorkers.add(merchantWorker);
            }
        }
        List<Worker> newworkers = workerDao.selectList(new QueryWrapper<Worker>().in("mobile_code",mobileCodes));
        for (Worker worker : newworkers) {
            MerchantWorker merchantWorker = new MerchantWorker();
            merchantWorker.setMerchantId(merchantId);
            merchantWorker.setWorkerId(worker.getId());
            merchantWorkers.add(merchantWorker);
        }
        boolean b = merchantWorkerService.saveBatch(merchantWorkers);
        if (b){
            return ReturnJson.success("导入成功！");
        }
        return ReturnJson.error("导入失败！");
    }



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
