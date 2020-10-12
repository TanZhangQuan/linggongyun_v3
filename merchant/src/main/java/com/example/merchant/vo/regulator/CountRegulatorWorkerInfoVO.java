package com.example.merchant.vo.regulator;

import com.example.mybatis.entity.Worker;
import com.example.mybatis.po.WorekerPaymentListPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "创客详情")
public class CountRegulatorWorkerInfoVO {
    @ApiModelProperty(value = "创客个人流水统计")
    private CountSingleRegulatorWorkerVO countSingleRegulatorWorkerVO;

    @ApiModelProperty(value = "创客个人信息")
    private Worker worker;

    @ApiModelProperty(value = "创客支付明细")
    private List<WorekerPaymentListPo> worekerPaymentListPos;
}
