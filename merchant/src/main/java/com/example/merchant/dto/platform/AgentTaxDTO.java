package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author .
 * @date 2021/1/29.
 * @time 16:27.
 */
@Data
@ApiModel(description = "服务商合作信息DTO")
public class AgentTaxDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * AgentTaxVO 里面的id
     */
    private String id;

    @ApiModelProperty(value = "公司ID")
    private String agentId;

    @ApiModelProperty(value = "服务商ID")
    @NotBlank(message = "服务商ID不能为空")
    private String taxId;

    @ApiModelProperty(value = "费用类型0为一口价，1为梯度价")
    @NotNull(message = "费用类型不能为空")
    private Integer chargeStatus;

    @ApiModelProperty(value = "一口价的服务费(如果为梯度价这为空)")
    @Min(value = 0,message = "服务费率不能小于零")
    private BigDecimal serviceCharge;

    @ApiModelProperty(value = "合作类型")
    @NotNull(message = "合作类型不能为空")
    private Integer packageStatus;

    @ApiModelProperty(value = "合作合同地址")
    @NotBlank(message = "合同地址不能为空")
    private String contract;

    @ApiModelProperty(value = "梯度价信息")
    private List<AddAgentLadderServiceDTO> addCompanyLadderServiceDtoList;
}
