package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.ExcelResponseUtils;
import com.example.common.MD5;
import com.example.common.ReturnJson;
import com.example.common.UuidUtil;
import com.example.merchant.service.FileOperationService;
import com.example.mybatis.entity.PaymentInventory;
import com.example.mybatis.entity.Worker;
import com.example.mybatis.mapper.WorkerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class FileOperationServiceImpl implements FileOperationService {

    @Autowired
    private WorkerDao workerDao;

    @Value("${PWD_KEY}")
    private String PWD_KEY;

    @Value("${PathImage_KEY}")
    private String PathImage_KEY;

    @Value("${PathExcel_KEY}")
    private String PathExcel_KEY;


    /**
     * 获取Excel表中的创客信息
     * @param workerExcel
     * @return
     */
    @Override
    public ReturnJson getExcelWorker(MultipartFile workerExcel) {
        if (workerExcel.getSize() == 0){
            return ReturnJson.error("上传文件不能为空！");
        }
        List<Map<String, Object>> maps = ExcelResponseUtils.workerExcel(workerExcel);
        List mobileCodes = new ArrayList();
        for (Map<String, Object> map: maps){
            mobileCodes.add(map.get("MobileCode"));
        }
        List<Worker> workers = workerDao.selectList(new QueryWrapper<Worker>().in("mobile_code",mobileCodes));
        mobileCodes = new ArrayList();
        for (Worker worker : workers) {
            mobileCodes.add(worker.getMobileCode());
        }
        for (Map<String, Object> map: maps){
            if (!mobileCodes.contains(map.get("MobileCode"))) {
                Worker worker = new Worker();
                String idCardCode = (String) map.get("IDCardCode");
                String mobileCode = String.valueOf(map.get("MobileCode"));
                worker.setAccountName((String) map.get("WorkerName"));
                worker.setAccountName(mobileCode);
                worker.setAccountName(idCardCode);
                worker.setAccountName((String) map.get("BankCode"));
                worker.setUserName(mobileCode);
                worker.setUserPwd(PWD_KEY+ MD5.md5(idCardCode.substring(12)));
                workers.add(worker);
            }
        }
        return ReturnJson.success(workers);
    }

    /**
     * 上传JPG或PDF文件
     * @param uploadJpgOrPdf
     * @return
     * @throws IOException
     */
    @Override
    public ReturnJson uploadJpgOrPdf(MultipartFile uploadJpgOrPdf) throws IOException {
        if (uploadJpgOrPdf.getSize() == 0){
            return ReturnJson.error("上传文件不能为空！");
        }
        String[] files = {"pdf","jpg"};
        String fileName = uploadJpgOrPdf.getOriginalFilename();
        String suffixName = fileName.substring(fileName.indexOf(".")+1);
        if (Arrays.asList(files).contains(suffixName)){
            String newFileName = UuidUtil.get32UUID()+"."+suffixName;
            File fileMkdir = new File(PathImage_KEY);
            if (!fileMkdir.exists()) {// 判断目录是否存在
                fileMkdir.mkdirs();
            }
            String filePath = PathImage_KEY+newFileName;
            File file = new File(filePath);
            uploadJpgOrPdf.transferTo(file);
            return ReturnJson.success("图片上传成功",filePath);
        } else {
            return ReturnJson.error("你上传的文件格式不正确！");
        }
    }

    /**
     * 上传支付清单
     * @param uploadInvoice
     * @return
     * @throws IOException
     */
    @Override
    public ReturnJson uploadInvoice(MultipartFile uploadInvoice) throws IOException{
        if (uploadInvoice.getSize() == 0){
            return ReturnJson.error("上传文件不能为空！");
        }
        String[] files = {"xlsx","xls"};
        String fileName = uploadInvoice.getOriginalFilename();
        String suffixName = fileName.substring(fileName.indexOf(".")+1);
        if (Arrays.asList(files).contains(suffixName)){
            List<Map<String, Object>> maps = ExcelResponseUtils.paymentInventoryExcel(uploadInvoice);
            List mobileCodes = new ArrayList();
            for (Map<String, Object> map: maps){
                mobileCodes.add(map.get("MobileCode"));
            }
            List<Worker> workers = workerDao.selectList(new QueryWrapper<Worker>().in("mobile_code",mobileCodes));
            if (workers == null || workers.size() != maps.size()){
                return ReturnJson.error("你上传的支付清单中有未注册的创客,请先导入创客！");
            }
            List<PaymentInventory> paymentInventorys = new ArrayList<>();
            for (Worker worker : workers){
                for (Map<String, Object> map: maps){
                    String idCardCode = (String) map.get("IDCardCode");
                    String mobileCode = (String) map.get("MobileCode");
                    String workerName = (String) map.get("WorkerName");
                    String bankCode = (String) map.get("BankCode");
                    BigDecimal realMoney = BigDecimal.valueOf(Double.valueOf((String) map.get("RealMoney")));
                    String bankName = (String) map.get("BankName");
                    if (mobileCode.equals(worker.getMobileCode()) && workerName.equals(worker.getAccountName())) {
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
                }
            }
            String newFileName = UuidUtil.get32UUID()+"."+suffixName;
            File fileMkdir = new File(PathExcel_KEY);
            if (!fileMkdir.exists()) {// 判断目录是否存在
                fileMkdir.mkdirs();
            }
            String filePath = PathExcel_KEY+newFileName;
            File file = new File(filePath);
            uploadInvoice.transferTo(file);
            return ReturnJson.success("Excel上传成功！",filePath,paymentInventorys);
        } else {
            return ReturnJson.error("你上传的文件格式不正确！");
        }
    }
}
