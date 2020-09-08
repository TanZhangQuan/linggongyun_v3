package com.example.merchant.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author jun.
 * @date 2020/4/2.
 * @time 14:09.
 */
@Data
public class senSMSDto implements Serializable {

    @NotBlank(message = "请输入手机号码")
    private String mobile;
}
