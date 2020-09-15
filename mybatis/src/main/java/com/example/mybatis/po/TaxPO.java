package com.example.mybatis.po;

import com.example.mybatis.entity.Tax;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "服务商信息")
public class TaxPO extends Tax {
    /**
     * 合作合同地址
     */
    @ApiModelProperty("合作合同地址")
    private String contract;
}
