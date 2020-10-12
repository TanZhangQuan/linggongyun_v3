package com.example.merchant.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.sms.SenSMS;
import com.example.common.util.*;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.CompanyWorkerService;
import com.example.merchant.service.TaskService;
import com.example.merchant.service.WorkerService;
import com.example.merchant.service.WorkerTaskService;
import com.example.merchant.util.AcquireID;
import com.example.merchant.util.JwtUtils;
import com.example.mybatis.entity.CompanyWorker;
import com.example.mybatis.entity.Merchant;
import com.example.mybatis.entity.Worker;
import com.example.mybatis.entity.WorkerTask;
import com.example.mybatis.mapper.MerchantDao;
import com.example.mybatis.mapper.WorkerDao;
import com.example.mybatis.po.WorekerPaymentListPo;
import com.example.mybatis.po.WorkerPo;
import com.example.mybatis.vo.WorkerVo;
import com.example.redis.dao.RedisDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private TaskService taskService;

    @Resource
    private WorkerTaskService workerTaskService;

    @Resource
    private CompanyWorkerService companyWorkerService;

    @Resource
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
        Merchant merchant = merchantDao.selectById(merchantId);
        QueryWrapper<CompanyWorker> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_id", merchant.getCompanyId());
        List<CompanyWorker> records = companyWorkerService.list(queryWrapper);
        Page<Worker> workerPage = null;
        if (records != null && records.size() != 0) {
            List<String> ids = new ArrayList<>();
            for (CompanyWorker companyWorker : records) {
                ids.add(companyWorker.getWorkerId());
            }
            Page<Worker> pageData = new Page<>(page, pageSize);
            workerPage = workerDao.selectPage(pageData, new QueryWrapper<Worker>().in("id", ids));
            return ReturnJson.success(workerPage);
        }
        return ReturnJson.success("");
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
    public ReturnJson getByIdAndAccountNameAndMobile(String merchantId, String id, String accountName, String mobileCode, Integer page, Integer pageSize) {
        Page<Worker> workerPage = new Page<>(page, pageSize);
        IPage<Worker> workerIPage = workerDao.selectByIdAndAccountNameAndMobile(workerPage, merchantId, id, accountName, mobileCode);
        return ReturnJson.success(workerIPage);
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
        worker.setUserPwd("");
        List<WorkerTask> workerTasks = workerTaskService.list(new QueryWrapper<WorkerTask>().eq("worker_id", worker.getId()));
        List ids = new ArrayList();
        for (WorkerTask workerTask : workerTasks) {
            ids.add(workerTask.getTaskId());
        }
        List list = null;
        if (!VerificationCheck.listIsNull(ids)) {
            list = taskService.listByIds(ids);
        }
        return ReturnJson.success(worker, list);
    }

    /**
     * 导入创客，当创客没注册时先注册创客（创客登录账号为手机号码，密码为身份证后6为数）
     *
     * @param workers
     * @param merchantId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson saveWorker(List<Worker> workers, String merchantId) {
        List<CompanyWorker> companyWorkers = new ArrayList<>();
        Merchant merchant = merchantDao.selectById(merchantId);
        List<String> mobileCodes = new ArrayList<>();
        for (Worker worker : workers) {
            mobileCodes.add(worker.getMobileCode());
            if (StringUtils.isBlank(worker.getId())) {
                this.save(worker);
            }
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

    @Resource
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
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success("");
        }
        Page<Worker> workerPage = new Page<>(page, pageSize);
        IPage<Worker> workerIPage = workerDao.selectWorkerAll(workerPage, merchantIds);
        return ReturnJson.success(workerIPage);
    }

    @Override
    public ReturnJson getWorkerAllNotPaas(String managersId, Integer page, Integer pageSize) throws CommonException {
        List<String> merchantIds = acquireID.getMerchantIds(managersId);
        merchantIds.add(managersId);
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success("");
        }
        Page<Worker> workerPage = new Page<>(page, pageSize);
        IPage<Worker> workerIPage = workerDao.selectWorkerAllNot(workerPage, merchantIds);
        return ReturnJson.success(workerIPage);
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
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success("");
        }
        List<Worker> workers = workerDao.selectByIdAndAccountNameAndMobilePaas(merchantIds, id, accountName, mobileCode);
        return ReturnJson.success(workers);
    }

    @Override
    public ReturnJson getByIdAndAccountNameAndMobileNotPaas(String managersId, String id, String accountName, String mobileCode) throws CommonException {
        List<String> merchantIds = acquireID.getMerchantIds(managersId);
        merchantIds.add(managersId);
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success("");
        }
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
        return ReturnJson.success(worker, list);
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
        return ReturnJson.success(worekerPaymentListPoIPage);
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

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private SenSMS senSMS;

    @Resource
    private RedisDao redisDao;

    @Value("${APPID}")
    private String APPID;

    @Value("${SECRET}")
    private String SECRET;

    /**
     * 创客登录
     *
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
            redisDao.setExpire(worker.getId(), 7, TimeUnit.DAYS);
            return ReturnJson.success(worker);
        }
        return ReturnJson.error("你输入的用户名或密码有误！");
    }

    /**
     * 发送验证码
     *
     * @param mobileCode
     * @return
     */
    @Override
    public ReturnJson senSMS(String mobileCode) {
        ReturnJson rj = new ReturnJson();
        Worker worker = this.getOne(new QueryWrapper<Worker>().eq("mobile_code", mobileCode));
        if (worker == null) {
            rj.setCode(401);
            rj.setMessage("你还未注册，请先去注册！");
            return rj;
        }
        Map<String, Object> result = senSMS.senSMS(mobileCode);
        if ("000000".equals(result.get("statusCode"))) {
            rj.setCode(200);
            rj.setMessage("验证码发送成功");
            redisDao.set(mobileCode, String.valueOf(result.get("checkCode")));
            redisDao.setExpire(mobileCode, 5 * 60);
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
     * 手机号登录
     *
     * @param loginMobile
     * @param checkCode
     * @param resource
     * @return
     */
    @Override
    public ReturnJson loginMobile(String loginMobile, String checkCode, HttpServletResponse resource) {
        String redisCheckCode = redisDao.get(loginMobile);
        if (StringUtils.isBlank(redisCheckCode)) {
            return ReturnJson.error("验证码以过期，请重新获取！");
        } else if (!checkCode.equals(redisCheckCode)) {
            return ReturnJson.error("输入的验证码有误!");
        } else {
            redisDao.remove(loginMobile);
            Worker worker = this.getOne(new QueryWrapper<Worker>().eq("mobile_code", loginMobile));
            worker.setUserPwd("");
            String token = jwtUtils.generateToken(worker.getId());
            resource.setHeader(TOKEN, token);
            redisDao.set(worker.getId(), JsonUtils.objectToJson(worker));
            redisDao.setExpire(worker.getId(), 7, TimeUnit.DAYS);
            return ReturnJson.success(worker);
        }
    }

    /**
     * 修改密码忘记密码
     *
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
     *
     * @param code
     * @return
     */
    @Override
    public ReturnJson wxLogin(String code, String iv, String encryptedData) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        JSONObject result = new JSONObject();
        if (code.equals("")) {
            return returnJson.error("请输入微信授权码");
        }
        //通过code换取网页授权access_token
        String url = "https://api.weixin.qq.com/sns/jscode2session?" +
                "appid=" + APPID +         //开发者设置中的appId
                "&secret=" + SECRET +      //开发者设置中的appSecret
                "&js_code=" + code +       //小程序调用wx.login返回的code
                "&grant_type=authorization_code";    //默认参数

        JSONObject wxResult = HttpClientUtils.httpGet(url);
        if (wxResult == null) {
            log.error("微信授权失败, 查询数据失败");
            return returnJson.error("登录失败");
        }
        Object errcode = wxResult.get("errcode");
        String errmsg = wxResult.getString("errmsg");
        if (errcode != null) {
            log.error(errmsg);
            return returnJson.error(errmsg);
        }
        String openid = wxResult.getString("openid");
        String sessionKey = wxResult.getString("session_key");
        if (StringUtils.isBlank(openid)) {
            log.error("微信授权失败, openid为空");
            return returnJson.error("登录失败");
        }

        if (StringUtils.isBlank(sessionKey)) {
            log.error("微信授权失败, session_key为空");
            return returnJson.error("登录失败");
        }
        result.put("openid", openid);
        result.put("sessionKey", sessionKey);

        if (StringUtils.isNoneBlank(iv, encryptedData)) {
            // 参数含义：第一个，加密数据串（String）；第二个，session_key需要通过微信小程序的code获得（String）；
            // 第三个，数据加密时所使用的偏移量，解密时需要使用（String）；第四个，编码
            String AesResult = AesCbcUtil.decrypt(encryptedData, sessionKey, iv, "UTF-8");

            if (StringUtils.isBlank(AesResult)) {
                log.error("解密数据失败");
                return returnJson.error("登录失败");
            }

            // 将解密后的JSON格式字符串转化为对象
            wxResult = JSONObject.parseObject(AesResult);
            // 查询手机号
            String purePhoneNumber = wxResult.getString("purePhoneNumber");
            if (StringUtils.isBlank(purePhoneNumber)) {
                log.error("微信授权失败，手机号为空");
                return returnJson.error("登录失败");
            }

            result.put("purePhoneNumber", purePhoneNumber);

            Worker worker = workerDao.selectOne(new QueryWrapper<Worker>().eq("wx_id", openid));
            if (worker == null) {
                worker = new Worker();
                worker.setWxId(openid);
                worker.setMobileCode((String) wxResult.get("purePhoneNumber"));
                worker.setAccountName((String) wxResult.get("nickName"));//用户昵称
                worker.setWorkerSex((Integer) wxResult.get("gender"));//用户性别
                workerDao.insert(worker);
            } else {
                worker.setWxName((String) wxResult.get("nickName"));//获取微信名称
                worker.setHeadPortraits((String) wxResult.get("avatarUrl"));//获取微信头像
                workerDao.update(worker, new QueryWrapper<Worker>().eq("wx_id", openid));
            }
        }
        return returnJson.success(wxResult);
    }

    /**
     * 首页创客赚钱信息
     *
     * @return
     */
    @Override
    public ReturnJson setWorkerMakeMoney() {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        List<WorkerVo> workerVos = workerDao.setWorkerMakeMoney();
        if (workerVos != null) {
            for (int i = 0; i < workerVos.size(); i++) {
                int num = workerVos.get(i).getAccountName().length();
                String name = workerVos.get(i).getAccountName().substring(0, 1);
                switch (num - 1) {
                    case 1:
                        workerVos.get(i).setAccountName(name + "*");
                        break;
                    case 2:
                        workerVos.get(i).setAccountName(name + "**");
                        break;
                    case 3:
                        workerVos.get(i).setAccountName(name + "***");
                        break;
                }
            }
            returnJson = new ReturnJson("操作成功", workerVos, 200);
        }
        return returnJson;
    }

    /**
     * 退出登录
     *
     * @param workerId
     * @return
     */
    @Override
    public ReturnJson logout(String workerId) {
        redisDao.remove(workerId);
        return ReturnJson.success("退出登录成功！");
    }
}

