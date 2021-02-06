package com.example.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author .
 * @date 2021/1/28.
 * @time 11:41.
 */
@Data
@ApiModel(description = "XXXXX")
public class ManagersVO implements Serializable {

    /**
     * ID
     */
    private String id;

    /**
     * 父级ID
     */
    private String parentId;

    /**
     * 角色ID
     */
    private String roleName;

    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 登录用户名
     */
    private String userName;

    /**
     * 部门
     */
    private String userDept;

    /**
     * 岗位
     */
    private String userPost;

    /**
     * 手机号
     */
    private String mobileCode;

    /**
     * 登录密码
     */
    private String passWord;

    /**
     * 用户的相关说明
     */
    private String userDesc;

    /**
     * 平台名称
     */
    private String paasName;

    /**
     * 1渠道商，2业务员，3管理员
     */
    private Integer userSign;

    /**
     * 0正常，1停用
     */
    private Integer status;

    /**
     * 头像
     */
    private String userHead;

    /**
     * 用户创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDate;

    /**
     * 用户修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateDate;

    /**
     * 直客
     */
    private List<CommissionProportionVO> directCustomer;
    /**
     * 代理商
     */
    private List<CommissionProportionVO> agent;
}
