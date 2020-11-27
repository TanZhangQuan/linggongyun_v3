package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 联系人表
 * </p>
 *
 * @author hzp
 * @since 2020-09-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_linkman")
public class Linkman extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 公司ID
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
     * 职位
     */
    private String post;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否默认：0为默认，1为不默认
     */
    private Integer isNot;

    /**
     * 0为启用，1为停用
     */
    private Integer status;

}
