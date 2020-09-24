package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ReturnJson;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.CompanyWorkerService;
import com.example.merchant.service.TaskService;
import com.example.merchant.service.WorkerService;
import com.example.merchant.service.WorkerTaskService;
import com.example.mybatis.entity.CompanyWorker;
import com.example.mybatis.entity.Merchant;
import com.example.mybatis.entity.Worker;
import com.example.mybatis.entity.WorkerTask;
import com.example.mybatis.mapper.MerchantDao;
import com.example.mybatis.mapper.WorkerDao;
import com.example.mybatis.po.WorekerPaymentListPo;
import com.example.mybatis.po.WorkerPo;
import com.example.merchant.util.AcquireID;
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
    private CompanyWorkerService companyWorkerService;

    @Autowired
    private MerchantDao merchantDao;


    /**
     * 分页查询商户下的所以创客
     *
     * @param merchantId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public ReturnJson getWorkerAll(String merchantId, Integer page, Integer pageSize) {
        ReturnJson returnJson = new ReturnJson();
        Merchant merchant = merchantDao.selectById(merchantId);
        Page<CompanyWorker> pageData = new Page<>(page, pageSize);
        QueryWrapper<CompanyWorker> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_id", merchant.getCompanyId());
        Page<CompanyWorker> merchantWorkerPage = companyWorkerService.page(pageData, queryWrapper);
        List<CompanyWorker> records = merchantWorkerPage.getRecords();
        if (records != null && records.size() != 0) {
            List<String> ids = new ArrayList<>();
            for (CompanyWorker companyWorker : records) {
                ids.add(companyWorker.getWorkerId());
            }
            List<Worker> workers = workerDao.selectBatchIds(ids);
            returnJson.setData(workers);
        }
        returnJson.setCode(200);
        returnJson.setState("success");
        returnJson.setFinished(true);
        returnJson.setPageSize(pageSize);
        returnJson.setItemsCount((int) merchantWorkerPage.getTotal());
        returnJson.setPageCount((int) merchantWorkerPage.getPages());
        return returnJson;
    }

    /**
     * 按编号、姓名、手机号，查询该商户下的创客
     *
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
     *
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
     *
     * @param workers
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson saveWorker(List<Worker> workers, String merchantId) {
        List<CompanyWorker> companyWorkers = new ArrayList<>();
        Merchant merchant = merchantDao.selectById(merchantId);
        List<String> mobileCodes = new ArrayList<>();
        for (Worker worker : workers) {
            mobileCodes.add(worker.getMobileCode());
            if (StringUtils.isBlank(worker.getId())) {
                this.saveOrUpdate(worker);
            } else {
                CompanyWorker companyWorker = new CompanyWorker();
                companyWorker.setCompanyId(merchant.getCompanyId());
                companyWorker.setWorkerId(worker.getId());
                companyWorkers.add(companyWorker);
            }
        }
        List<Worker> newworkers = workerDao.selectList(new QueryWrapper<Worker>().in("mobile_code", mobileCodes));
        for (Worker worker : newworkers) {
            CompanyWorker companyWorker = new CompanyWorker();
            companyWorker.setCompanyId(merchant.getCompanyId());
            companyWorker.setWorkerId(worker.getId());
            companyWorkers.add(companyWorker);
        }
        boolean b = companyWorkerService.saveBatch(companyWorkers);
        if (b) {
            return ReturnJson.success("导入成功！");
        }
        return ReturnJson.error("导入失败！");
    }


    @Override
    public ReturnJson getWorkerByTaskId(String taskId, Integer offset) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        RowBounds rowBounds = new RowBounds(offset * 9, 9);
        List<WorkerPo> poList = workerDao.getWorkerByTaskId(taskId, rowBounds);
        if (poList != null) {
            returnJson = new ReturnJson("查询成功", poList, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getCheckByTaskId(String taskId, Integer offset) {
        ReturnJson returnJson = new ReturnJson("验收查询失败", 300);
        RowBounds rowBounds = new RowBounds(offset * 9, 9);
        List<WorkerPo> poList = workerDao.getCheckByTaskId(taskId, rowBounds);
        if (poList != null) {
            returnJson = new ReturnJson("验收查询成功", poList, 200);
        }
        return returnJson;
    }

    @Autowired
    private AcquireID acquireID;

    /**
     * 分页查询管理人员下的所以创客
     *
     * @param managersId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public ReturnJson getWorkerAllPaas(String managersId, Integer page, Integer pageSize) throws CommonException {
        List<String> merchantIds = acquireID.getMerchantIds(managersId);
        merchantIds.add(managersId);
        Page<Worker> workerPage = new Page<>(page, pageSize);
        IPage<Worker> workerIPage = workerDao.selectWorkerAll(workerPage, merchantIds);
        ReturnJson returnJson = new ReturnJson();
        returnJson.setCode(200);
        returnJson.setState("success");
        returnJson.setFinished(true);
        returnJson.setPageSize(pageSize);
        returnJson.setItemsCount((int) workerIPage.getTotal());
        returnJson.setPageCount((int) workerIPage.getPages());
        returnJson.setData(workerIPage.getRecords());
        return returnJson;
    }

    @Override
    public ReturnJson getWorkerAllNotPaas(String managersId, Integer page, Integer pageSize) throws CommonException {
        List<String> merchantIds = acquireID.getMerchantIds(managersId);
        merchantIds.add(managersId);
        Page<Worker> workerPage = new Page<>(page, pageSize);
        IPage<Worker> workerIPage = workerDao.selectWorkerAllNot(workerPage, merchantIds);
        ReturnJson returnJson = new ReturnJson();
        returnJson.setCode(200);
        returnJson.setState("success");
        returnJson.setFinished(true);
        returnJson.setPageSize(pageSize);
        returnJson.setItemsCount((int) workerIPage.getTotal());
        returnJson.setPageCount((int) workerIPage.getPages());
        returnJson.setData(workerIPage.getRecords());
        return returnJson;
    }

    /**
     * 按编号、姓名、手机号，查询该管理人员下已认证的创客
     *
     * @param managersId
     * @param id
     * @param accountName
     * @param mobileCode
     * @return
     */
    @Override
    public ReturnJson getByIdAndAccountNameAndMobilePaas(String managersId, String id, String accountName, String mobileCode) throws CommonException {
        List<String> merchantIds = acquireID.getMerchantIds(managersId);
        merchantIds.add(managersId);
        List<Worker> workers = workerDao.selectByIdAndAccountNameAndMobilePaas(merchantIds, id, accountName, mobileCode);
        return ReturnJson.success(workers);
    }

    @Override
    public ReturnJson getByIdAndAccountNameAndMobileNotPaas(String managersId, String id, String accountName, String mobileCode) throws CommonException {
        List<String> merchantIds = acquireID.getMerchantIds(managersId);
        merchantIds.add(managersId);
        List<Worker> workers = workerDao.selectByIdAndAccountNameAndMobilePaasNot(merchantIds, id, accountName, mobileCode);
        return ReturnJson.success(workers);
    }

    /**
     * 查询创客的基本信息
     *
     * @param id
     * @return
     */
    @Override
    public ReturnJson getWorkerInfoPaas(String id) {
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
     * 查询创客的收入列表
     *
     * @param id
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public ReturnJson getWorkerPaymentListPaas(String id, Integer page, Integer pageSize) {
        Page<WorekerPaymentListPo> worekerPaymentListPoPage = new Page<>(page, pageSize);
        IPage<WorekerPaymentListPo> worekerPaymentListPoIPage = workerDao.workerPaymentList(worekerPaymentListPoPage, id);
        ReturnJson returnJson = new ReturnJson();
        returnJson.setCode(200);
        returnJson.setState("success");
        returnJson.setFinished(true);
        returnJson.setPageSize(pageSize);
        returnJson.setItemsCount((int) worekerPaymentListPoIPage.getTotal());
        returnJson.setPageCount((int) worekerPaymentListPoIPage.getPages());
        returnJson.setData(worekerPaymentListPoIPage.getRecords());
        return returnJson;
    }

    /**
     * 编辑创客
     *
     * @param worker
     * @return
     */
    @Override
    public ReturnJson updateWorkerPaas(Worker worker) {
        boolean flag = this.updateById(worker);
        if (flag) {
            return ReturnJson.success("编辑成功！");
        }
        return ReturnJson.error("编辑失败！");
    }
}

