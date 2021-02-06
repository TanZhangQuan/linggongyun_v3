package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.AchievementStatistics;

/**
 * @author jun.
 * @date 2021/1/29.
 * @time 15:32.
 */
public interface AchievementStatisticsService extends IService<AchievementStatistics> {

    ReturnJson commissionSettlement(String achievementStatisticsId);

}
