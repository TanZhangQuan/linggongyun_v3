package com.example.mybatis.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerchantListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String companyName;
}
