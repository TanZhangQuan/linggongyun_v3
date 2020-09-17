package com.example.paas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.util.ExcelResponseUtils;
import com.example.common.util.MD5;
import com.example.common.util.ReturnJson;
import com.example.common.util.UuidUtil;
import com.example.mybatis.entity.PaymentInventory;
import com.example.mybatis.entity.Worker;
import com.example.mybatis.mapper.WorkerDao;
import com.example.paas.service.FileOperationService;
import com.example.paas.service.WorkerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FileOperationServiceImpl implements FileOperationService {

    @Autowired
    private WorkerDao workerDao;

    @Autowired
    private WorkerService workerService;

    @Value("${PWD_KEY}")
    private String PWD_KEY;

    @Value("${PathImage_KEY}")
    private String PathImage_KEY;

    @Value("${PathExcel_KEY}")
    private String PathExcel_KEY;

    @Value("${fileStaticAccesspathImage}")
    private String fileStaticAccesspathImage;

    @Value("${fileStaticAccesspathExcel}")
    private String fileStaticAccesspathExcel;


    /**
     * 获取Excel表中的创客信息
     *
     * @param workerExcel
     * @return
     */
    @Override
    public ReturnJson getExcelWorker(MultipartFile workerExcel) {
        if (workerExcel.getSize() == 0) {
            return ReturnJson.error("上传文件不能为空！");
        }
        List<Map<String, Object>> maps = ExcelResponseUtils.workerExcel(workerExcel);
        List mobileCodes = new ArrayList();
        for (Map<String, Object> map : maps) {
            mobileCodes.add(map.get("MobileCode"));
        }
        List<Worker> workers = workerDao.selectList(new QueryWrapper<Worker>().in("mobile_code", mobileCodes));
        mobileCodes = new ArrayList();
        for (Worker worker : workers) {
            mobileCodes.add(worker.getMobileCode());
        }
        for (Map<String, Object> map : maps) {
            if (!mobileCodes.contains(map.get("MobileCode"))) {
                Worker worker = new Worker();
                String idCardCode = (String) map.get("IDCardCode");
                String mobileCode = String.valueOf(map.get("MobileCode"));
                worker.setAccountName((String) map.get("WorkerName"));
                worker.setMobileCode(mobileCode);
                worker.setIdcardBack(idCardCode);
                worker.setBankCode((String) map.get("BankCode"));
                worker.setUserName(mobileCode);
                worker.setWorkerStatus(1);
                worker.setUserPwd(PWD_KEY + MD5.md5(idCardCode.substring(12)));
                workers.add(worker);
            }
        }
        return ReturnJson.success(workers);
    }

    /**
     * 上传JPG或PDF文件
     *
     * @param uploadJpgOrPdf
     * @return
     * @throws IOException
     */
    @Override
    public ReturnJson uploadJpgOrPdf(MultipartFile uploadJpgOrPdf, HttpServletRequest request) throws IOException {
        if (uploadJpgOrPdf.getSize() == 0) {
            return ReturnJson.error("上传文件不能为空！");
        }
        String[] files = {"pdf", "jpg", "png", "rar", "zip", "7z", "arj"};
        String fileName = uploadJpgOrPdf.getOriginalFilename();
        String suffixName = fileName.substring(fileName.indexOf(".") + 1);
        if (Arrays.asList(files).contains(suffixName.toLowerCase())) {
            String newFileName = UuidUtil.get32UUID() + "." + suffixName;
            File fileMkdir = new File(PathImage_KEY);
            if (!fileMkdir.exists()) {// 判断目录是否存在
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

    /**
     * 上传支付清单
     *
     * @param uploadInvoice
     * @return
     * @throws IOException
     */
    @Override
    public ReturnJson uploadInvoice(MultipartFile uploadInvoice, HttpServletRequest request) throws IOException {
        if (uploadInvoice.getSize() == 0) {
            return ReturnJson.error("上传文件不能为空！");
        }
        String[] files = {"xlsx", "xls"};
        String fileName = uploadInvoice.getOriginalFilename();
        String suffixName = fileName.substring(fileName.indexOf(".") + 1);
        if (Arrays.asList(files).contains(suffixName)) {
            List<Map<String, Object>> maps = ExcelResponseUtils.paymentInventoryExcel(uploadInvoice);
            List<PaymentInventory> paymentInventorys = new ArrayList<>();
            List<Worker> workers = new ArrayList<>();
            for (Map<String, Object> map : maps) {
                String idCardCode = (String) map.get("IDCardCode");
                String mobileCode = (String) map.get("MobileCode");
                String workerName = (String) map.get("WorkerName");
                String bankCode = (String) map.get("BankCode");
                BigDecimal realMoney = BigDecimal.valueOf(Double.valueOf((String) map.get("RealMoney")));
                String bankName = (String) map.get("BankName");
                Worker worker = workerDao.selectOne(new QueryWrapper<Worker>().eq("mobile_code", mobileCode));
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
                } else {
                    worker = new Worker();
                    worker.setAccountName((String) map.get("WorkerName"));
                    worker.setMobileCode(mobileCode);
                    worker.setIdcardBack(idCardCode);
                    worker.setBankCode((String) map.get("BankCode"));
                    worker.setUserName(mobileCode);
                    worker.setWorkerStatus(2);
                    worker.setUserPwd(PWD_KEY + MD5.md5(idCardCode.substring(12)));
                    workers.add(worker);
                }
            }
            workerService.saveBatch(workers);
            String newFileName = UuidUtil.get32UUID() + "." + suffixName;
            File fileMkdir = new File(PathExcel_KEY);
            if (!fileMkdir.exists()) {// 判断目录是否存在
                fileMkdir.mkdirs();
            }
            String filePath = PathExcel_KEY + newFileName;
            File file = new File(filePath);
            uploadInvoice.transferTo(file);
            String accessPath = request.getScheme() + "://" + request.getServerName() + ":" +
                    request.getServerPort() + request.getContextPath() + fileStaticAccesspathExcel + newFileName;
            log.info(fileStaticAccesspathExcel);
            return ReturnJson.success("Excel上传成功！", accessPath, paymentInventorys);
        } else {
            return ReturnJson.error("你上传的文件格式不正确！");
        }
    }
}
