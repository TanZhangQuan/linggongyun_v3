package com.example.merchant.dto.platform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "添加代理商的参数")
public class AgentInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "代理商ID")
    private String agentId;

    @ApiModelProperty(value = "代理商名称", required = true)
    @NotBlank(message = "代理商名称不能为空")
    private String agentName;

    @ApiModelProperty(value = "登录用户名", required = true)
    @NotBlank(message = "登录用户名不能为空")
    private String userName;

    @ApiModelProperty(value = "联系人", required = true)
    @NotBlank(message = "联系人不能为空")
    private String linkMan;

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11, message = "请输入11位手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
    private String linkMobile;

    @ApiModelProperty(value = "加盟合同", required = true)
    @NotBlank(message = "加盟合同不能为空")
    private String contractFile;

    @ApiModelProperty(value = "所属业务员ID", required = true)
    @NotBlank(message = "所属业务员ID不能为空")
    private String salesManId;

    @ApiModelProperty(value = "初始密码", required = true)
    private String initPassWord;

    @ApiModelProperty(value = "状态", required = true)
    private Integer agentStatus = 0;

    @ApiModelProperty(value = "1代理商", hidden = true)
    @JsonIgnore
    private Integer userSign = 1;

    @ApiModelProperty(value = "直客的流水结算梯度", required = true)
    private List<CommissionProportionDTO> directCommissionProportion;

    @ApiModelProperty(value = "服务商合作信息")
    @Valid
    List<AgentTaxDTO> agentTaxDtos;

}
