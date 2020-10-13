package com.example.mybatis.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "代理商信息")
public class AgentListPO {
    @ApiModelProperty(notes = "代理商ID", value = "代理商ID")
    private String agentId;

    @ApiModelProperty(notes = "代理商名称", value = "代理商名称")
    private String agentName;

    @ApiModelProperty(notes = "登录用户名", value = "登录用户名")
    private String userName;

    @ApiModelProperty(notes = "联系人", value = "联系人")
    private String linkMan;

    @ApiModelProperty(notes = "联系电话", value = "联系电话")
    private String linkMobile;

    @ApiModelProperty(notes = "加盟合同", value = "加盟合同")
    private String contractFile;

    @ApiModelProperty(notes = "业务员名称", value = "业务员名称")
    private String salesManName;

    @ApiModelProperty(notes = "状态", value = "状态")
    private Integer status;

    @ApiModelProperty(notes = "创建时间", value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
}
