package com.example.mybatis.vo;

import com.example.mybatis.entity.Industry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "行业列表")
public class IndustryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "行业Id")
    private String id;

    @ApiModelProperty(value = "行业类型")
    private String industryType;

    @ApiModelProperty(value = "行业")
    private List<Industry> list;
}
