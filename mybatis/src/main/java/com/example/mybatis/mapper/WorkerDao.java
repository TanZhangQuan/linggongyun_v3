package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.entity.Worker;
import com.example.mybatis.po.WorekerPaymentListPo;
import com.example.mybatis.po.WorkerPo;
import com.example.mybatis.vo.WorkerPassVo;
import com.example.mybatis.vo.WorkerVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 创客表 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface WorkerDao extends BaseMapper<Worker> {

    /**
     * 已接单创客明细
     *
     * @return
     */
    IPage<WorkerPo> getWorkerByTaskId(Page page, @Param("taskId") String taskId);

    IPage<WorkerPo> getCheckByTaskId(Page page, @Param("taskId") String taskId);

    IPage<Worker> selectByIdAndAccountNameAndMobile(Page page, @Param("merchantId") String merchantId, @Param("id") String id, @Param("accountName") String accountName, @Param("mobileCode") String mobileCode);

    IPage<Worker> selectWorkerQuery(Page page, @Param("companyIds") List<String> companyIds, @Param("workerId") String workerId, @Param("accountName") String accountName, @Param("mobileCode") String mobileCode);

    IPage<Worker> selectWorkerQueryNot(Page page, @Param("companyIds") List<String> companyIds, @Param("workerId") String workerId, @Param("accountName") String accountName, @Param("mobileCode") String mobileCode);

    IPage<WorekerPaymentListPo> workerPaymentList(Page page, @Param("workerId") String workerId);

    IPage<WorekerPaymentListPo> regulatorWorkerPaymentList(Page page, @Param("workerId") String workerId, @Param("paymentOrderIds") List<String> paymentOrderIds);

    IPage<WorekerPaymentListPo> selectRegulatorWorkerPaymentInfo(Page page, @Param("paymentOrderIds") List<String> paymentOrderIds, @Param("workerId") String workerId,
                                                                 @Param("companyName") String companyName, @Param("taxName") String taxName,
                                                                 @Param("startDate") String startDate, @Param("endDate") String endDate);

    List<WorekerPaymentListPo> exportRegulatorWorkerPaymentInfo(@Param("paymentOrderIds") List<String> paymentOrderIds, @Param("workerId") String workerId);

    List<WorkerVo> setWorkerMakeMoney();

    IPage<WorkerPassVo> getPaasCheckByTaskId(Page page, @Param("taskId") String taskId);

}
