package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.entity.Worker;
import com.example.mybatis.po.WorekerPaymentListPo;
import com.example.mybatis.po.WorkerPo;
import com.example.mybatis.vo.WorkerVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

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
     * @return
     */
    List<WorkerPo> getWorkerByTaskId(String taskId,RowBounds rowBounds);

    /**
     * 验收已接单创客明细
     * @param taskId
     * @param rowBounds
     * @return
     */
    List<WorkerPo> getCheckByTaskId(String taskId,RowBounds rowBounds);

    IPage<Worker> selectByIdAndAccountNameAndMobile(Page page,@Param("merchantId") String merchantId, @Param("id") String id, @Param("accountName")String accountName, @Param("mobileCode") String mobileCode);
    List<Worker> selectByIdAndAccountNameAndMobilePaas(@Param("merchantIds") List<String> merchantIds, @Param("id") String id, @Param("accountName")String accountName, @Param("mobileCode") String mobileCode);
    List<Worker> selectByIdAndAccountNameAndMobilePaasNot(@Param("merchantIds") List<String> merchantIds, @Param("id") String id, @Param("accountName")String accountName, @Param("mobileCode") String mobileCode);
    IPage<Worker> selectWorkerAllNot(Page page, @Param("merchantIds")List<String> merchantIds);
    IPage<Worker> selectWorkerAll(Page page, @Param("merchantIds")List<String> merchantIds);
    IPage<WorekerPaymentListPo> workerPaymentList(Page page, @Param("workerId")String workerId);

    List<WorkerVo> setWorkerMakeMoney();
}
