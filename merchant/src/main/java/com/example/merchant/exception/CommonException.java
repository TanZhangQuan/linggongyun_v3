package com.example.merchant.exception;

import lombok.Getter;

@Getter
public class CommonException extends Exception {
    private Integer responseCode;
    private String responseMsg;

    public CommonException(Integer responseCode, String responseMsg) {
        this.responseCode = responseCode;
        this.responseMsg = responseMsg;
    }
}
