package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import org.apache.ibatis.annotations.Param;

public interface BillService {

    /**
     * 总包月账单统计
     *
     * @param workerId
     * @param year
     * @param month
     * @return
     */
    ReturnJson getTotalMonthBill(String workerId, Integer year, Integer month);

    /**
     * 查询总包月账单明细
     *
     * @param workerId
     * @param year
     * @param month
     * @return
     */
    ReturnJson getTotalMonthBillInfo(String workerId, Integer year, Integer month);

    /**
     * 众包月账单统计
     *
     * @param workerId
     * @param year
     * @param month
     * @return
     */
    ReturnJson getManyMonthBill(String workerId, Integer year, Integer month);

    /**
     * 查询众包月账单明细
     *
     * @param workerId
     * @param year
     * @param month
     * @return
     */
    ReturnJson getManyMonthBillInfo(String workerId, Integer year, Integer month);

    /**
     * 总包年账单统计
     *
     * @param workerId
     * @param year
     * @return
     */
    ReturnJson getTotalYearBillCount(String workerId, Integer year);

    /**
     * 众包年账单统计
     *
     * @param workerId
     * @param year
     * @return
     */
    ReturnJson getManyYearBillCount(String workerId, Integer year);

    /**
     * 账单明细
     *
     * @param workerId
     * @param id
     * @param isNot
     * @return
     */
    ReturnJson queryBillInfo(String workerId, String id, Integer isNot);
}
