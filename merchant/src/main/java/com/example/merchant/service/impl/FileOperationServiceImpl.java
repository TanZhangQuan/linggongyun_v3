package com.example.merchant.service.impl;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.util.*;
import com.example.merchant.excel.MakerExcel;
import com.example.merchant.excel.MakerPanymentExcel;
import com.example.merchant.excel.MakerPanymentReadListener;
import com.example.merchant.excel.MakerReadListener;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.FileOperationService;
import com.example.mybatis.entity.MakerInvoice;
import com.example.mybatis.entity.PaymentInventory;
import com.example.mybatis.entity.Worker;
import com.example.mybatis.mapper.MakerInvoiceDao;
import com.example.mybatis.mapper.PaymentInventoryDao;
import com.example.mybatis.mapper.WorkerDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@Service
public class FileOperationServiceImpl implements FileOperationService {

    @Resource
    private WorkerDao workerDao;

    @Resource
    private MakerInvoiceDao makerInvoiceDao;

    @Resource
    private PaymentInventoryDao paymentInventoryDao;

    @Value("${PWD_KEY}")
    private String PWD_KEY;

    @Value("${PathImage_KEY}")
    private String PathImage_KEY;

    @Value("${PathExcel_KEY}")
    private String PathExcel_KEY;

    @Value("${PathVideo_KEY}")
    private String PathVideo_KEY;

    @Value("${fileStaticAccesspathImage}")
    private String fileStaticAccesspathImage;

    @Value("${fileStaticAccesspathExcel}")
    private String fileStaticAccesspathExcel;

    @Value("${fileStaticAccesspathVideo}")
    private String fileStaticAccesspathVideo;

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    @Override
    public ReturnJson getExcelWorker(MultipartFile workerExcel) throws Exception {
        if (workerExcel.getSize() == 0) {
            return ReturnJson.error("上传文件内容不能为空！");
        }
        // 查询上传文件的后缀
        String suffix = workerExcel.getOriginalFilename();
        if ((!StringUtils.endsWithIgnoreCase(suffix, ".xls") && !StringUtils.endsWithIgnoreCase(suffix, ".xlsx"))) {
            return ReturnJson.error("请选择Excel文件");
        }

        //MultipartFile转InputStream
        InputStream inputStream = workerExcel.getInputStream();

        //根据总包支付清单生成分包
        MakerReadListener makerReadListener = new MakerReadListener();
        ExcelReader excelReader = EasyExcelFactory.read(inputStream, MakerExcel.class, makerReadListener).headRowNumber(1).build();
        excelReader.readAll();
        List<MakerExcel> makerExcelList = makerReadListener.getList();
        excelReader.finish();

        List mobileCodes = new ArrayList();
        for (MakerExcel makerExcel : makerExcelList) {
            mobileCodes.add(makerExcel.getPhoneNumber());
        }

        List<Worker> workers = workerDao.selectList(new QueryWrapper<Worker>().lambda()
                .in(Worker::getMobileCode, mobileCodes));
        mobileCodes = new ArrayList();
        for (Worker worker : workers) {
            mobileCodes.add(worker.getMobileCode());
        }
        for (MakerExcel makerExcel : makerExcelList) {
            if (!mobileCodes.contains(makerExcel.getPhoneNumber())) {
                Worker worker = new Worker();
                String idCardCode = makerExcel.getIdcardNo();
                worker.setAccountName(makerExcel.getName());
                worker.setMobileCode(makerExcel.getPhoneNumber());
                worker.setIdcardCode(idCardCode);
                worker.setBankName(makerExcel.getBankName());
                worker.setBankCode(makerExcel.getBankCardNo());
                worker.setUserName(makerExcel.getPhoneNumber());
                worker.setWorkerStatus(1);
                worker.setUserPwd(MD5.md5(PWD_KEY + idCardCode.substring(12)));
                workers.add(worker);
            }
        }
        return ReturnJson.success(workers);
    }

    @Override
    public ReturnJson uploadJpgOrPdf(MultipartFile uploadJpgOrPdf, HttpServletRequest request) throws Exception {
        if (uploadJpgOrPdf.getSize() == 0) {
            return ReturnJson.error("上传文件内容不能为空！");
        }
        String[] files = {"pdf", "jpg", "png", "rar", "zip", "7z", "arj"};
        String fileName = uploadJpgOrPdf.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (Arrays.asList(files).contains(suffixName.toLowerCase())) {
            String newFileName = UuidUtil.get32UUID() + "." + suffixName;
            File fileMkdir = new File(PathImage_KEY);
            // 判断目录是否存在
            if (!fileMkdir.exists()) {
                fileMkdir.mkdirs();
            }
            String filePath = PathImage_KEY + newFileName;
            File file = new File(filePath);
            String accessPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
                    request.getContextPath() + fileStaticAccesspathImage + newFileName;
            uploadJpgOrPdf.transferTo(file);
            return ReturnJson.success("图片上传成功", accessPath);
        } else {
            return ReturnJson.error("你上传的文件格式不正确！,请上传" + Arrays.toString(files) + "格式的文件。");
        }
    }

    @Override
    public String uploadJpgOrPdf(String fileUrl, HttpServletRequest request) {
        CloseableHttpResponse closeableHttpResponse = HttpClientUtils.urlGet(fileUrl);
        if (closeableHttpResponse == null) {
            return "";
        }
        InputStream inputStream = null;
        try {
            inputStream = closeableHttpResponse.getEntity().getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (inputStream == null) {
            return "";
        }
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        File fileMkdir = new File(PathImage_KEY);
        String newFileName = UuidUtil.get32UUID() + ".pdf";
        // 判断目录是否存在
        if (!fileMkdir.exists()) {
            fileMkdir.mkdirs();
        }
        String fileName = PathImage_KEY + newFileName;
        in = new BufferedInputStream(inputStream);
        try {
            out = new BufferedOutputStream(new FileOutputStream(fileName));
            int len = -1;
            byte[] b = new byte[1024];
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
            String accessPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
                    request.getContextPath() + fileStaticAccesspathImage + newFileName;
            return accessPath;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                closeableHttpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson uploadInvoice(MultipartFile uploadInvoice, HttpServletRequest request) throws Exception {
        if (uploadInvoice.getSize() == 0) {
            return ReturnJson.error("上传文件内容不能为空！");
        }
        String[] files = {"xlsx", "xls"};
        String fileName = uploadInvoice.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (Arrays.asList(files).contains(suffixName)) {
            List<String> workerList = new ArrayList<>();
            Map<String, Object> listMap = new HashMap<>(0);
            //MultipartFile转InputStream
            InputStream inputStream = uploadInvoice.getInputStream();

            //根据总包支付清单生成分包
            MakerPanymentReadListener makerPanymentReadListener = new MakerPanymentReadListener();
            ExcelReader excelReader = EasyExcelFactory.read(inputStream, MakerPanymentExcel.class, makerPanymentReadListener).headRowNumber(1).build();
            excelReader.readAll();
            List<MakerPanymentExcel> makerPanymentExcels = makerPanymentReadListener.getList();
            makerPanymentExcels.remove(makerPanymentExcels.get(makerPanymentExcels.size() - 1));
            excelReader.finish();

            List<PaymentInventory> paymentInventorys = new ArrayList<>();
            for (MakerPanymentExcel makerPanymentExcel : makerPanymentExcels) {
                //判断金额 金额为null自动去除这个人
                if (makerPanymentExcel.getRealMoney() == null) {
                    continue;
                }
                String idCardCode = makerPanymentExcel.getIdCardCode();
                String mobileCode = makerPanymentExcel.getPhoneNumber();
                String workerName = makerPanymentExcel.getPayeeName();
                String bankCode = makerPanymentExcel.getBankCardNo();
                BigDecimal realMoney = BigDecimal.valueOf(Double.valueOf(makerPanymentExcel.getRealMoney()));
                String bankName = makerPanymentExcel.getBankName();
                Worker worker = workerDao.selectOne(new QueryWrapper<Worker>().lambda()
                        .eq(Worker::getMobileCode, mobileCode));
                if (worker == null) {
                    return ReturnJson.error(workerName + "不存在此创客，或者此创客手机号码不对");
                }
                if (!worker.getIdcardCode().equals(idCardCode)) {
                    return ReturnJson.error(workerName + "表格身份证与系统内不一致！");
                }
                if (!worker.getAccountName().equals(workerName)) {
                    return ReturnJson.error(workerName + "表格名称与系统创客不一致！");
                }
                if (bankCode != null) {
                    if (!isInteger(bankCode)) {
                        return ReturnJson.error(workerName + "银行卡错误请确认表格内数据！");
                    }
                }

                BigDecimal realMoneySum = paymentInventoryDao.getRealMoneyByWorker(worker.getId());
                if (realMoneySum.compareTo(new BigDecimal("3000000")) >= 0) {
                    workerList.add("自然人（\" + workerName + idCardCode + \"）在系统代开发票公历年度内已经超三百万，在此不能进行发放！");
                }
                realMoneySum = realMoneySum.add(realMoney);
                if (realMoneySum.compareTo(new BigDecimal("5000000")) >= 0) {
                    return ReturnJson.error("自然人（" + workerName + idCardCode + "）在系统代开发票公历年度内已等于或超过五百万，在此不能进行发放！");
                }

                if (worker != null) {
                    PaymentInventory paymentInventory = new PaymentInventory();
                    paymentInventory.setWorkerId(worker.getId());
                    paymentInventory.setWorkerName(workerName);
                    paymentInventory.setMobileCode(mobileCode);
                    paymentInventory.setBankName(bankName);
                    paymentInventory.setBankCode(bankCode);
                    paymentInventory.setRealMoney(realMoney);
                    paymentInventory.setIdCardCode(idCardCode);
                    paymentInventory.setAttestation(worker.getAttestation());
                    paymentInventorys.add(paymentInventory);
                }

                if (worker.getAttestation() == 0 || worker.getAttestation() == -1) {
                    throw new CommonException(300, workerName + "创客需要认证才能进行发放！");
                }
                if (worker.getAgreementSign() != 2) {
                    throw new CommonException(300, workerName + "创客需要签约才能进行发放！");
                }
            }
            String newFileName = UuidUtil.get32UUID() + "." + suffixName;
            File fileMkdir = new File(PathExcel_KEY);
            // 判断目录是否存在
            if (!fileMkdir.exists()) {
                fileMkdir.mkdirs();
            }
            String filePath = PathExcel_KEY + newFileName;
            File file = new File(filePath);
            uploadInvoice.transferTo(file);
            String accessPath = request.getScheme() + "://" + request.getServerName() + ":" +
                    request.getServerPort() + request.getContextPath() + fileStaticAccesspathExcel + newFileName;
            log.info(fileStaticAccesspathExcel);
            listMap.put("paymentInventorys", paymentInventorys);
            listMap.put("workerList", workerList);
            listMap.put("accessPath", accessPath);
            return ReturnJson.success("Excel上传成功！", listMap);
        } else {
            return ReturnJson.error("你上传的文件格式不正确！");
        }
    }

    @Override
    public ReturnJson uploadInvoiceOrTaxReceipt(String state, MultipartFile uploadTaxReceipt, String paymentInventoryId, HttpServletRequest request) throws Exception {
        //时间转换
        DateTimeFormatter dfd = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (uploadTaxReceipt.getSize() == 0) {
            return ReturnJson.error("上传文件不能为空！");
        }
        String[] files = {"pdf", "jpg", "png", "rar", "zip", "7z", "arj"};
        String fileName = uploadTaxReceipt.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (Arrays.asList(files).contains(suffixName.toLowerCase())) {
            String newFileName = UuidUtil.get32UUID() + "." + suffixName;
            File fileMkdir = new File(PathImage_KEY);
            // 判断目录是否存在
            if (!fileMkdir.exists()) {
                fileMkdir.mkdirs();
            }
            String filePath = PathImage_KEY + newFileName;
            File file = new File(filePath);
            String accessPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
                    request.getContextPath() + fileStaticAccesspathImage + newFileName;
            uploadTaxReceipt.transferTo(file);
            MakerInvoice makerInvoice = new MakerInvoice();
            if ("0".equals(state)) {
                makerInvoice.setMakerVoiceUrl(fileName);
                makerInvoice.setUpdateDate(LocalDateTime.parse(DateUtil.getTime(), dfd));
                makerInvoiceDao.update(makerInvoice, new QueryWrapper<MakerInvoice>().lambda()
                        .eq(MakerInvoice::getPaymentInventoryId, paymentInventoryId));
                return ReturnJson.success("发票上传成功", accessPath);
            } else {
                makerInvoice.setMakerTaxUrl(fileName);
                makerInvoice.setUpdateDate(LocalDateTime.parse(DateUtil.getTime(), dfd));
                makerInvoiceDao.update(makerInvoice, new QueryWrapper<MakerInvoice>().lambda()
                        .eq(MakerInvoice::getPaymentInventoryId, paymentInventoryId));
                return ReturnJson.success("税票上传成功", accessPath);
            }
        } else {
            return ReturnJson.error("你上传的文件格式不正确！,请上传" + Arrays.toString(files) + "格式的文件。");
        }
    }

    @Override
    public ReturnJson uploadVideo(MultipartFile uploadVideo, HttpServletRequest request) {
        if (uploadVideo.getSize() == 0) {
            return ReturnJson.error("上传文件不能为空！");
        }
        String[] files = {"avi", "vavi", "mp4"};
        String fileName = uploadVideo.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (Arrays.asList(files).contains(suffixName.toLowerCase())) {
            String newFileName = UuidUtil.get32UUID() + "." + suffixName;
            File fileMkdir = new File(PathVideo_KEY);
            String accessPath = null;
            try {
                // 判断目录是否存在
                if (!fileMkdir.exists()) {
                    fileMkdir.mkdirs();
                }
                String filePath = PathVideo_KEY + newFileName;
                File file = new File(filePath);
                accessPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
                        request.getContextPath() + fileStaticAccesspathVideo + newFileName;
                uploadVideo.transferTo(file);
            } catch (IOException e) {
                log.error(e.toString() + ":" + e.getMessage());
            }
            return ReturnJson.success("视频上传成功", accessPath);
        } else {
            return ReturnJson.error("你上传的文件格式不正确！,请上传" + Arrays.toString(files) + "格式的文件。");
        }
    }
}
