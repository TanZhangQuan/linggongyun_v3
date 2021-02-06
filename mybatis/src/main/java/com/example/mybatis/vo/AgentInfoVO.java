package com.example.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

/**
 * @author jun.
 * @date 2021/1/28.
 * @time 13:40.
 */
@Data
@ApiModel(description = "代理商的流水结算梯度Vo")
public class AgentInfoVO implements Serializable {

    /**
     * 代理商ID
     */
    private String agentId;

    /**
     * 代理商名称
     */
    private String agentName;

    /**
     * 登录用户名
     */
    private String userName;

    /**
     * 联系人
     */
    private String linkMan;

    /**
     * 手机号
     */
    private String linkMobile;

    /**
     * 加盟合同
     */
    private String contractFile;

    /**
     * 所属业务员ID
     */
    private String salesManId;

    /**
     * 初始密码
     */
    private String initPassWord;

    /**
     * 状态
     */
    private Integer agentStatus = 0;

    /**
     * 1代理商
     */
    private Integer userSign = 1;

    /**
     * 直客的流水结算梯度
     */
    private List<CommissionProportionVO> directCommissionProportion;

    private List<AgentTaxVO> agentTaxVo;


}
