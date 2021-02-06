package com.example.mybatis.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author .
 * @date 2021/2/2.
 * @time 15:43.
 */
@Data
public class TaxPackageVO  implements Serializable {
    /**
     * 服务商
     */
    private String taxId;

    /**
     * 一口价综合税费率
     */
    private BigDecimal taxPrice;

    /**
     * 合作类型 0总包 1众包
     */
    private Integer packageStatus;

    /**
     * 支持的类目ID 逗号分隔 全量更新
     */
    private String supportCategory;

    /**
     * 梯度价
     */
    private List<InvoiceLadderPriceDetailVO> invoiceLadderPriceDetailVOS;
}
