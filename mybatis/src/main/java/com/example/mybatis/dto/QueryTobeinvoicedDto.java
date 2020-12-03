package com.example.mybatis.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestAttribute;

import javax.validation.constraints.Min;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/2
 */
@Data
public class QueryTobeinvoicedDto {
    @ApiModelProperty(notes = "平台服务商",value = "平台服务商")
    private String platformServiceProvider;

    @ApiModelProperty(notes = "创建时间1,yyyy:HH:mm",value = "创建时间1,yyyy:HH:mm")
    private String applicationDateOne;

    @ApiModelProperty(notes = "创建时间2,yyyy:HH:mm",value = "创建时间2,yyyy:HH:mm")
    private String applicationDateTwo;

    @ApiModelProperty(notes = "当前页默认第一页",value = "当前页默认第一页")
    @Min(value = 1,message = "当前页最小为1")
    private Integer pageNo=1;

    @ApiModelProperty(notes = "每页大小默认为10",value = "每页大小默认为10")
    private Integer pageSize=10;
}
