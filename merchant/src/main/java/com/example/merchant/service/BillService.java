package com.example.merchant.service;

import com.example.common.util.ReturnJson;

public interface BillService {

    ReturnJson getTotalMonthBill(Integer year, Integer month);

    ReturnJson getTotalMonthBillInfo(Integer year, Integer month);

    ReturnJson getManyMonthBill(Integer year, Integer month);

    ReturnJson getManyMonthBillInfo(Integer year, Integer month);

    ReturnJson getTotalYearBillCount(Integer year);

    ReturnJson getManyYearBillCount(Integer year);
}
