package com.example.common.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * Title :
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Author :蔡雨生
 * </p>
 * <p>
 * Department : 研发部
 * </p>
 * <p> Copyright : ©协群（上海）网络科技有限公司 </p>
 */
public class ExcelResponseUtils {

    /**
     * 设置表头
     *
     * @param firstRow
     */
    public static HSSFWorkbook initExcelWorkbook(List<String> firstRow) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("fieldSheet");
        sheet.setAutobreaks(true);
        HSSFCellStyle setBorder = wb.createCellStyle();
        setBorder.setAlignment(HorizontalAlignment.CENTER);
        setBorder.setWrapText(true);
        HSSFRow row = sheet.createRow(0);

        //设置第一行的字段
        for (int i = 0; i < firstRow.size(); i++) {
            row.createCell(i).setCellValue(firstRow.get(i));
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i,3000);
        }
        return wb;
    }

    public static HSSFWorkbook exportExcel(List<Map> dataList, List<String> firstRow) {
        HSSFWorkbook wb = initExcelWorkbook(firstRow);
        HSSFSheet sheet = wb.getSheet("fieldSheet");
        for (int i = 0; i < dataList.size(); i++) {
            HSSFRow row = sheet.createRow(i + 1);
            Map<String, Object> data = dataList.get(i);
            for (int j = 0; j < firstRow.size(); j++) {
                String fieldName = firstRow.get(j);
                String value = "";
                if (data.get(fieldName) != null) {
                    value = String.valueOf(data.get(fieldName));
                }
                row.createCell(j).setCellValue(value);
            }
        }
        for (int i = 0; i < firstRow.size(); i++) {
            sheet.autoSizeColumn(i);
        }
        return wb;
    }

    public static List<Map<String, Object>> analyzeExcel(MultipartFile excel) {
        InputStream in = null;
        HSSFWorkbook wb = null;
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            in = excel.getInputStream();
            wb = new HSSFWorkbook(in);
            //先取出表头
            Sheet sheet = wb.getSheetAt(0);
            Row fieldNameRow = sheet.getRow(0);
            List<String> fieldNameList = new ArrayList<>();
            fieldNameList.add("WorkerName");
            fieldNameList.add("MobileCode");
            fieldNameList.add("IDCardCode");
            fieldNameList.add("BankCode");

            //解析值
            for (int rowIndex = sheet.getFirstRowNum() + 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                //行
                Map<String, Object> dataItem = new HashMap();
                Row row = sheet.getRow(rowIndex);
                for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); cellIndex++) {
                    if(cellIndex == 1){
                        DecimalFormat df = new DecimalFormat("#");
                        String s = df.format(row.getCell(cellIndex).getNumericCellValue());
                        dataItem.put(fieldNameList.get(cellIndex), s);
                    }else{
                        //列
                        Cell valueCell = row.getCell(cellIndex);
                        dataItem.put(fieldNameList.get(cellIndex), getCellValue(valueCell));
                    }

                }
                result.add(dataItem);
            }
        } catch (IOException e) {
            throw new RuntimeException("excel读取失败", e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 读取商户导入的创客信息
     * @param excel
     * @return
     */
    public static List<Map<String, Object>> workerExcel(MultipartFile excel) {
        InputStream in = null;
        XSSFWorkbook wb = null;
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            in = excel.getInputStream();
            wb = new XSSFWorkbook(in);
            //先取出表头
            Sheet sheet = wb.getSheetAt(0);
            Row fieldNameRow = sheet.getRow(0);
            List<String> fieldNameList = new ArrayList<>();
            fieldNameList.add("WorkerName");
            fieldNameList.add("MobileCode");
            fieldNameList.add("IDCardCode");
            fieldNameList.add("BankCode");

            //解析值
            for (int rowIndex = sheet.getFirstRowNum() + 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                //行
                Map<String, Object> dataItem = new HashMap();
                Row row = sheet.getRow(rowIndex);
                for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); cellIndex++) {
                    if(cellIndex == 1){
                        //DecimalFormat df = new DecimalFormat("#");
                        //String s = row.getCell(cellIndex).getStringCellValue();
                        Object cellValue = getCellValue(row.getCell(cellIndex));
                        dataItem.put(fieldNameList.get(cellIndex), cellValue);
                    }else{
                        //列
                        Cell valueCell = row.getCell(cellIndex);
                        dataItem.put(fieldNameList.get(cellIndex), getCellValue(valueCell));
                    }

                }
                result.add(dataItem);
            }
        } catch (IOException e) {
            throw new RuntimeException("excel读取失败", e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 读取支付清单的信息
     * @param excel
     * @return
     */
    public static List<Map<String, Object>> paymentInventoryExcel(MultipartFile excel) {
        InputStream in = null;
        XSSFWorkbook wb = null;

        List<Map<String, Object>> result = new ArrayList<>();
        try {
            in = excel.getInputStream();
            wb = new XSSFWorkbook(in);
            //先取出表头
            Sheet sheet = wb.getSheetAt(0);
            Row fieldNameRow = sheet.getRow(0);
            List<String> fieldNameList = new ArrayList<>();
            fieldNameList.add("WorkerName");
            fieldNameList.add("MobileCode");
            fieldNameList.add("IDCardCode");
            fieldNameList.add("BankName");
            fieldNameList.add("BankCode");
            fieldNameList.add("RealMoney");
            //解析值
            //解析值
            for (int rowIndex = sheet.getFirstRowNum() + 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                //行
                Map<String, Object> dataItem = new HashMap();
                Row row = sheet.getRow(rowIndex);
                for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); cellIndex++) {
                    if(cellIndex == 1){
                        //DecimalFormat df = new DecimalFormat("#");
                        //String s = row.getCell(cellIndex).getStringCellValue();
                        Object cellValue = getCellValue(row.getCell(cellIndex));
                        dataItem.put(fieldNameList.get(cellIndex), cellValue);
                    }else{
                        //列
                        Cell valueCell = row.getCell(cellIndex);
                        dataItem.put(fieldNameList.get(cellIndex), getCellValue(valueCell));
                    }

                }
                result.add(dataItem);
            }
        } catch (IOException e) {
            throw new RuntimeException("excel读取失败", e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }



    public static void export(HSSFWorkbook wb, HttpServletResponse response, String tableName) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        InputStream is = null;
        try {
            wb.write(os);
            byte[] content = os.toByteArray();
            is = new ByteArrayInputStream(content);
            response.reset();
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + tableName + ".xls");
            ServletOutputStream out = response.getOutputStream();
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException("导出数据异常", e);
        } finally {
            try {
                bis.close();
                bos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Object getCellValue(Cell cell) {
        Object cellValue = "";
        DataFormatter formatter = new DataFormatter();
        cell.setCellType(CellType.STRING);

        if (cell != null) {
            switch (cell.getCellType()) {
                case NUMERIC:
                    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                        try {
                            cellValue = sdf.parse(sdf.format(date));
                        } catch (ParseException e) {
                            throw new RuntimeException("日期格式错误", e);
                        }
                    } else {
                        double value = cell.getNumericCellValue();
                        int intValue = (int) value;
                        cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
                    }
                    break;
                case STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case FORMULA:
                    cellValue = String.valueOf(cell.getCellFormula());
                    break;
                case BLANK:
                    cellValue = "";
                    break;
                case ERROR:
                    cellValue = "";
                    break;
                default:
                    cellValue = cell.toString().trim();
                    break;
            }
        }
        return cellValue;
    }




}
