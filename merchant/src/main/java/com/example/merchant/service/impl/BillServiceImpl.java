package com.example.merchant.service.impl;

import com.example.common.util.ReturnJson;
import com.example.merchant.service.BillService;
import com.example.merchant.vo.makerend.MonthBillCountVO;
import com.example.mybatis.mapper.PaymentInventoryDao;
import com.example.mybatis.mapper.PaymentOrderDao;
import com.example.mybatis.mapper.PaymentOrderManyDao;
import com.example.mybatis.po.BillCountPO;
import com.example.mybatis.po.BillPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    @Resource
    private PaymentOrderDao paymentOrderDao;

    @Resource
    private PaymentOrderManyDao paymentOrderManyDao;

    /**
     * 总包月账单统计
     *
     * @param year
     * @param month
     * @return
     */
    @Override
    public ReturnJson getTotalMonthBill(String workerId, Integer year, Integer month) {
        MonthBillCountVO monthBillCountVO = new MonthBillCountVO();
        ReturnJson returnJson = this.getTotalMonthBillInfo(workerId, year, month);
        List<BillPO> billPOS = (List<BillPO>) returnJson.getData();
        BigDecimal monthBillMoney = new BigDecimal("0");
        for (BillPO billPO : billPOS) {
            monthBillMoney = monthBillMoney.add(billPO.getMoney());
        }
        monthBillCountVO.setMonthOrderCount(billPOS.size());
        monthBillCountVO.setMonthBillMoney(monthBillMoney);
        returnJson.setObj(monthBillCountVO);
        return returnJson;
    }

    /**
     * 查询总包月账单明细
     *
     * @param year
     * @param month
     * @return
     */
    @Override
    public ReturnJson getTotalMonthBillInfo(String workerId, Integer year, Integer month) {
        List<BillPO> billPOS = paymentOrderDao.selectMonthBill(workerId, year, month, null);
        return ReturnJson.success(billPOS);
    }

    /**
     * 众包月账单统计
     *
     * @param year
     * @param month
     * @return
     */
    @Override
    public ReturnJson getManyMonthBill(String workerId, Integer year, Integer month) {
        MonthBillCountVO monthBillCountVO = new MonthBillCountVO();
        ReturnJson returnJson = this.getManyMonthBillInfo(workerId, year, month);
        List<BillPO> billPOS = (List<BillPO>) returnJson.getData();
        BigDecimal monthBillMoney = new BigDecimal("0");
        for (BillPO billPO : billPOS) {
            monthBillMoney = monthBillMoney.add(billPO.getMoney());
        }
        monthBillCountVO.setMonthOrderCount(billPOS.size());
        monthBillCountVO.setMonthBillMoney(monthBillMoney);
        returnJson.setObj(monthBillCountVO);
        return returnJson;
    }

    /**
     * 查询众包月账单明细
     *
     * @param year
     * @param month
     * @return
     */
    @Override
    public ReturnJson getManyMonthBillInfo(String workerId, Integer year, Integer month) {
        List<BillPO> billPOS = paymentOrderManyDao.selectMonthBill(workerId, year, month);
        return ReturnJson.success(billPOS);
    }

    /**
     * 总包年账单统计
     *
     * @param year
     * @return
     */
    @Override
    public ReturnJson getTotalYearBillCount(String workerId, Integer year) {
        BillCountPO billCountPO = paymentOrderDao.selectYearCount(workerId, year);
        return ReturnJson.success(billCountPO);
    }

    /**
     * 众包年账单统计
     *
     * @param year
     * @return
     */
    @Override
    public ReturnJson getManyYearBillCount(String workerId, Integer year) {
        BillCountPO billCountPO = paymentOrderManyDao.selectYearCount(workerId, year);
        return ReturnJson.success(billCountPO);
    }

    @Override
    public ReturnJson queryBillInfo(String workerId, String id, Integer isNot) {
        BillPO billPO;
        if (isNot == 0) {
            billPO = paymentOrderDao.queryBillInfo(workerId, id);
        } else {
            billPO = paymentOrderManyDao.queryBillInfo(workerId, id);
        }
        return ReturnJson.success(billPO);
    }
}
