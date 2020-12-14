package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.regulator.PayInfoDto;
import com.example.mybatis.dto.RegulatorTaxDto;
import com.example.mybatis.entity.RegulatorTax;
import org.apache.http.HttpRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 监管部门监管的服务商 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-25
 */
public interface RegulatorTaxService extends IService<RegulatorTax> {

    /**
     * 监管服务商上方的四个数据
     *
     * @param regulatorId
     * @return
     */
    ReturnJson homeFourData(String regulatorId);

    /**
     * 查询服务商列表
     *
     * @param regulatorTaxDto
     * @return
     */
    ReturnJson listTax(RegulatorTaxDto regulatorTaxDto, String regulatorId);

    /**
     * 查询服务商信息
     *
     * @param taxId
     * @return
     */
    ReturnJson getTax(String taxId);

    /**
     * 批量导出服务商信息
     *
     * @param taxIds 服务商id
     * @return
     */
    ReturnJson batchExportTax(String taxIds, HttpServletResponse response);

    /**
     * 支付订单信息
     *
     * @param payInfoDto
     * @return
     */
    ReturnJson getPayInfo(PayInfoDto payInfoDto);

    /**
     * 导出支付订单信息
     *
     * @param paymentOrderIds
     * @param response
     * @return
     */
    ReturnJson batchExportPayInfo(String paymentOrderIds, HttpServletResponse response);

    /**
     * 支付清单明细查询
     *
     * @param paymentOrderId
     * @param page
     * @param pageSize
     * @return
     */
    ReturnJson getPaymentInventoryInfo(String paymentOrderId, Integer page, Integer pageSize);

    /**
     * 支付信息  以及  支付方信息
     *
     * @param paymentOrderId 支付id
     * @param type           合作类型
     * @return
     */
    ReturnJson getPaymentOrderInfo(String paymentOrderId, Integer type);
}
