package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.entity.Managers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.po.SalesManPaymentListPO;
import com.example.mybatis.vo.TimerStatisticsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-15
 */
public interface ManagersDao extends BaseMapper<Managers> {

    IPage<SalesManPaymentListPO> selectSalesManPaymentList(Page page, @Param("companyIds") List<String> companyIds);

    List<TimerStatisticsVO> timerSalesManStatistics();

    List<TimerStatisticsVO> timerAgentStatistics();
}
