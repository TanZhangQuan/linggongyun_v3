package com.example.merchant.vo.platform;

import lombok.Data;

import java.io.Serializable;

@Data
public class CompanyBriefVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String companyName;
}
