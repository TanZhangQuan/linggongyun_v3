package com.example.mybatis.po;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "总包发票信息")
public class InvoiceInfoPO {
    private String invoiceUrl;
    private String makerInvoiceUrl;
    private String expressCompanyName;
    private String expressSheetNo;
}
