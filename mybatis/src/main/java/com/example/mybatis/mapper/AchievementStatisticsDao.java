package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.entity.AchievementStatistics;
import com.example.mybatis.vo.AchievementStatisticsVO;
import com.example.mybatis.vo.SalesmanSAndAgentDetailVO;
import com.example.mybatis.vo.TotalAchievementStatisticsVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author .
 * @date 2021/1/29.
 * @time 15:29.
 */
public interface AchievementStatisticsDao extends BaseMapper<AchievementStatistics> {

    IPage<AchievementStatisticsVO> salesmanAndAgentStatistics(@Param("userId")String userId,@Param("objectType")Integer objectType,@Param("time") String time,Page page);

    TotalAchievementStatisticsVO totalSalesmanAndAgentStatistics(@Param("userId") String userId,@Param("objectType") Integer objectType);

    IPage<SalesmanSAndAgentDetailVO> salesmanDetail(@Param("managersId")String managersId,@Param("customerType")Integer customerType, Page page);

    IPage<SalesmanSAndAgentDetailVO> agentDetail(@Param("managersId")String managersId, Page page);

}
