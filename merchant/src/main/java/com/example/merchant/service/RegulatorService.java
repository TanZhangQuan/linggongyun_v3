package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.AddRegulatorTaxDTO;
import com.example.merchant.dto.platform.RegulatorDTO;
import com.example.merchant.dto.platform.RegulatorQueryDTO;
import com.example.merchant.dto.regulator.RegulatorMerchantDTO;
import com.example.merchant.dto.regulator.RegulatorMerchantPaymentOrderDTO;
import com.example.merchant.dto.regulator.RegulatorWorkerDTO;
import com.example.merchant.dto.regulator.RegulatorWorkerPaymentDTO;
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

    /**
     * 添加监管部门
     *
     * @param regulatorDto
     * @return
     */
    ReturnJson addRegulator(RegulatorDTO regulatorDto);

    /**
     * 编辑监管部门
     *
     * @param regulatorDto
     * @return
     */
    ReturnJson updateRegulator(RegulatorDTO regulatorDto);

    /**
     * 按ID查询监管部门
     *
     * @param regulatorId
     * @return
     */
    ReturnJson getByRegulatorId(Long regulatorId);

    /**
     * 按条件查询监管部门
     *
     * @param regulatorQueryDto
     * @return
     */
    ReturnJson getRegulatorQuery(RegulatorQueryDTO regulatorQueryDto);

    /**
     * 查询服务商
     *
     * @param page
     * @param pageSize
     * @return
     */
    ReturnJson getTaxAll(Integer page, Integer pageSize, String regulatorId);

    /**
     * 添加监管服务商
     *
     * @param addRegulatorTaxDTOS
     * @return
     */
    ReturnJson addRegulatorTax(List<AddRegulatorTaxDTO> addRegulatorTaxDTOS);

    /**
     * 查询监管部门监管的服务商交易统计
     *
     * @param regulatorId
     * @return
     */
    ReturnJson getRegultorPaymentCount(String regulatorId);

    /**
     * 查看监管服务商
     *
     * @param regulatorId
     * @return
     */
    ReturnJson getRegulatorTaxAll(String regulatorId, Integer page, Integer pageSize);

    /**
     * 查看监管服务商的成交明细
     *
     * @param taxId
     * @param page
     * @param pageSize
     * @return
     */
    ReturnJson getRegulatorTaxPaymentList(String taxId, Integer page, Integer pageSize);

    /**
     * 查看成交订单
     *
     * @param paymentOrderId
     * @param packageStatus
     * @return
     */
    ReturnJson getPaymentInfo(String paymentOrderId, Integer packageStatus);

    /**
     * 查看支付清单
     *
     * @param paymentOrderId
     * @param page
     * @param pageSize
     * @return
     */
    ReturnJson getPaymentInventoryInfo(String paymentOrderId, Integer page, Integer pageSize);

    /**
     * 按条件查询所监管的创客
     *
     * @param regulatorWorkerDto
     * @param regulatorId
     * @return
     */
    ReturnJson getRegulatorWorker(RegulatorWorkerDTO regulatorWorkerDto, String regulatorId);

    /**
     * 导出创客
     *
     * @param workerIds
     * @return
     */
    ReturnJson exportRegulatorWorker(String workerIds, String regulatorId, HttpServletResponse response);

    /**
     * 获取所监管的创客的流水统计
     *
     * @param regulatorId
     * @return
     */
    ReturnJson countRegulatorWorker(String regulatorId);

    /**
     * 获取所监管的创客的详情
     *
     * @param regulatorId
     * @param workerId
     * @return
     */
    ReturnJson countRegulatorWorkerInfo(String regulatorId, String workerId);

    /**
     * 查询所监管创客的支付明细
     *
     * @param regulatorWorkerPaymentDto
     * @return
     */
    ReturnJson getRegulatorWorkerPaymentInfo(RegulatorWorkerPaymentDTO regulatorWorkerPaymentDto, String regulatorId);

    /**
     * 导出所监管的创客支付明细
     *
     * @param workerId
     * @param paymentIds
     * @param response
     * @return
     */
    ReturnJson exportRegulatorWorkerPaymentInfo(String workerId, String paymentIds, HttpServletResponse response);

    /**
     * 查询支付订单详情
     *
     * @param workerId
     * @param paymentId
     * @param packageStatus
     * @return
     */
    ReturnJson getPaymentOrderInfo(String workerId, String paymentId, Integer packageStatus);

    /**
     * 分页查询支付明细
     *
     * @param paymentOrderId
     * @param page
     * @param pageSize
     * @return
     */
    ReturnJson getPaymentInventory(String paymentOrderId, Integer page, Integer pageSize);

    /**
     * 按条件查询所监管的商户
     *
     * @param regulatorMerchantDto
     * @param regulatorId
     * @return
     */
    ReturnJson getRegulatorMerchant(RegulatorMerchantDTO regulatorMerchantDto, String regulatorId);

    /**
     * 导出所监管的商户
     *
     * @param companyIds
     * @param regulatorId
     * @param response
     * @return
     */
    ReturnJson exportRegulatorMerchant(String companyIds, String regulatorId, HttpServletResponse response);

    /**
     * 统计所监管的商户的流水
     *
     * @param regulatorId
     * @return
     */
    ReturnJson getCountRegulatorMerchant(String regulatorId);

    /**
     * 查询所监管的公司详情
     *
     * @param companyId
     * @param regulatorId
     * @return
     */
    ReturnJson getRegulatorMerchantParticulars(String companyId, String regulatorId);

    /**
     * 查询所监管商户的支付订单
     *
     * @param regulatorMerchantPaymentOrderDto
     * @return
     */
    ReturnJson getRegulatorMerchantPaymentOrder(RegulatorMerchantPaymentOrderDTO regulatorMerchantPaymentOrderDto, String regulatorId);

    /**
     * 导出所监管商户的支付订单
     *
     * @param paymentOrderIds
     * @return
     */
    ReturnJson exportRegulatorMerchantPaymentOrder(String paymentOrderIds, HttpServletResponse response);

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @param response
     * @return
     */
    ReturnJson regulatorLogin(String username, String password, HttpServletResponse response);

    /**
     * 登出
     *
     * @param regulatorId 监督用户Id
     * @return
     */
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

    /**
     * 获取监管人员信息
     *
     * @param regulatorId
     * @return
     */
    ReturnJson getRegulatorInfo(String regulatorId);

}
