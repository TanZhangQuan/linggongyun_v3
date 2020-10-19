package com.example.mybatis.vo;

import com.example.mybatis.entity.Industry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "行业列表")
@Data
public class IndustryVo {
    @ApiModelProperty(notes = "行业Id",value = "行业Id")
    private String id;

    @ApiModelProperty(notes = "行业类型",value = "行业类型")
    private String industryType;

    private List<Industry> list;
}
