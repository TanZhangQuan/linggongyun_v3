package com.example.merchant.vo.regulator;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/23
 */
@Data
@ApiModel(description = "监管人员信息")
public class RegulatorInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 监管部门名称
     */
    private String regulatorName;

    /**
     * 地址
     */
    private String address;

    /**
     * 联系人
     */
    private String linkman;

    /**
     * 联系电话
     */
    private String linkMobile;

    /**
     * 登录账号
     */
    private String userName;

    /**
     * 状态0启用，1停用
     */
    private Integer status;
}
