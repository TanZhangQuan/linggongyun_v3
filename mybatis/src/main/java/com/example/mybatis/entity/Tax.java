package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 合作园区信息
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_tax")
public class Tax implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 税源地公司id
     */

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 管理人员ID
     */
    private String managersId;

    /**
     */
    @ApiModelProperty("平台服务商名称")
    private String taxName;

    /**
     * 服务费率
     */
    @ApiModelProperty("综合税费率")
    private BigDecimal taxPrice;

    /**
     * 收款方户名
     */
    @ApiModelProperty("收款方户名")
    private String payee;

    /**
     * 税源地公司的主公司
     */
    private String taxCompany;

    /**
     * 银行名称
     */
    @ApiModelProperty("银行名称")
    private String bankName;

    /**
     * 银行账号
     */
    @ApiModelProperty("银行账号")
    private String bankCode;

    /**
     * 状态 1 启用 0  停用
     */
    private Integer taxStatus;

    /**
     * 0总包，1众包
     */
    @ApiModelProperty("0总包服务商，1众包服务商")
    private Integer packageStatus;

    /**
     * 支持的类目 逗号分隔 全量更新
     */
    private String supportCategory;

    /**
     * 统一社会信用代码
     */
    @ApiModelProperty("统一社会信用代码（纳税识别号）")
    private String socialCode;

    /**
     * 服务费率信息
     */
    private String rateInfo;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;


}
