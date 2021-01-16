package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.entity.Regulator;
import com.example.mybatis.po.RegulatorMerchantInfoPO;
import com.example.mybatis.po.RegulatorWorkerPO;
import com.example.mybatis.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 监管部门 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-25
 */
public interface RegulatorDao extends BaseMapper<Regulator> {
    IPage<Regulator> selectRegulator(Page page, @Param("regulatorName") String regulatorName, @Param("startDate") String startDate, @Param("endDate") String endDate);

    IPage<RegulatorWorkerPO> selectRegulatorWorker(Page page, @Param("workerId") String workerId, @Param("workerName") String workerName,
                                                   @Param("idCardCode") String idCardCode, @Param("paymentOrderIds") List<String> paymentOrderIds,
                                                   @Param("startDate") String startDate, @Param("endDate") String endDate);

    List<RegulatorWorkerPO> selectExportRegulatorWorker(@Param("workerIds") List<String> workerIds, @Param("paymentOrderIds") List<String> paymentOrderIds);

    IPage<RegulatorMerchantInfoPO> selectRegulatorMerchant(Page page,@Param("taxIds") List<String> taxIds, @Param("companyId") String companyId,
                                                           @Param("companyName")String companyName, @Param("startDate")String startDate,
                                                           @Param("endDate")String endDate);

    List<RegulatorMerchantInfoPO> selectExportRegulatorMerchant(@Param("companyIds") List<String> companyIds, @Param("taxIds") List<String> taxIds);

    IPage<TaxBriefVO> selectTaxBrief(Page page,String regulatorId);

    List<RegulatorPayInfoVO> regulatorPayInfo(String paymentId);

    RegulatorPaymentVO getRegulatorPay(String paymentId,Integer type);

    PayInfoVO getPayInfo(String paymentId,Integer type);

    RegulatorSubpackageInfoVO querySub(String paymentId);
}
