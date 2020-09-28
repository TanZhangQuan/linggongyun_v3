package com.example.merchant.service.impl;

import com.example.common.util.ReturnJson;
import com.example.merchant.service.BillService;
import com.example.merchant.vo.MonthBillCountVO;
import com.example.mybatis.mapper.PaymentInventoryDao;
import com.example.mybatis.mapper.PaymentOrderDao;
import com.example.mybatis.mapper.PaymentOrderManyDao;
import com.example.mybatis.po.BillCountPO;
import com.example.mybatis.po.BillPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private PaymentInventoryDao paymentInventoryDao;

    @Autowired
    private PaymentOrderDao paymentOrderDao;

    @Autowired
    private PaymentOrderManyDao paymentOrderManyDao;

    /**
     * 总包月账单统计
     * @param year
     * @param month
     * @return
     */
    @Override
    public ReturnJson getTotalMonthBill(Integer year, Integer month) {
        MonthBillCountVO monthBillCountVO = new MonthBillCountVO();
        ReturnJson returnJson = this.getTotalMonthBillInfo( year, month);
        List<BillPO> billPOS = (List<BillPO>)returnJson.getData();
        BigDecimal monthBillMoney = new BigDecimal("0");
        for (BillPO billPO : billPOS) {
            monthBillMoney = monthBillMoney.add(billPO.getMoney());
        }
        monthBillCountVO.setMonthBillMoney(monthBillMoney);
        returnJson.setObj(monthBillCountVO);
        return returnJson;
    }

    /**
     * 查询总包月账单明细
     * @param year
     * @param month
     * @return
     */
    @Override
    public ReturnJson getTotalMonthBillInfo(Integer year, Integer month) {
        List<BillPO> billPOS = paymentOrderDao.selectMonthBill(year, month);
        return ReturnJson.success(billPOS);
    }

    /**
     * 众包月账单统计
     * @param year
     * @param month
     * @return
     */
    @Override
    public ReturnJson getManyMonthBill(Integer year, Integer month) {
        MonthBillCountVO monthBillCountVO = new MonthBillCountVO();
        ReturnJson returnJson = this.getManyMonthBillInfo( year, month);
        List<BillPO> billPOS = (List<BillPO>)returnJson.getData();
        BigDecimal monthBillMoney = new BigDecimal("0");
        for (BillPO billPO : billPOS) {
            monthBillMoney = monthBillMoney.add(billPO.getMoney());
        }
        monthBillCountVO.setMonthBillMoney(monthBillMoney);
        returnJson.setObj(monthBillCountVO);
        return returnJson;
    }

    /**
     * 查询众包月账单明细
     * @param year
     * @param month
     * @return
     */
    @Override
    public ReturnJson getManyMonthBillInfo(Integer year, Integer month) {
        List<BillPO> billPOS = paymentOrderManyDao.selectMonthBill(year, month);
        return ReturnJson.success(billPOS);
    }

    /**
     * 总包年账单统计
     * @param year
     * @return
     */
    @Override
    public ReturnJson getTotalYearBillCount(Integer year) {
        BillCountPO billCountPO = paymentOrderDao.selectYearCount(year);
        return ReturnJson.success(billCountPO);
    }

    /**
     * 众包年账单统计
     * @param year
     * @return
     */
    @Override
    public ReturnJson getManyYearBillCount(Integer year) {
        BillCountPO billCountPO = paymentOrderManyDao.selectYearCount(year);
        return ReturnJson.success(billCountPO);
    }
}
