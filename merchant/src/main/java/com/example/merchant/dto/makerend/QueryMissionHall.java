package com.example.merchant.dto.makerend;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Min;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/11/18
 */
@Data
public class QueryMissionHall {

    @ApiParam(value = "任务行业类型")
    private String industryType;

    @Min(value = 1)
    private Integer pageNo=1;

    private Integer pageSize=10;
}
