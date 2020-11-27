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

    ReturnJson homeFourData(String regulatorId);

    ReturnJson listTax(RegulatorTaxDto regulatorTaxDto, String regulatorId);

    ReturnJson getTax(String taxId);

    ReturnJson batchExportTax(String taxIds, HttpServletResponse response);

    ReturnJson getPayInfo(PayInfoDto payInfoDto);

    ReturnJson batchExportPayInfo(String paymentOrderIds, HttpServletResponse response);

    ReturnJson getPaymentInventoryInfo(String paymentOrderId,Integer page,Integer pageSize);

    ReturnJson getPaymentOrderInfo(String paymentOrderId,Integer type);
}
