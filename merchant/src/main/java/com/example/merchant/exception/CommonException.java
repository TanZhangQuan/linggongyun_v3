package com.example.merchant.exception;

import lombok.Getter;

@Getter
public class CommonException extends Exception {
    private String responseCode;
    private String responseMsg;

    public CommonException(String responseCode, String responseMsg) {
        this.responseCode = responseCode;
        this.responseMsg = responseMsg;
    }
}
