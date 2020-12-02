package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 地址表
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_address")
public class Address extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    private String companyId;

    /**
     * 联系人
     */
    private String linkName;

    /**
     * 联系电话
     */
    private String linkMobile;

    /**
     * 详细地址
     */
    private String addressName;

    /**
     * 0为启用，1为停用
     */
    private Integer status;

    /**
     * 是否默认：0为默认，1为不默认
     */
    private Integer isNot;

}
