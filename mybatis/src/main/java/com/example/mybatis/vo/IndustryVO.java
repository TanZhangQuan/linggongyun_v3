package com.example.mybatis.vo;

import com.example.mybatis.entity.Industry;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "行业列表")
@Data
public class IndustryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 行业ID
     */
    private String id;

    /**
     * 行业类型
     */
    private String industryType;

    private List<Industry> list;
}
