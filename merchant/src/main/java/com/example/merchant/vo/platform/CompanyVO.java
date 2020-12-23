package com.example.merchant.vo.platform;

import com.example.mybatis.vo.CooperationInfoVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "XXXXX")
public class CompanyVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公司基本信息")
    private CompanyInfoVO companyInfoVo;

    @ApiModelProperty(value = "公司的开票信息")
    private QueryInvoiceInfoVO queryInvoiceInfoVo;

    @ApiModelProperty(value = "公司的账户信息")
    private QueryMerchantInfoVO queryMerchantInfoVo;

    @ApiModelProperty(value = "公司的组织结构信息")
    private QueryCooperationInfoVO queryCooperationInfoVo;

    @ApiModelProperty(value = "服务商合作信息")
    private List<CooperationInfoVO> cooperationInfoVOList;
}
