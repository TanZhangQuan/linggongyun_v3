package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.AchievementStatisticsService;
import com.example.mybatis.entity.AchievementStatistics;
import com.example.mybatis.mapper.AchievementStatisticsDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author jun.
 * @date 2021/1/29.
 * @time 15:35.
 */
@Service
public class AchievementStatisticsServiceImpl extends ServiceImpl<AchievementStatisticsDao, AchievementStatistics> implements AchievementStatisticsService {

    @Resource
    private AchievementStatisticsDao achievementStatisticsDao;

    @Override
    public ReturnJson commissionSettlement(String achievementStatisticsId) {
        AchievementStatistics achievementStatistics = achievementStatisticsDao.selectById(achievementStatisticsId);
        if(null == achievementStatistics){
            return ReturnJson.error("失败");
        }
        achievementStatistics.setSettlementState(1);
        achievementStatisticsDao.updateById(achievementStatistics);
        return ReturnJson.success("结算成功");
    }
}
