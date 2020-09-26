package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.RegulatorDto;
import com.example.merchant.dto.RegulatorQueryDto;
import com.example.merchant.dto.RegulatorTaxDto;
import com.example.mybatis.entity.Regulator;

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


}
