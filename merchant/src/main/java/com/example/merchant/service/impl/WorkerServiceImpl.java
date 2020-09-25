package com.example.merchant.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.sms.SenSMS;
import com.example.common.util.HttpClientUtils;
import com.example.common.util.JsonUtils;
import com.example.common.util.MD5;
import com.example.common.util.ReturnJson;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.CompanyWorkerService;
import com.example.merchant.service.TaskService;
import com.example.merchant.service.WorkerService;
import com.example.merchant.service.WorkerTaskService;
import com.example.merchant.util.JwtUtils;
import com.example.mybatis.entity.CompanyWorker;
import com.example.mybatis.entity.Merchant;
import com.example.mybatis.entity.Worker;
import com.example.mybatis.entity.WorkerTask;
import com.example.mybatis.mapper.MerchantDao;
import com.example.mybatis.mapper.WorkerDao;
import com.example.mybatis.po.WorekerPaymentListPo;
import com.example.mybatis.po.WorkerPo;
import com.example.merchant.util.AcquireID;
import com.example.redis.dao.RedisDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Value("${PWD_KEY}")
    String PWD_KEY;
    @Value("${TOKEN}")
    private String TOKEN;

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

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SenSMS senSMS;

    @Autowired
    private RedisDao redisDao;

    @Value("${APPID}")
    private String APPID;

    @Value("${SECRET}")
    private String SECRET;

    /**
     * 创客登录
     * @param username
     * @param password
     * @param response
     * @return
     */
    @Override
    public ReturnJson loginWorker(String username, String password, HttpServletResponse response) {
        String encryptPWD = PWD_KEY + MD5.md5(password);
        QueryWrapper<Worker> workerQueryWrapper = new QueryWrapper<>();
        workerQueryWrapper.eq("user_name", username).eq("user_pwd", encryptPWD);
        Worker worker = this.getOne(workerQueryWrapper);
        if (worker != null) {
            String token = jwtUtils.generateToken(worker.getId());
            worker.setUserPwd("");
            redisDao.set(worker.getId(), JsonUtils.objectToJson(worker));
            response.setHeader(TOKEN, token);
            redisDao.setExpire(worker.getId(), 60 * 60 * 24 * 7);
            return ReturnJson.success(worker);
        }
        return ReturnJson.error("你输入的用户名或密码有误！");
    }

    /**
     * 发送验证码
     * @param mobileCode
     * @return
     */
    @Override
    public ReturnJson senSMS(String mobileCode) {
        ReturnJson rj = new ReturnJson();
        Worker worker = this.getOne(new QueryWrapper<Worker>().eq("mobile_code", mobileCode));
        if (worker == null){
            rj.setCode(401);
            rj.setMessage("你还未注册，请先去注册！");
            return rj;
        }
        Map<String, Object> result = senSMS.senSMS(mobileCode);
        if ("000000".equals(result.get("statusCode"))) {
            rj.setCode(200);
            rj.setMessage("验证码发送成功");
            redisDao.set(mobileCode,String.valueOf(result.get("checkCode")));
            redisDao.setExpire(mobileCode,5*60);
        } else if ("160040".equals(result.get("statusCode"))) {
            rj.setCode(300);
            rj.setMessage(String.valueOf(result.get("statusMsg")));
        } else {
            rj.setCode(300);
            rj.setMessage(String.valueOf(result.get("statusMsg")));
        }
        return rj;
    }

    /**
     *手机号登录
     * @param loginMobile
     * @param checkCode
     * @param resource
     * @return
     */
    @Override
    public ReturnJson loginMobile(String loginMobile, String checkCode, HttpServletResponse resource) {
        String redisCheckCode = redisDao.get(loginMobile);
        if (StringUtils.isBlank(redisCheckCode)){
            return ReturnJson.error("验证码以过期，请重新获取！");
        } else if (!checkCode.equals(redisCheckCode)){
            return ReturnJson.error("输入的验证码有误!");
        } else {
            redisDao.remove(loginMobile);
            Worker worker = this.getOne(new QueryWrapper<Worker>().eq("mobile_code", loginMobile));
            worker.setUserPwd("");
            String token = jwtUtils.generateToken(worker.getId());
            resource.setHeader(TOKEN,token);
            redisDao.set(worker.getId(), JsonUtils.objectToJson(worker));
            redisDao.setExpire(worker.getId(),60*60*24*7);
            return ReturnJson.success(worker);
        }
    }

    /**
     * 修改密码忘记密码
     * @param loginMobile
     * @param checkCode
     * @param newPassWord
     * @return
     */
    @Override
    public ReturnJson updataPassWord(String loginMobile, String checkCode, String newPassWord) {
        String redisCode = redisDao.get(loginMobile);
        if (redisCode.equals(checkCode)) {
            Worker worker = new Worker();
            worker.setUserPwd(PWD_KEY + MD5.md5(newPassWord));
            boolean flag = this.update(worker, new QueryWrapper<Worker>().eq("login_mobile", loginMobile));
            if (flag) {
                redisDao.remove(loginMobile);
                return ReturnJson.success("密码修改成功！");
            } else {
                return ReturnJson.success("密码修改失败！");
            }
        }
        return ReturnJson.error("你的验证码有误！");
    }

    /**
     * 微信登录
     * @param code
     * @return
     */
    @Override
    public ReturnJson wxLogin(String code) {
        //通过code换取网页授权access_token
        String url="https://api.weixin.qq.com/sns/jscode2session?appid="+APPID+"&secret="+SECRET+"&js_code="+code+"&grant_type=authorization_code";//请求路径
        JSONObject wxResult= HttpClientUtils.httpGet(url);
        String openid = wxResult.getString("openid");
        String access_Token = wxResult.getString("access_token");

        //拉取用户信息(需scope为 snsapi_userinfo)
        url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_Token +
                "&openid=" + openid +
                "&lang=zh_CN";
        JSONObject workerInfoJson = HttpClientUtils.httpGet(url);

        Worker worker = workerDao.selectOne(new QueryWrapper<Worker>().eq("wx_id",openid));
        if (worker == null) {
            worker = new Worker();

        } else {

        }
        return null;
    }
}

