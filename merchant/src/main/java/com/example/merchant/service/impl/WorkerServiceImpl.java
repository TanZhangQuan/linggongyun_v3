package com.example.merchant.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.sms.SenSMS;
import com.example.common.util.*;
import com.example.merchant.dto.makerend.AddWorkerDTO;
import com.example.merchant.dto.merchant.WorkerDTO;
import com.example.merchant.dto.platform.WorkerQueryDTO;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.*;
import com.example.merchant.util.AcquireID;
import com.example.merchant.util.JwtUtils;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.MerchantDao;
import com.example.mybatis.mapper.WorkerDao;
import com.example.mybatis.po.WorekerPaymentListPo;
import com.example.mybatis.po.WorkerPo;
import com.example.mybatis.vo.WorkerCompanyVO;
import com.example.mybatis.vo.WorkerPassVO;
import com.example.mybatis.vo.WorkerVO;
import com.example.redis.dao.RedisDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
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

    @Value("${PWD_KEY}")
    String PWD_KEY;
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
    @Value("${TOKEN}")
    private String TOKEN;
    @Resource
    private AcquireID acquireID;
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


    @Override
    public ReturnJson getWorkerAll(String merchantId, Integer page, Integer pageSize, Integer workerType) {
        Merchant merchant = merchantDao.selectById(merchantId);
        QueryWrapper<CompanyWorker> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CompanyWorker::getCompanyId, merchant.getCompanyId());
        List<CompanyWorker> records = companyWorkerService.list(queryWrapper);
        Page<Worker> workerPage = null;
        if (!VerificationCheck.listIsNull(records)) {
            List<String> ids = new ArrayList<>();
            for (CompanyWorker companyWorker : records) {
                ids.add(companyWorker.getWorkerId());
            }
            Page<Worker> pageData = new Page<>(page, pageSize);
            if (workerType == 0) {
                workerPage = workerDao.selectPage(pageData,
                        new QueryWrapper<Worker>().lambda().in(Worker::getId, ids)
                                .eq(Worker::getAttestation, 1)
                                .eq(Worker::getAgreementSign, 2));
            }
            if (workerType == 1) {
                workerPage = workerDao.selectPage(pageData,
                        new QueryWrapper<Worker>().lambda().in(Worker::getId, ids)
                                .ne(Worker::getAttestation, 1)
                                .ne(Worker::getAgreementSign, 2));
            }
            return ReturnJson.success(workerPage);
        }
        return ReturnJson.success("");
    }


    @Override
    public ReturnJson getByIdAndAccountNameAndMobile(String merchantId, WorkerDTO workerDto) {
        Merchant merchant = merchantDao.selectById(merchantId);
        Page<Worker> workerPage = new Page<>(workerDto.getPageNo(), workerDto.getPageSize());
        IPage<Worker> workerIPage = workerDao.selectByIdAndAccountNameAndMobile(workerPage, merchant.getCompanyId(), workerDto.getWorkerId(), workerDto.getAccountName(), workerDto.getMobileCode());
        return ReturnJson.success(workerIPage);
    }


    @Override
    public ReturnJson getWorkerInfo(String id) {
        Worker worker = workerDao.selectById(id);
        worker.setUserPwd("");
        List<WorkerTask> workerTasks = workerTaskService.list(new QueryWrapper<WorkerTask>().lambda()
                .eq(WorkerTask::getWorkerId, worker.getId()));
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


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson saveWorker(List<Worker> workers, String merchantId) throws CommonException {
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
        throw new CommonException(300, "导入失败！");
    }


    @Override
    public ReturnJson getWorkerByTaskId(String taskId, Integer pageNo, Integer pageSize) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        Page page = new Page(pageNo, pageSize);
        IPage<WorkerPo> poList = workerDao.getWorkerByTaskId(page, taskId);
        if (poList != null) {
            return ReturnJson.success(poList);
        }
        return returnJson;
    }


    @Override
    public ReturnJson getCheckByTaskId(String taskId, Integer pageNo, Integer pageSize) {
        ReturnJson returnJson = new ReturnJson("验收查询失败", 300);
        Page page = new Page(pageNo, pageSize);
        IPage<WorkerPo> poList = workerDao.getCheckByTaskId(page, taskId);
        if (poList != null) {
            return ReturnJson.success(poList);
        }
        return returnJson;
    }


    @Override
    public ReturnJson getWorkerPaymentListPaas(String id, Integer page, Integer pageSize) {
        Page<WorekerPaymentListPo> workerPaymentListPoPage = new Page<>(page, pageSize);
        IPage<WorekerPaymentListPo> workerPaymentListPoIPage = workerDao.workerPaymentList(workerPaymentListPoPage, id);
        return ReturnJson.success(workerPaymentListPoIPage);
    }


    @Override
    public ReturnJson updateWorkerPaas(Worker worker) {
        boolean flag = this.updateById(worker);
        if (flag) {
            return ReturnJson.success("编辑成功！");
        }
        return ReturnJson.error("编辑失败！");
    }


    @Override
    public ReturnJson loginWorker(String username, String password, HttpServletResponse response) {
        String encryptPWD = MD5.md5(PWD_KEY + password);
        QueryWrapper<Worker> workerQueryWrapper = new QueryWrapper<>();
        workerQueryWrapper.lambda().eq(Worker::getUserName, username)
                .eq(Worker::getUserPwd, encryptPWD);
        Worker worker = this.getOne(workerQueryWrapper);
        if (worker != null) {
            String token = jwtUtils.generateToken(worker.getId());
            redisDao.set(worker.getId(), token);
            response.setHeader(TOKEN, token);
            redisDao.setExpire(worker.getId(), 1, TimeUnit.DAYS);
            return ReturnJson.success("登录成功", token);
        }
        return ReturnJson.error("你输入的用户名或密码有误！");
    }


    @Override
    public ReturnJson senSMS(String mobileCode, String isNot) {
        ReturnJson rj = new ReturnJson();
        Worker worker = workerDao.selectOne(new QueryWrapper<Worker>().lambda()
                .eq(Worker::getMobileCode, mobileCode));
        if ("T".equals(isNot)) {
            if (worker == null) {
                rj.setCode(300);
                rj.setMessage("你还未注册，请先去注册！");
                return rj;
            } else {
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
            }
        } else {
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
        }
        return rj;
    }


    @Override
    public ReturnJson loginMobile(String loginMobile, String checkCode, HttpServletResponse resource) {
        String redisCheckCode = redisDao.get(loginMobile);
        if (StringUtils.isBlank(redisCheckCode)) {
            return ReturnJson.error("验证码以过期，请重新获取！");
        } else if (!checkCode.equals(redisCheckCode)) {
            return ReturnJson.error("输入的验证码有误!");
        } else {
            redisDao.remove(loginMobile);
            Worker worker = this.getOne(new QueryWrapper<Worker>().lambda()
                    .eq(Worker::getMobileCode, loginMobile));
            String token = jwtUtils.generateToken(worker.getId());
            resource.setHeader(TOKEN, token);
            redisDao.set(worker.getId(), token);
            redisDao.setExpire(worker.getId(), 1, TimeUnit.DAYS);
            return ReturnJson.success("登录成功", token);
        }
    }

    @Override
    public ReturnJson updataPassWord(String loginMobile, String checkCode, String newPassWord) {
        String redisCode = redisDao.get(loginMobile);
        if (checkCode.equals(redisCode)) {
            Worker worker = workerDao.selectOne(new QueryWrapper<Worker>().lambda()
                    .eq(Worker::getMobileCode, loginMobile));
            if (worker != null) {
                if (worker.getUserPwd().equals(MD5.md5(PWD_KEY + newPassWord))) {
                    return ReturnJson.error("旧密码与新密码一致！");
                }
                worker.setUserPwd(MD5.md5(PWD_KEY + newPassWord));
                int flag = workerDao.updateById(worker);
                if (flag > 0) {
                    redisDao.remove(loginMobile);
                    return ReturnJson.success("密码修改成功！");
                } else {
                    return ReturnJson.success("密码修改失败！");
                }
            } else {
                return ReturnJson.error("你输入的号码不存在对应创客！");
            }
        }
        return ReturnJson.error("你的验证码有误！");
    }

    @Override
    public ReturnJson wxLogin(String code, String iv, String encryptedData) {
        JSONObject result = new JSONObject();
        if ("".equals(code)) {
            return ReturnJson.error("请输入微信授权码");
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
            return ReturnJson.error("登录失败");
        }
        Object errcode = wxResult.get("errcode");
        String errmsg = wxResult.getString("errmsg");
        if (errcode != null) {
            log.error(errmsg);
            return ReturnJson.error(errmsg);
        }
        String openid = wxResult.getString("openid");
        String sessionKey = wxResult.getString("session_key");
        if (StringUtils.isBlank(openid)) {
            log.error("微信授权失败, openid为空");
            return ReturnJson.error("登录失败");
        }

        if (StringUtils.isBlank(sessionKey)) {
            log.error("微信授权失败, session_key为空");
            return ReturnJson.error("登录失败");
        }
        result.put("openid", openid);
        result.put("sessionKey", sessionKey);

        if (StringUtils.isNoneBlank(iv, encryptedData)) {
            // 参数含义：第一个，加密数据串（String）；第二个，session_key需要通过微信小程序的code获得（String）；
            // 第三个，数据加密时所使用的偏移量，解密时需要使用（String）；第四个，编码；
            String AesResult = AesCbcUtil.decrypt(encryptedData, sessionKey, iv, "UTF-8");

            if (StringUtils.isBlank(AesResult)) {
                log.error("解密数据失败");
                return ReturnJson.error("登录失败");
            }

            // 将解密后的JSON格式字符串转化为对象
            wxResult = JSONObject.parseObject(AesResult);
            // 查询手机号
            String purePhoneNumber = wxResult.getString("purePhoneNumber");
            if (StringUtils.isBlank(purePhoneNumber)) {
                log.error("微信授权失败，手机号为空");
                return ReturnJson.error("登录失败");
            }

            result.put("purePhoneNumber", purePhoneNumber);

            Worker worker = workerDao.selectOne(new QueryWrapper<Worker>().lambda()
                    .eq(Worker::getMobileCode, purePhoneNumber));
            if (worker == null) {
                worker = new Worker();
                worker.setWxId(openid);
                worker.setMobileCode((String) wxResult.get("purePhoneNumber"));
                worker.setCreateDate(LocalDateTime.now());
                workerDao.insert(worker);
            } else {
                worker.setWxId(openid);
                workerDao.updateById(worker);
            }
            String token = jwtUtils.generateToken(worker.getId());
            redisDao.set(worker.getId(), token);
            redisDao.setExpire(worker.getId(), 1, TimeUnit.DAYS);
            return ReturnJson.success("登录成功", token);
        }
        return ReturnJson.error("信息错误,请重新登录");
    }

    @Override
    public ReturnJson setWorkerMakeMoney() {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        List<WorkerVO> workerVOS = workerDao.setWorkerMakeMoney();
        if (workerVOS != null) {
            for (int i = 0; i < workerVOS.size(); i++) {
                int num = workerVOS.get(i).getAccountName().length();
                String name = workerVOS.get(i).getAccountName().substring(0, 1);
                switch (num - 1) {
                    case 1:
                        workerVOS.get(i).setAccountName(name + "*");
                        break;
                    case 2:
                        workerVOS.get(i).setAccountName(name + "**");
                        break;
                    default:
                        workerVOS.get(i).setAccountName(name + "***");
                        break;
                }
            }
            returnJson = new ReturnJson("操作成功", workerVOS, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson logout(String workerId) {
        redisDao.remove(workerId);
        return ReturnJson.success("退出登录成功！");
    }

    @Override
    public ReturnJson getWorkerInfoBytoken(String userId) {
        Worker worker = this.getById(userId);
        worker.setUserPwd("");
        return ReturnJson.success(worker);
    }

    @Override
    public ReturnJson getPaasWorkerByTaskId(String taskId, Integer pageNo, Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        IPage<WorkerPassVO> iPage = workerDao.getPaasCheckByTaskId(page, taskId);
        return ReturnJson.success(iPage);
    }

    @Override
    public ReturnJson registerWorker(AddWorkerDTO addWorkerDto) {
        Worker worker = workerDao.selectOne(new QueryWrapper<Worker>().lambda()
                .eq(Worker::getMobileCode, addWorkerDto.getMobileCode()));
        if (worker == null) {
            String redisCheckCode = redisDao.get(addWorkerDto.getMobileCode());
            if (StringUtils.isBlank(redisCheckCode)) {
                return ReturnJson.error("验证码以过期，请重新获取！");
            } else if (!redisCheckCode.equals(addWorkerDto.getCheckCode())) {
                return ReturnJson.error("输入的验证码有误！");
            }
            worker = new Worker();
            worker.setUserName(addWorkerDto.getUserName());
            worker.setUserPwd(MD5.md5(PWD_KEY + addWorkerDto.getUserPwd()));
            worker.setMobileCode(addWorkerDto.getMobileCode());
            workerDao.insert(worker);
            return ReturnJson.success("注册成功");
        } else {
            return ReturnJson.success("该手机号已经注册过，请直接登录！");
        }
    }

    @Override
    public ReturnJson getWorkerQuery(String managersId, WorkerQueryDTO workerQueryDto) throws CommonException {
        List<String> companyIds = acquireID.getCompanyIds(managersId);
        IPage<Worker> workerIPage = workerDao.selectWorkerQuery(new Page(workerQueryDto.getPageNo(), workerQueryDto.getPageSize()), companyIds, workerQueryDto.getWorkerId(), workerQueryDto.getAccountName(), workerQueryDto.getMobileCode());
        return ReturnJson.success(workerIPage);
    }

    @Override
    public ReturnJson getWorkerQueryNot(String managersId, WorkerQueryDTO workerQueryDto) throws CommonException {
        List<String> companyIds = acquireID.getCompanyIds(managersId);
        IPage<Worker> workerIPage = workerDao.selectWorkerQueryNot(new Page(workerQueryDto.getPageNo(), workerQueryDto.getPageSize()), companyIds, workerQueryDto.getWorkerId(), workerQueryDto.getAccountName(), workerQueryDto.getMobileCode());
        return ReturnJson.success(workerIPage);
    }

    @Override
    public ReturnJson queryWorkerInfo(String workerId) {
        return ReturnJson.success(workerDao.queryWorkerInfo(workerId));
    }

    @Override
    public ReturnJson queryWorkerCompanyByID(String merchantId, Integer pageNo, Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        Merchant merchant = merchantDao.selectById(merchantId);
        IPage<WorkerCompanyVO> iPage;
        if (merchant != null) {
            iPage = workerDao.queryWorkerCompanyByID(page, merchant.getCompanyId());
        } else {
            iPage = workerDao.queryWorkerCompanyByID(page, merchantId);
        }
        return ReturnJson.success(iPage);
    }
}

