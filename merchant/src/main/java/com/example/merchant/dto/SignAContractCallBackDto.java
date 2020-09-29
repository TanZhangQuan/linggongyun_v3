package com.example.merchant.dto;

import lombok.Data;

@Data
public class SignAContractCallBackDto {
    private String action;
    private String flowId;
    private String accountId;
    private String authorizedAccountId;
    private Integer order;
    private String signTime;
    private Integer signResult;
    private String resultDescription;
    private Long timestamp;
    private String thirdPartyUserId;
}
