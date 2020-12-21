package com.example.merchant.vo.platform;

import com.example.mybatis.entity.InvoiceLadderPrice;
import com.example.mybatis.entity.TaxPackage;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Data
@ApiModel(description = "添加服务商")
public class TaxPlatformVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商ID
     */
    private String id;

    /**
     * 公司的简称
     */
    private String taxSName;

    /**
     * 公司的法定人
     */
    private String taxMan;

    /**
     * 公司的营业执照
     */
    private String businessLicense;

    /**
     * 公司全称
     */
    private String taxName;

    /**
     * 公司的详细地址
     */
    private String taxAddress;

    /**
     * 公司的成立时间
     */
    private LocalDateTime taxCreateDate;

    /**
     * 公司联系人
     */
    private String linkMan;

    /**
     * 公司联系电话
     */
    private String linkMobile;

    /**
     * 统一的社会信用代码
     */
    private String creditCode;

    /**
     * 公司状态0正常，1停用
     */
    private Integer taxStatus;

    /**
     * 总包信息
     */
    private TaxPackage totalTaxPackage;

    /**
     * 众包信息
     */
    private TaxPackage manyTaxPackage;

    /**
     * 总包税率梯度价
     */
    private List<InvoiceLadderPrice> totalLadders;

    /**
     * 众包税率梯度价
     */
    private List<InvoiceLadderPrice> manyLadders;
}
