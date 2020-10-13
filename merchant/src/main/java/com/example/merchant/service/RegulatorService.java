package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.RegulatorDto;
import com.example.merchant.dto.platform.RegulatorQueryDto;
import com.example.merchant.dto.platform.RegulatorTaxDto;
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

    ReturnJson getTaxAll(Integer page, Integer pageSize);

    ReturnJson addRegulatorTax(List<RegulatorTaxDto> regulatorTaxDtos);

    ReturnJson getRegultorPaymentCount(String regulatorId);

    ReturnJson getRegulatorTaxAll(String regulatorId, Integer page, Integer pageSize);

    ReturnJson getRegulatorTaxPaymentList(String taxId, Integer page, Integer pageSize);

    ReturnJson getPaymentInfo(String paymentOrderId, Integer packageStatus);

    ReturnJson getPaymentInventoryInfo(String paymentOrderId, Integer page, Integer pageSize);

    ReturnJson getRegulatorWorker(RegulatorWorkerDto regulatorWorkerDto);

    ReturnJson exportRegulatorWorker(String workerIds, String regulatorId, HttpServletResponse response);

    ReturnJson countRegulatorWorker(String regulatorId);

    ReturnJson countRegulatorWorkerInfo(String regulatorId, String workerId);

    ReturnJson getRegulatorWorkerPaymentInfo(RegulatorWorkerPaymentDto regulatorWorkerPaymentDto);

    ReturnJson exportRegulatorWorkerPaymentInfo(String workerId, String paymentIds, HttpServletResponse response);

    ReturnJson getPaymentOrderInfo(String workerId, String paymentId, Integer packageStatus);

    ReturnJson getPaymentInventory(String paymentOrderId, Integer page, Integer pageSize);
}
