package com.example.common.lianlianpay.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liuwm@lianlianpay.com
 * @description 代发结果查询接口
 * @date 2019/8/15 20:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuerySingleOrderRequest extends BaseRequestBean {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 商户订单号 C String（32） 商户唯一订单号
     */
    private String order_no;
    /**
     * 连薪订单号 C String（24） 连薪唯一订单号
     */
    private String salary_id;

    /** api当前版本号 . */
    private String api_version = "1.0";

    /**
     * 连薪订单号 C String（24） 连薪唯一订单号
     */
    @Override
    public String validateLogic() {
        return null;
    }
}
