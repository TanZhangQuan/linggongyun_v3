package com.example.paas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.MerchantWorker;
import com.example.mybatis.entity.Worker;
import com.example.mybatis.entity.WorkerTask;
import com.example.mybatis.mapper.WorkerDao;
import com.example.mybatis.po.WorekerPaymentListPo;
import com.example.mybatis.po.WorkerPo;
import com.example.paas.service.MerchantWorkerService;
import com.example.paas.service.TaskService;
import com.example.paas.service.WorkerService;
import com.example.paas.service.WorkerTaskService;
import com.example.paas.util.AcquireMerchantID;
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
    private AcquireMerchantID acquireMerchantID;

    /**
     * 分页查询管理人员下的所以创客
     * @param managersId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public ReturnJson getWorkerAll(String managersId, Integer page, Integer pageSize) {
        List<String> merchantIds = acquireMerchantID.getMerchantIds(managersId);
        merchantIds.add(managersId);
        Page<Worker> workerPage = new Page<>(page,pageSize);
        IPage<Worker> workerIPage = workerDao.selectWorkerAll(workerPage, merchantIds);
        ReturnJson returnJson = new ReturnJson();
        returnJson.setCode(200);
        returnJson.setState("success");
        returnJson.setFinished(true);
        returnJson.setPageSize(pageSize);
        returnJson.setItemsCount((int) workerIPage.getTotal());
        returnJson.setPageCount((int)workerIPage.getPages());
        returnJson.setData(workerIPage.getRecords());
        return returnJson;
    }

    @Override
    public ReturnJson getWorkerAllNot(String managersId, Integer page, Integer pageSize) {
        List<String> merchantIds = acquireMerchantID.getMerchantIds(managersId);
        merchantIds.add(managersId);
        Page<Worker> workerPage = new Page<>(page,pageSize);
        IPage<Worker> workerIPage = workerDao.selectWorkerAllNot(workerPage, merchantIds);
        ReturnJson returnJson = new ReturnJson();
        returnJson.setCode(200);
        returnJson.setState("success");
        returnJson.setFinished(true);
        returnJson.setPageSize(pageSize);
        returnJson.setItemsCount((int) workerIPage.getTotal());
        returnJson.setPageCount((int)workerIPage.getPages());
        returnJson.setData(workerIPage.getRecords());
        return returnJson;
    }

    /**
     * 按编号、姓名、手机号，查询该管理人员下已认证的创客
     * @param managersId
     * @param id
     * @param accountName
     * @param mobileCode
     * @return
     */
    @Override
    public ReturnJson getByIdAndAccountNameAndMobile(String managersId, String id, String accountName, String mobileCode) {
        List<String> merchantIds = acquireMerchantID.getMerchantIds(managersId);
        merchantIds.add(managersId);
        List<Worker> workers = workerDao.selectByIdAndAccountNameAndMobilePaas(merchantIds, id, accountName, mobileCode);
        return ReturnJson.success(workers);
    }

    @Override
    public ReturnJson getByIdAndAccountNameAndMobileNot(String managersId, String id, String accountName, String mobileCode) {
        List<String> merchantIds = acquireMerchantID.getMerchantIds(managersId);
        merchantIds.add(managersId);
        List<Worker> workers = workerDao.selectByIdAndAccountNameAndMobilePaasNot(merchantIds, id, accountName, mobileCode);
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
            MerchantWorker workerServiceOne = merchantWorkerService.getOne(new QueryWrapper<MerchantWorker>().eq("worker_id", worker.getId()).eq("merchant_id", merchantId));
            if (workerServiceOne == null){
                MerchantWorker merchantWorker = new MerchantWorker();
                merchantWorker.setMerchantId(merchantId);
                merchantWorker.setWorkerId(worker.getId());
                merchantWorkers.add(merchantWorker);
            }
        }
        boolean b = merchantWorkerService.saveOrUpdateBatch(merchantWorkers);
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

    /**
     * 查询创客的收入列表
     * @param id
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public ReturnJson getWorkerPaymentList(String id, Integer page, Integer pageSize) {
        Page<WorekerPaymentListPo> worekerPaymentListPoPage = new Page<>(page,pageSize);
        IPage<WorekerPaymentListPo> worekerPaymentListPoIPage = workerDao.workerPaymentList(worekerPaymentListPoPage, id);
        ReturnJson returnJson = new ReturnJson();
        returnJson.setCode(200);
        returnJson.setState("success");
        returnJson.setFinished(true);
        returnJson.setPageSize(pageSize);
        returnJson.setItemsCount((int) worekerPaymentListPoIPage.getTotal());
        returnJson.setPageCount((int)worekerPaymentListPoIPage.getPages());
        returnJson.setData(worekerPaymentListPoIPage.getRecords());
        return returnJson;
    }

    /**
     * 编辑创客
     * @param worker
     * @return
     */
    @Override
    public ReturnJson updateWorker(Worker worker) {
        boolean flag = this.updateById(worker);
        if (flag) {
            return ReturnJson.success("编辑成功！");
        }
        return ReturnJson.error("编辑失败！");
    }
}
