package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.AgentLadderServiceService;
import com.example.merchant.service.AgentTaxService;
import com.example.merchant.service.InvoiceLadderPriceService;
import com.example.merchant.service.TaxPackageService;
import com.example.mybatis.entity.AgentTax;
import com.example.mybatis.entity.TaxPackage;
import com.example.mybatis.mapper.AgentTaxDao;
import com.example.mybatis.vo.CompanyInvoiceLadderPriceDetailVO;
import com.example.mybatis.vo.CompanyPackageDetailVO;
import com.example.mybatis.vo.InvoiceLadderPriceDetailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jun.
 * @date 2021/1/29.
 * @time 15:35.
 */
@Service
public class AgentTaxServiceImpl extends ServiceImpl<AgentTaxDao, AgentTax> implements AgentTaxService {

    @Resource
    private TaxPackageService taxPackageService;

    @Resource
    private InvoiceLadderPriceService invoiceLadderPriceService;

    @Resource
    private AgentLadderServiceService agentLadderServiceService;

    @Resource
    private AgentTaxDao agentTaxDao;

    @Override
    public ReturnJson queryAgentPackage(String taxId, String agentId, Integer packageStatus) {
        CompanyPackageDetailVO companyPackageDetailVO = new CompanyPackageDetailVO();
        TaxPackage taxPackage = taxPackageService.queryTaxPackage(taxId, packageStatus);
        if (taxPackage != null) {
            //服务商一口价综合税费率
            companyPackageDetailVO.setTaxPrice(taxPackage.getTaxPrice());
            //获取服务商梯度价
            List<CompanyInvoiceLadderPriceDetailVO> companyInvoiceLadderPriceDetailVOList = new ArrayList<>();
            List<InvoiceLadderPriceDetailVO> invoiceLadderPriceDetailVOList = invoiceLadderPriceService.queryInvoiceLadderPriceDetailVOList(taxPackage.getId());
            if (invoiceLadderPriceDetailVOList != null && invoiceLadderPriceDetailVOList.size() > 0) {
                for (InvoiceLadderPriceDetailVO invoiceLadderPriceDetailVO : invoiceLadderPriceDetailVOList) {
                    CompanyInvoiceLadderPriceDetailVO companyInvoiceLadderPriceDetailVO = new CompanyInvoiceLadderPriceDetailVO();
                    BeanUtils.copyProperties(invoiceLadderPriceDetailVO, companyInvoiceLadderPriceDetailVO);
                    companyInvoiceLadderPriceDetailVOList.add(companyInvoiceLadderPriceDetailVO);
                }
            }
//            QueryWrapper<AgentTax> queryWrapper = new QueryWrapper<>();
//            queryWrapper.lambda().eq(AgentTax::getTaxId, taxId).eq(AgentTax::getAgentId, agentId).eq(AgentTax::getPackageStatus, packageStatus);
//            AgentTax agentTax = baseMapper.selectOne(queryWrapper);
//            if (agentTax != null) {
//                //商户一口价综合税费率
//                companyPackageDetailVO.setCompanyPrice(agentTax.getServiceCharge());
//
//                //获取商户梯度价税率
//                if (companyInvoiceLadderPriceDetailVOList.size() > 0) {
//                    for (CompanyInvoiceLadderPriceDetailVO companyInvoiceLadderPriceDetailVO : companyInvoiceLadderPriceDetailVOList) {
//                        BigDecimal serviceCharge = agentLadderServiceService.queryServiceCharge(agentTax.getId(), companyInvoiceLadderPriceDetailVO.getStartMoney(), companyInvoiceLadderPriceDetailVO.getEndMoney());
//                        companyInvoiceLadderPriceDetailVO.setCompanyRate(serviceCharge);
//                    }
//                }
//
//            }

        }

        return ReturnJson.success(companyPackageDetailVO);
    }

    @Override
    public AgentTax queryAgentTax(String taxId, String agentId) {
        QueryWrapper<AgentTax> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgentTax::getAgentId,agentId).eq(AgentTax::getTaxId,taxId);
        return agentTaxDao.selectOne(queryWrapper);
    }
}
