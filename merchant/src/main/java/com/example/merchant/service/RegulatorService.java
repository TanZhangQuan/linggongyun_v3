package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.RegulatorDto;
import com.example.merchant.dto.platform.RegulatorQueryDto;
import com.example.merchant.dto.platform.AddRegulatorTaxDto;
import com.example.merchant.dto.regulator.RegulatorMerchantDto;
import com.example.merchant.dto.regulator.RegulatorMerchantPaymentOrderDto;
import com.example.merchant.dto.regulator.RegulatorWorkerDto;
import com.example.merchant.dto.regulator.RegulatorWorkerPaymentDto;
import com.example.mybatis.entity.Regulator;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 监管部门 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-25
 */
public interface RegulatorService extends IService<Regulator> {

    ReturnJson addRegulator(RegulatorDto regulatorDto);

    ReturnJson updateRegulator(RegulatorDto regulatorDto);

    ReturnJson getByRegulatorId(Long regulatorId);

    ReturnJson getRegulatorQuery(RegulatorQueryDto regulatorQueryDto);

    ReturnJson getTaxAll(Integer page, Integer pageSize, String regulatorId);

    ReturnJson addRegulatorTax(List<AddRegulatorTaxDto> addRegulatorTaxDtos);

    ReturnJson getRegultorPaymentCount(String regulatorId);

    ReturnJson getRegulatorTaxAll(String regulatorId, Integer page, Integer pageSize);

    ReturnJson getRegulatorTaxPaymentList(String taxId, Integer page, Integer pageSize);

    ReturnJson getPaymentInfo(String paymentOrderId, Integer packageStatus);

    ReturnJson getPaymentInventoryInfo(String paymentOrderId, Integer page, Integer pageSize);

    ReturnJson getRegulatorWorker(RegulatorWorkerDto regulatorWorkerDto, String regulatorId);

    ReturnJson exportRegulatorWorker(String workerIds, String regulatorId, HttpServletResponse response);

    ReturnJson countRegulatorWorker(String regulatorId);

    ReturnJson countRegulatorWorkerInfo(String regulatorId, String workerId);

    ReturnJson getRegulatorWorkerPaymentInfo(RegulatorWorkerPaymentDto regulatorWorkerPaymentDto, String regulatorId);

    ReturnJson exportRegulatorWorkerPaymentInfo(String workerId, String paymentIds, HttpServletResponse response);

    ReturnJson getPaymentOrderInfo(String workerId, String paymentId, Integer packageStatus);

    ReturnJson getPaymentInventory(String paymentOrderId, Integer page, Integer pageSize);

    ReturnJson getRegulatorMerchant(RegulatorMerchantDto regulatorMerchantDto, String regulatorId);

    ReturnJson exportRegulatorMerchant(String companyIds, String regulatorId, HttpServletResponse response);

    ReturnJson getCountRegulatorMerchant(String regulatorId);

    ReturnJson getRegulatorMerchantParticulars(String companyId, String regulatorId);

    ReturnJson getRegulatorMerchantPaymentOrder(RegulatorMerchantPaymentOrderDto regulatorMerchantPaymentOrderDto, String regulatorId);

    ReturnJson exportRegulatorMerchantPaymentOrder(String paymentOrderIds, HttpServletResponse response);

    ReturnJson regulatorLogin(String username, String password, HttpServletResponse response);

    ReturnJson regulatorLogout(String regulatorId);

    /**
     * 修改监管状态
     *
     * @param taxId
     * @param regulatorId
     * @param status
     * @return
     */
    ReturnJson updateRegulatorTaxStatus(String taxId, String regulatorId, Integer status);
}
