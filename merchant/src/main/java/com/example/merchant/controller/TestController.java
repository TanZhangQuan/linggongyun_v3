package com.example.merchant.controller;

import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.example.common.util.ExcelUtils;
import com.example.common.util.ReturnJson;
import com.example.merchant.excel.MakerReadListener;
import com.example.merchant.excel.WorkerExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/11/23
 */
@Api(value = "导入", tags = "导入")
@RestController
@RequestMapping("/hutool")
public class TestController {

    @PostMapping("/daoru")
    public ReturnJson daoru(@ApiParam(value = "Excel文件", required = true) @NotNull(message = "请选择Excel文件") @RequestParam(required = false) MultipartFile file) throws IOException {
        //判断文件内容是否为空
        if (file.isEmpty()) {
            return ReturnJson.error("Excel文件不能为空");
        }
        // 查询上传文件的后缀
        String suffix = file.getOriginalFilename();
        if ((!StringUtils.endsWithIgnoreCase(suffix, ".xls") && !StringUtils.endsWithIgnoreCase(suffix, ".xlsx"))) {
            return ReturnJson.error("请选择Excel文件");
        }
        //MultipartFile转InputStream
        InputStream inputStream = file.getInputStream();

        //根据总包支付清单生成分包
        MakerReadListener makerReadListener = new MakerReadListener();
        ExcelReader excelReader = EasyExcelFactory.read(inputStream, WorkerExcel.class, makerReadListener).headRowNumber(1).build();
        excelReader.readAll();
        List<WorkerExcel> makerExcelList = makerReadListener.getList();
        excelReader.finish();
        return ReturnJson.success(makerExcelList);
    }
}
