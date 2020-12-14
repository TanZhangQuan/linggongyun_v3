package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.util.ReturnJson;
import com.example.common.util.VerificationCheck;
import com.example.merchant.service.PersonalCenterService;
import com.example.merchant.vo.makerend.WorkerInfoVO;
import com.example.mybatis.entity.Worker;
import com.example.mybatis.entity.WorkerBank;
import com.example.mybatis.mapper.WorkerBankDao;
import com.example.mybatis.mapper.WorkerDao;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PersonalCenterServiceImpl implements PersonalCenterService {

    @Resource
    private WorkerDao workerDao;

    @Resource
    private WorkerBankDao workerBankDao;

    @Override
    public ReturnJson personageInfo(String workerId) {
        Worker worker = workerDao.selectById(workerId);
        WorkerInfoVO workerInfoVO = new WorkerInfoVO();
        if (worker != null) {
            BeanUtils.copyProperties(worker,workerInfoVO);
        } else {
            return ReturnJson.error("该用户不存在！");
        }
        List<WorkerBank> workerBanks = workerBankDao.selectList(new QueryWrapper<WorkerBank>().eq("worker_id", workerId));
        if (!VerificationCheck.listIsNull(workerBanks)){
            workerInfoVO.setBankName(workerBanks.get(0).getBankName());
            workerInfoVO.setBankCode(workerBanks.get(0).getBankCode());
        }
        return ReturnJson.success(workerInfoVO);
    }
}
