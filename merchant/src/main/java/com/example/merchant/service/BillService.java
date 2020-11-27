package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import org.apache.ibatis.annotations.Param;

public interface BillService {

    ReturnJson getTotalMonthBill(String workerId,Integer year, Integer month);

    ReturnJson getTotalMonthBillInfo(String workerId,Integer year, Integer month);

    ReturnJson getManyMonthBill(String workerId,Integer year, Integer month);

    ReturnJson getManyMonthBillInfo(String workerId,Integer year, Integer month);

    ReturnJson getTotalYearBillCount(String workerId,Integer year);

    ReturnJson getManyYearBillCount(String workerId,Integer year);
}
