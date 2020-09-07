package com.example.mybatis.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 税源地公司id
     */
    private String taxName;

    /**
     * 服务费率
     */
    private BigDecimal taxPrice;

    /**
     * 税源地公司的主公司
     */
    private String taxCompany;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行账号
     */
    private String bankCode;

    /**
     * 状态 1 启用 0  停用
     */
    private Integer taxStatus;

    /**
     * 总包
     */
    private BigDecimal totalTackage;

    /**
     * 众包
     */
    private BigDecimal partPackage;

    /**
     * 支持的类目 逗号分隔 全量更新
     */
    private String supportCategory;

    /**
     * 统一社会信用代码
     */
    private String socialCode;

    /**
     * 服务费率信息
     */
    private String rateInfo;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    private LocalDateTime updateDate;


}
