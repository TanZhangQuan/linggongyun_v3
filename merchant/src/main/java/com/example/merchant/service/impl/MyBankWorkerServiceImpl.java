package com.example.merchant.service.impl;

import com.example.common.mybank.entity.BankCardBind;
import com.example.common.mybank.entity.Personal;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.MyBankService;
import com.example.merchant.service.MyBankWorkerService;
import com.example.mybatis.entity.Worker;
import com.example.mybatis.mapper.WorkerDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class MyBankWorkerServiceImpl implements MyBankWorkerService {

    @Autowired
    private WorkerDao workerDao;
    @Autowired
    private MyBankService myBankService;

    @Override
    public ReturnJson personalRegister(Personal personal, String userId) throws Exception {
        Worker worker = workerDao.selectById(userId);
        if (worker == null) {
            log.info("UserID---->" + userId);
            ReturnJson.error("输入的UserID有误！");
        }
        if (worker.getSubAccountNo() != null) {
            return ReturnJson.error("您已经是网商会员了，可以直接使用！");
        }
        personal.setUid(userId);
        Map<String, Object> r = myBankService.personalRegister(personal);
        if (("F").equals(r.get("is_success"))) {
            return ReturnJson.error("添加失败");
        }
        worker.setMemberId((String) r.get("member_id"));
        worker.setSubAccountNo((String) r.get("sub_account_no"));
        workerDao.updateById(worker);
        return ReturnJson.success("注册网商个人会员成功！");
    }

    @Override
    public ReturnJson personalInfoModify(Personal personal, String userId) throws Exception {
        Worker worker = workerDao.selectById(userId);
        if (worker == null) {
            log.info("UserID---->" + userId);
            ReturnJson.error("输入的UserID有误！");
        }
        if (worker.getSubAccountNo() == null) {
            return ReturnJson.error("您还未注册，请先注册！");
        }
        personal.setUid(userId);
        Map<String, Object> r = myBankService.personalRegister(personal);
        if (("F").equals(r.get("is_success"))) {
            return ReturnJson.error("修改失败");
        }
        worker.setMemberId((String) r.get("member_id"));
        worker.setSubAccountNo((String) r.get("sub_account_no"));
        workerDao.updateById(worker);
        return ReturnJson.success("修改网商个人会员成功！");
    }

    @Override
    public ReturnJson personalInfoQuery(String userId) throws Exception {
        Worker worker = workerDao.selectById(userId);
        if (worker == null) {
            log.info("UserID---->" + userId);
            ReturnJson.error("输入的UserID有误！");
        }
        if (worker.getSubAccountNo() == null) {
            return ReturnJson.error("您还未注册，请先注册！");
        }
        Map<String, Object> r = myBankService.personalInfoQuery(userId);
        if (("F").equals(r.get("is_success"))) {
            log.info("UID:{}", userId + "emg:{}", r.get("error_message"));
            return ReturnJson.error("登录信息有误，请稍后再试！");
        }
        worker.setMemberId((String) r.get("member_id"));
        worker.setSubAccountNo((String) r.get("sub_account_no"));
        workerDao.updateById(worker);
        return ReturnJson.success("查询网商个人会员成功！", r);
    }

    @Override
    public ReturnJson bankCardBind(BankCardBind params, String userId) throws Exception {
        Worker worker = workerDao.selectById(userId);
        if (worker == null) {
            log.info("UserID---->" + userId);
            ReturnJson.error("输入的UserID有误！");
        }
        if (worker.getBankId() != null) {
            return ReturnJson.error("您已经绑定过了，如果绑定错误请先解绑！");
        }
        params.setUid(userId);
        Map<String, Object> r = myBankService.bankCardBind(params);
        if (("F").equals(r.get("is_success"))) {
            log.info("UID:{}", userId + "emg:{}", r.get("error_message"));
            return ReturnJson.error("输入信息有误，请稍后再试！");
        }
        worker.setBankId((String) r.get("bank_id"));
        workerDao.updateById(worker);
        return ReturnJson.success("网商银行银行卡绑定成功，马上可以进行提现操作！");
    }

    @Override
    public ReturnJson bankCardUnBind(String bankId, String userId) throws Exception {
        Worker worker = workerDao.selectById(userId);
        if (worker == null) {
            log.info("UID:{}", userId);
            return ReturnJson.success("登录信息有误，请稍后再试！");
        }
        if (worker.getBankId() == null) {
            return ReturnJson.error("请先绑定网商银行的银行卡在进行操作！");
        }
        Map<String, Object> r = myBankService.bankCardUnBind(bankId, userId);
        if (("F").equals(r.get("is_success"))) {
            log.info("UID:{}", userId + "emg:{}", r.get("error_message"));
            return ReturnJson.error("输入信息有误，请稍后再试！");
        }
        worker.setBankId(null);
        workerDao.updateById(worker);
        return ReturnJson.success("解绑成功，可以重新进行绑定了！");
    }


}
