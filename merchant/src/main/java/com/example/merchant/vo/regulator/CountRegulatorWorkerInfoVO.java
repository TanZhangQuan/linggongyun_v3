package com.example.merchant.vo.regulator;

import com.example.mybatis.entity.Worker;
import com.example.mybatis.po.WorekerPaymentListPo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "创客详情")
public class CountRegulatorWorkerInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客个人流水统计
     */
    private CountSingleRegulatorWorkerVO countSingleRegulatorWorkerVO;

    /**
     * 创客个人信息
     */
    private Worker worker;

    /**
     * 创客支付明细
     */
    private List<WorekerPaymentListPo> worekerPaymentListPos;
}
