package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity
 *
 * @author liangfeihu
 * @since 2020-10-27 15:46:36
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_lianlianpay")
public class Lianlianpay extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商户的企业ID
     */
    private String companyId;

    /**
     * 连连商户号
     */
    private String oidPartner;

    /**
     * 连连商户号的私钥
     */
    private String privateKey;

}
