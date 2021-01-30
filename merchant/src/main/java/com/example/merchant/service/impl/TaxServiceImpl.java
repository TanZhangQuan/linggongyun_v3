package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.MD5;
import com.example.common.util.ReturnJson;
import com.example.common.util.VerificationCheck;
import com.example.merchant.dto.TaxListDTO;
import com.example.merchant.dto.platform.*;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.*;
import com.example.merchant.vo.merchant.CompanyFlowInfoVO;
import com.example.merchant.vo.platform.HomePageVO;
import com.example.merchant.vo.platform.TaxPlatformVO;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.po.InvoicePO;
import com.example.mybatis.po.MerchantPaymentListPO;
import com.example.mybatis.po.TaxListPO;
import com.example.mybatis.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 合作园区信息 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class TaxServiceImpl extends ServiceImpl<TaxDao, Tax> implements TaxService {

    @Value("${PWD_KEY}")
    private String PWD_KEY;

    @Resource
    private TaxDao taxDao;

    @Resource
    private CompanyTaxService companyTaxService;

    @Resource
    private InvoiceCatalogDao invoiceCatalogDao;

    @Resource
    private TaxPackageService taxPackageService;

    @Resource
    private InvoiceLadderPriceService invoiceLadderPriceService;

    @Resource
    private PaymentOrderDao paymentOrderDao;

    @Resource
    private PaymentOrderManyDao paymentOrderManyDao;

    @Resource
    private InvoiceDao invoiceDao;

    @Resource
    private CrowdSourcingInvoiceDao crowdSourcingInvoiceDao;

    @Resource
    private MerchantDao merchantDao;

    @Resource
    private WorkerDao workerDao;

    @Resource
    private TaxWorkerService taxWorkerService;

    @Override
    public ReturnJson getTaxAll(String merchantId, Integer packageStatus) {
        Merchant merchant = merchantDao.selectById(merchantId);
        List<CompanyTax> companyTaxes = companyTaxService.list(new QueryWrapper<CompanyTax>().lambda()
                .eq(CompanyTax::getCompanyId, merchant.getCompanyId())
                .eq(CompanyTax::getPackageStatus, packageStatus));
        List<String> ids = new LinkedList<>();
        for (CompanyTax companyTax : companyTaxes) {
            ids.add(companyTax.getTaxId());
        }
        List<Tax> taxes = null;
        if (!VerificationCheck.listIsNull(ids)) {
            taxes = taxDao.selectList(new QueryWrapper<Tax>().lambda()
                    .in(Tax::getId, ids)
                    .eq(Tax::getTaxStatus, 0));
        }
        List<TaxBriefVO> taxBriefVOS = new ArrayList<>();
        if (taxes != null) {
            taxes.forEach(tax -> {
                TaxBriefVO taxBriefVO = new TaxBriefVO();
                BeanUtils.copyProperties(tax, taxBriefVO);
                taxBriefVOS.add(taxBriefVO);
            });
        }
        if (taxes == null) {
            return ReturnJson.success("没有可用的平台服务商");
        }
        return ReturnJson.success(taxBriefVOS);
    }

    @Override
    public ReturnJson getTaxPaasAll(String companyId, Integer packageStatus) {
        List<CompanyTax> companyTaxes = companyTaxService.list(new QueryWrapper<CompanyTax>().lambda()
                .eq(CompanyTax::getCompanyId, companyId)
                .eq(CompanyTax::getPackageStatus, packageStatus));
        List<String> ids = new LinkedList<>();
        for (CompanyTax companyTax : companyTaxes) {
            ids.add(companyTax.getTaxId());
        }
        List<Tax> taxes = null;
        if (!VerificationCheck.listIsNull(ids)) {
            taxes = taxDao.selectList(new QueryWrapper<Tax>().lambda()
                    .in(Tax::getId, ids)
                    .eq(Tax::getTaxStatus, 0));
        }
        List<TaxBriefVO> taxBriefVOS = new ArrayList<>();
        taxes.forEach(tax -> {
            TaxBriefVO taxBriefVO = new TaxBriefVO();
            BeanUtils.copyProperties(tax, taxBriefVO);
            taxBriefVOS.add(taxBriefVO);
        });
        return ReturnJson.success(taxBriefVOS);
    }

    @Override
    public ReturnJson getCatalogAll() {
        List<InvoiceCatalog> invoiceCatalogs = invoiceCatalogDao.selectList(new QueryWrapper<>());
        return ReturnJson.success(invoiceCatalogs);
    }

    @Override
    public ReturnJson saveCatalog(AddInvoiceCatalogDTO addInvoiceCatalogDto) {
        InvoiceCatalog invoiceCatalog = new InvoiceCatalog();
        BeanUtils.copyProperties(addInvoiceCatalogDto, invoiceCatalog);
        int i = invoiceCatalogDao.insert(invoiceCatalog);
        if (i == 1) {
            return ReturnJson.success("添加类目成功！");
        }
        return ReturnJson.error("添加类目失败！");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson addOrUpdateTax(TaxDTO taxDto) throws Exception {

        Tax tax;
        TaxWorker taxWorker;
        QueryWrapper<Tax> queryWrapper;
        QueryWrapper<TaxWorker> taxWorkerQueryWrapper;
        int taxNameCount;
        int creditCodeCount;
        int userNameCount;
        int loginMobileCount;
        if (StringUtils.isNotBlank(taxDto.getTaxId())) {

            tax = getById(taxDto.getTaxId());
            if (tax == null) {
                return ReturnJson.error("服务商不存在");
            }

            //判断是否存在相同服务商名称
            queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(Tax::getTaxName, taxDto.getTaxName()).ne(Tax::getId, taxDto.getTaxId());
            taxNameCount = baseMapper.selectCount(queryWrapper);
            if (taxNameCount > 0) {
                return ReturnJson.error("服务商名称已存在");
            }

            //判断是否存在相同统一社会信用代码
            queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(Tax::getCreditCode, taxDto.getCreditCode()).ne(Tax::getId, taxDto.getTaxId());
            creditCodeCount = baseMapper.selectCount(queryWrapper);
            if (creditCodeCount > 0) {
                return ReturnJson.error("统一社会信用代码已存在");
            }

            //查看服务商是否有主账号
            taxWorkerQueryWrapper = new QueryWrapper<>();
            taxWorkerQueryWrapper.lambda().eq(TaxWorker::getTaxId, taxDto.getTaxId()).eq(TaxWorker::getParentId, "0");
            taxWorker = taxWorkerService.getOne(taxWorkerQueryWrapper);
            if (taxWorker == null) {
                return ReturnJson.error("服务商主账号不存在");
            }

            //判断是否存在相同用户名的员工账号
            taxWorkerQueryWrapper = new QueryWrapper<>();
            taxWorkerQueryWrapper.lambda().eq(TaxWorker::getUserName, taxDto.getUserName()).ne(TaxWorker::getId, taxWorker.getId());
            userNameCount = taxWorkerService.count(taxWorkerQueryWrapper);
            if (userNameCount > 0) {
                return ReturnJson.error("管理员账户号已存在");
            }

            //判断是否存在相同登录手机号的员工账号
            taxWorkerQueryWrapper = new QueryWrapper<>();
            taxWorkerQueryWrapper.lambda().eq(TaxWorker::getLoginMobile, taxDto.getLoginMobile()).ne(TaxWorker::getId, taxWorker.getId());
            loginMobileCount = taxWorkerService.count(taxWorkerQueryWrapper);
            if (loginMobileCount > 0) {
                return ReturnJson.error("管理员手机号已存在");
            }

            //更新服务商
            BeanUtils.copyProperties(taxDto, tax);
            updateById(tax);
            //更新主账号
            if (StringUtils.isBlank(taxDto.getPassWord())) {
                taxDto.setPassWord(taxWorker.getPassWord());
            } else {
                taxDto.setPassWord(MD5.md5(PWD_KEY + taxDto.getPassWord()));
            }
            BeanUtils.copyProperties(taxDto, taxWorker);
            taxWorkerService.updateById(taxWorker);

            return ReturnJson.success("操作成功！");

        } else {

            if (StringUtils.isBlank(taxDto.getPassWord())) {
                return ReturnJson.error("请输入管理员密码");
            }

            if (taxDto.getPassWord().length() < 6 || taxDto.getPassWord().length() > 18) {
                return ReturnJson.error("请输入长度为6-18位的管理员密码");
            }

            //判断是否存在相同服务商名称
            queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(Tax::getTaxName, taxDto.getTaxName());
            taxNameCount = baseMapper.selectCount(queryWrapper);
            if (taxNameCount > 0) {
                return ReturnJson.error("服务商名称已存在");
            }

            //判断是否存在相同统一社会信用代码
            queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(Tax::getCreditCode, taxDto.getCreditCode());
            creditCodeCount = baseMapper.selectCount(queryWrapper);
            if (creditCodeCount > 0) {
                return ReturnJson.error("统一社会信用代码已存在");
            }

            //判断是否存在相同用户名的员工账号
            taxWorkerQueryWrapper = new QueryWrapper<>();
            taxWorkerQueryWrapper.lambda().eq(TaxWorker::getUserName, taxDto.getUserName());
            userNameCount = taxWorkerService.count(taxWorkerQueryWrapper);
            if (userNameCount > 0) {
                return ReturnJson.error("管理员账户号已存在");
            }

            //判断是否存在相同登录手机号的员工账号
            taxWorkerQueryWrapper = new QueryWrapper<>();
            taxWorkerQueryWrapper.lambda().eq(TaxWorker::getLoginMobile, taxDto.getLoginMobile());
            loginMobileCount = taxWorkerService.count(taxWorkerQueryWrapper);
            if (loginMobileCount > 0) {
                return ReturnJson.error("管理员手机号已存在");
            }

            //新建服务商
            tax = new Tax();
            BeanUtils.copyProperties(taxDto, tax);
            save(tax);
            //新建主账号
            taxWorker = new TaxWorker();
            taxDto.setPassWord(MD5.md5(PWD_KEY + taxDto.getPassWord()));
            BeanUtils.copyProperties(taxDto, taxWorker);
            taxWorker.setTaxId(tax.getId());
            taxWorkerService.save(taxWorker);
        }

        //判断是否有总包，有总包就添加
        TaxPackageDTO totalTaxPackageDTO = taxDto.getTotalTaxPackage();
        if (totalTaxPackageDTO != null) {

            //商户-服务商总包合作信息操作
            TaxPackage totalTaxPackage = new TaxPackage();
            BeanUtils.copyProperties(totalTaxPackageDTO, totalTaxPackage);
            totalTaxPackage.setTaxId(tax.getId());
            taxPackageService.save(totalTaxPackage);

            //总包（手续费）税率梯度价
            List<InvoiceLadderPriceDTO> totalServiceLaddersDTO = taxDto.getTotalServiceLadders();
            List<InvoiceLadderPrice> totalServiceLadders = new ArrayList<>();
            if (totalServiceLaddersDTO != null && totalServiceLaddersDTO.size() > 0) {
                for (int i = 0; i < totalServiceLaddersDTO.size(); i++) {
                    InvoiceLadderPrice invoiceLadderPrice = new InvoiceLadderPrice();
                    BeanUtils.copyProperties(totalServiceLaddersDTO.get(i), invoiceLadderPrice);
                    totalServiceLadders.add(invoiceLadderPrice);
                }
            }

            //判断是否有梯度价
            if (!VerificationCheck.listIsNull(totalServiceLadders)) {
                //判断梯度价是否合理
                for (int i = 0; i < totalServiceLadders.size(); i++) {
                    if (i != totalServiceLadders.size() - 1) {
                        InvoiceLadderPrice invoiceLadderPrice = totalServiceLadders.get(i);
                        if (invoiceLadderPrice.getEndMoney().compareTo(invoiceLadderPrice.getStartMoney()) <= 0) {
                            throw new CommonException(300, "总包（手续费）税率梯度价结束金额应该大于起始金额");
                        }
                        InvoiceLadderPrice invoiceLadderPriceNext = totalServiceLadders.get(i + 1);
                        invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney());
                        if (invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney()) != 0) {
                            throw new CommonException(300, "总包（手续费）税率梯度价上梯度结束金额应等于下梯度起始金额");
                        }
                    }
                    totalServiceLadders.get(i).setTaxId(tax.getId());
                    totalServiceLadders.get(i).setTaxPackageId(totalTaxPackage.getId());
                }
                invoiceLadderPriceService.saveBatch(totalServiceLadders);
            }

            //分包汇总代开（开票）税率梯度价
            List<InvoiceLadderPriceDTO> totalCollectLaddersDto = taxDto.getTotalCollectLadders();
            List<InvoiceLadderPrice> totalCollectLadders = new ArrayList<>();
            if (totalCollectLaddersDto != null && totalCollectLaddersDto.size() > 0) {
                for (int i = 0; i < totalCollectLaddersDto.size(); i++) {
                    InvoiceLadderPrice invoiceLadderPrice = new InvoiceLadderPrice();
                    BeanUtils.copyProperties(totalCollectLaddersDto.get(i), invoiceLadderPrice);
                    totalCollectLadders.add(invoiceLadderPrice);
                }
            }

            //判断是否有梯度价
            if (!VerificationCheck.listIsNull(totalCollectLadders)) {
                //判断梯度价是否合理
                for (int i = 0; i < totalCollectLadders.size(); i++) {
                    if (i != totalCollectLadders.size() - 1) {
                        InvoiceLadderPrice invoiceLadderPrice = totalCollectLadders.get(i);
                        if (invoiceLadderPrice.getEndMoney().compareTo(invoiceLadderPrice.getStartMoney()) <= 0) {
                            throw new CommonException(300, "分包汇总代开（开票）税率梯度价结束金额应该大于起始金额");
                        }
                        InvoiceLadderPrice invoiceLadderPriceNext = totalCollectLadders.get(i + 1);
                        invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney());
                        if (invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney()) != 0) {
                            throw new CommonException(300, "分包汇总代开（开票）税率梯度价上梯度结束金额应等于下梯度起始金额");
                        }
                    }
                    totalCollectLadders.get(i).setTaxId(tax.getId());
                    totalCollectLadders.get(i).setTaxPackageId(totalTaxPackage.getId());
                }
                invoiceLadderPriceService.saveBatch(totalCollectLadders);
            }

            //分包单人单开（开票）税率梯度价
            List<InvoiceLadderPriceDTO> totalSingleLaddersDto = taxDto.getTotalSingleLadders();
            List<InvoiceLadderPrice> totalSingleLadders = new ArrayList<>();
            if (totalSingleLaddersDto != null && totalSingleLaddersDto.size() > 0) {
                for (int i = 0; i < totalSingleLaddersDto.size(); i++) {
                    InvoiceLadderPrice invoiceLadderPrice = new InvoiceLadderPrice();
                    BeanUtils.copyProperties(totalSingleLaddersDto.get(i), invoiceLadderPrice);
                    totalSingleLadders.add(invoiceLadderPrice);
                }
            }

            //判断是否有梯度价
            if (!VerificationCheck.listIsNull(totalSingleLadders)) {
                //判断梯度价是否合理
                for (int i = 0; i < totalSingleLadders.size(); i++) {
                    if (i != totalSingleLadders.size() - 1) {
                        InvoiceLadderPrice invoiceLadderPrice = totalSingleLadders.get(i);
                        if (invoiceLadderPrice.getEndMoney().compareTo(invoiceLadderPrice.getStartMoney()) <= 0) {
                            throw new CommonException(300, "分包单人单开（开票）税率梯度价结束金额应该大于起始金额");
                        }
                        InvoiceLadderPrice invoiceLadderPriceNext = totalSingleLadders.get(i + 1);
                        invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney());
                        if (invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney()) != 0) {
                            throw new CommonException(300, "分包单人单开（开票）税率梯度价上梯度结束金额应等于下梯度起始金额");
                        }
                    }
                    totalSingleLadders.get(i).setTaxId(tax.getId());
                    totalSingleLadders.get(i).setTaxPackageId(totalTaxPackage.getId());
                }
                invoiceLadderPriceService.saveBatch(totalSingleLadders);
            }
        }

        //判断是否有众包，有众包就添加
        TaxPackageDTO manyTaxPackageDTO = taxDto.getManyTaxPackage();
        if (manyTaxPackageDTO != null) {

            //商户-服务商众包合作信息操作
            TaxPackage manyTaxPackage = new TaxPackage();
            BeanUtils.copyProperties(manyTaxPackageDTO, manyTaxPackage);
            manyTaxPackage.setTaxId(tax.getId());
            taxPackageService.save(manyTaxPackage);

            //众包（手续费）税率梯度价
            List<InvoiceLadderPriceDTO> manyServiceLaddersDto = taxDto.getManyServiceLadders();
            List<InvoiceLadderPrice> manyServiceLadders = new ArrayList<>();
            if (manyServiceLaddersDto != null && manyServiceLaddersDto.size() > 0) {
                for (int i = 0; i < manyServiceLaddersDto.size(); i++) {
                    InvoiceLadderPrice invoiceLadderPrice = new InvoiceLadderPrice();
                    BeanUtils.copyProperties(manyServiceLaddersDto.get(i), invoiceLadderPrice);
                    manyServiceLadders.add(invoiceLadderPrice);
                }
            }

            //判断是否有梯度价
            if (!VerificationCheck.listIsNull(manyServiceLadders)) {
                //判断梯度价是否合理
                for (int i = 0; i < manyServiceLadders.size(); i++) {
                    if (i != manyServiceLadders.size() - 1) {
                        InvoiceLadderPrice invoiceLadderPrice = manyServiceLadders.get(i);
                        if (invoiceLadderPrice.getEndMoney().compareTo(invoiceLadderPrice.getStartMoney()) <= 0) {
                            throw new CommonException(300, "众包（手续费）税率梯度价结束金额应该大于起始金额");
                        }
                        InvoiceLadderPrice invoiceLadderPriceNext = manyServiceLadders.get(i + 1);
                        invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney());
                        if (invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney()) != 0) {
                            throw new CommonException(300, "众包（手续费）税率梯度价上梯度结束金额应等于下梯度起始金额");
                        }
                    }
                    manyServiceLadders.get(i).setTaxId(tax.getId());
                    manyServiceLadders.get(i).setTaxPackageId(manyTaxPackage.getId());
                }
                invoiceLadderPriceService.saveBatch(manyServiceLadders);
            }

            //众包（开票）税率梯度价
            List<InvoiceLadderPriceDTO> manyLaddersDto = taxDto.getManyLadders();
            List<InvoiceLadderPrice> manyLadders = new ArrayList<>();
            if (manyLaddersDto != null && manyLaddersDto.size() > 0) {
                for (int i = 0; i < manyLaddersDto.size(); i++) {
                    InvoiceLadderPrice invoiceLadderPrice = new InvoiceLadderPrice();
                    BeanUtils.copyProperties(manyLaddersDto.get(i), invoiceLadderPrice);
                    manyLadders.add(invoiceLadderPrice);
                }
            }

            //判断是否有梯度价
            if (!VerificationCheck.listIsNull(manyLadders)) {
                //判断梯度价是否合理
                for (int i = 0; i < manyLadders.size(); i++) {
                    if (i != manyLadders.size() - 1) {
                        InvoiceLadderPrice invoiceLadderPrice = manyLadders.get(i);
                        if (invoiceLadderPrice.getEndMoney().compareTo(invoiceLadderPrice.getStartMoney()) <= 0) {
                            throw new CommonException(300, "众包（开票）税率梯度价结束金额应该大于起始金额");
                        }
                        InvoiceLadderPrice invoiceLadderPriceNext = manyLadders.get(i + 1);
                        invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney());
                        if (invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney()) != 0) {
                            throw new CommonException(300, "众包（开票）税率梯度价上梯度结束金额应等于下梯度起始金额");
                        }
                    }
                    manyLadders.get(i).setTaxId(tax.getId());
                    manyLadders.get(i).setTaxPackageId(manyTaxPackage.getId());
                }
                invoiceLadderPriceService.saveBatch(manyLadders);
            }
        }

        return ReturnJson.success("操作成功！");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson updateTaxPackage(TaxPackageUpdateDTO taxPackageUpdateDTO) throws Exception {

        //删除所有商户-服务商总包众包合作信息
        companyTaxService.deleteCompanyTax(taxPackageUpdateDTO.getTaxId());
        //删除服务商总包众包合作信息
        taxPackageService.deleteTaxPackage(taxPackageUpdateDTO.getTaxId());

        //判断是否有总包，有总包就添加
        TaxPackageDTO totalTaxPackageDTO = taxPackageUpdateDTO.getTotalTaxPackage();
        if (totalTaxPackageDTO != null) {

            //商户-服务商总包合作信息操作
            TaxPackage totalTaxPackage = new TaxPackage();
            BeanUtils.copyProperties(totalTaxPackageDTO, totalTaxPackage);
            totalTaxPackage.setTaxId(taxPackageUpdateDTO.getTaxId());
            taxPackageService.save(totalTaxPackage);

            //总包（手续费）税率梯度价
            List<InvoiceLadderPriceDTO> totalServiceLaddersDTO = taxPackageUpdateDTO.getTotalServiceLadders();
            List<InvoiceLadderPrice> totalServiceLadders = new ArrayList<>();
            if (totalServiceLaddersDTO != null && totalServiceLaddersDTO.size() > 0) {
                for (int i = 0; i < totalServiceLaddersDTO.size(); i++) {
                    InvoiceLadderPrice invoiceLadderPrice = new InvoiceLadderPrice();
                    BeanUtils.copyProperties(totalServiceLaddersDTO.get(i), invoiceLadderPrice);
                    totalServiceLadders.add(invoiceLadderPrice);
                }
            }

            //判断是否有梯度价
            if (!VerificationCheck.listIsNull(totalServiceLadders)) {
                //判断梯度价是否合理
                for (int i = 0; i < totalServiceLadders.size(); i++) {
                    if (i != totalServiceLadders.size() - 1) {
                        InvoiceLadderPrice invoiceLadderPrice = totalServiceLadders.get(i);
                        if (invoiceLadderPrice.getEndMoney().compareTo(invoiceLadderPrice.getStartMoney()) <= 0) {
                            throw new CommonException(300, "总包（手续费）税率梯度价结束金额应该大于起始金额");
                        }
                        InvoiceLadderPrice invoiceLadderPriceNext = totalServiceLadders.get(i + 1);
                        invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney());
                        if (invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney()) != 0) {
                            throw new CommonException(300, "总包（手续费）税率梯度价上梯度结束金额应等于下梯度起始金额");
                        }
                    }
                    totalServiceLadders.get(i).setTaxId(taxPackageUpdateDTO.getTaxId());
                    totalServiceLadders.get(i).setTaxPackageId(totalTaxPackage.getId());
                }
                invoiceLadderPriceService.saveBatch(totalServiceLadders);
            }

            //分包汇总代开（开票）税率梯度价
            List<InvoiceLadderPriceDTO> totalCollectLaddersDto = taxPackageUpdateDTO.getTotalCollectLadders();
            List<InvoiceLadderPrice> totalCollectLadders = new ArrayList<>();
            if (totalCollectLaddersDto != null && totalCollectLaddersDto.size() > 0) {
                for (int i = 0; i < totalCollectLaddersDto.size(); i++) {
                    InvoiceLadderPrice invoiceLadderPrice = new InvoiceLadderPrice();
                    BeanUtils.copyProperties(totalCollectLaddersDto.get(i), invoiceLadderPrice);
                    totalCollectLadders.add(invoiceLadderPrice);
                }
            }

            //判断是否有梯度价
            if (!VerificationCheck.listIsNull(totalCollectLadders)) {
                //判断梯度价是否合理
                for (int i = 0; i < totalCollectLadders.size(); i++) {
                    if (i != totalCollectLadders.size() - 1) {
                        InvoiceLadderPrice invoiceLadderPrice = totalCollectLadders.get(i);
                        if (invoiceLadderPrice.getEndMoney().compareTo(invoiceLadderPrice.getStartMoney()) <= 0) {
                            throw new CommonException(300, "分包汇总代开（开票）税率梯度价结束金额应该大于起始金额");
                        }
                        InvoiceLadderPrice invoiceLadderPriceNext = totalCollectLadders.get(i + 1);
                        invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney());
                        if (invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney()) != 0) {
                            throw new CommonException(300, "分包汇总代开（开票）税率梯度价上梯度结束金额应等于下梯度起始金额");
                        }
                    }
                    totalCollectLadders.get(i).setTaxId(taxPackageUpdateDTO.getTaxId());
                    totalCollectLadders.get(i).setTaxPackageId(totalTaxPackage.getId());
                }
                invoiceLadderPriceService.saveBatch(totalCollectLadders);
            }

            //分包单人单开（开票）税率梯度价
            List<InvoiceLadderPriceDTO> totalSingleLaddersDto = taxPackageUpdateDTO.getTotalSingleLadders();
            List<InvoiceLadderPrice> totalSingleLadders = new ArrayList<>();
            if (totalSingleLaddersDto != null && totalSingleLaddersDto.size() > 0) {
                for (int i = 0; i < totalSingleLaddersDto.size(); i++) {
                    InvoiceLadderPrice invoiceLadderPrice = new InvoiceLadderPrice();
                    BeanUtils.copyProperties(totalSingleLaddersDto.get(i), invoiceLadderPrice);
                    totalSingleLadders.add(invoiceLadderPrice);
                }
            }

            //判断是否有梯度价
            if (!VerificationCheck.listIsNull(totalSingleLadders)) {
                //判断梯度价是否合理
                for (int i = 0; i < totalSingleLadders.size(); i++) {
                    if (i != totalSingleLadders.size() - 1) {
                        InvoiceLadderPrice invoiceLadderPrice = totalSingleLadders.get(i);
                        if (invoiceLadderPrice.getEndMoney().compareTo(invoiceLadderPrice.getStartMoney()) <= 0) {
                            throw new CommonException(300, "分包单人单开（开票）税率梯度价结束金额应该大于起始金额");
                        }
                        InvoiceLadderPrice invoiceLadderPriceNext = totalSingleLadders.get(i + 1);
                        invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney());
                        if (invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney()) != 0) {
                            throw new CommonException(300, "分包单人单开（开票）税率梯度价上梯度结束金额应等于下梯度起始金额");
                        }
                    }
                    totalSingleLadders.get(i).setTaxId(taxPackageUpdateDTO.getTaxId());
                    totalSingleLadders.get(i).setTaxPackageId(totalTaxPackage.getId());
                }
                invoiceLadderPriceService.saveBatch(totalSingleLadders);
            }
        }

        //判断是否有众包，有众包就添加
        TaxPackageDTO manyTaxPackageDTO = taxPackageUpdateDTO.getManyTaxPackage();
        if (manyTaxPackageDTO != null) {

            //商户-服务商众包合作信息操作
            TaxPackage manyTaxPackage = new TaxPackage();
            BeanUtils.copyProperties(manyTaxPackageDTO, manyTaxPackage);
            manyTaxPackage.setTaxId(taxPackageUpdateDTO.getTaxId());
            taxPackageService.save(manyTaxPackage);

            //众包（手续费）税率梯度价
            List<InvoiceLadderPriceDTO> manyServiceLaddersDto = taxPackageUpdateDTO.getManyServiceLadders();
            List<InvoiceLadderPrice> manyServiceLadders = new ArrayList<>();
            if (manyServiceLaddersDto != null && manyServiceLaddersDto.size() > 0) {
                for (int i = 0; i < manyServiceLaddersDto.size(); i++) {
                    InvoiceLadderPrice invoiceLadderPrice = new InvoiceLadderPrice();
                    BeanUtils.copyProperties(manyServiceLaddersDto.get(i), invoiceLadderPrice);
                    manyServiceLadders.add(invoiceLadderPrice);
                }
            }

            //判断是否有梯度价
            if (!VerificationCheck.listIsNull(manyServiceLadders)) {
                //判断梯度价是否合理
                for (int i = 0; i < manyServiceLadders.size(); i++) {
                    if (i != manyServiceLadders.size() - 1) {
                        InvoiceLadderPrice invoiceLadderPrice = manyServiceLadders.get(i);
                        if (invoiceLadderPrice.getEndMoney().compareTo(invoiceLadderPrice.getStartMoney()) <= 0) {
                            throw new CommonException(300, "众包（手续费）税率梯度价结束金额应该大于起始金额");
                        }
                        InvoiceLadderPrice invoiceLadderPriceNext = manyServiceLadders.get(i + 1);
                        invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney());
                        if (invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney()) != 0) {
                            throw new CommonException(300, "众包（手续费）税率梯度价上梯度结束金额应等于下梯度起始金额");
                        }
                    }
                    manyServiceLadders.get(i).setTaxId(taxPackageUpdateDTO.getTaxId());
                    manyServiceLadders.get(i).setTaxPackageId(manyTaxPackage.getId());
                }
                invoiceLadderPriceService.saveBatch(manyServiceLadders);
            }

            //众包（开票）税率梯度价
            List<InvoiceLadderPriceDTO> manyLaddersDto = taxPackageUpdateDTO.getManyLadders();
            List<InvoiceLadderPrice> manyLadders = new ArrayList<>();
            if (manyLaddersDto != null && manyLaddersDto.size() > 0) {
                for (int i = 0; i < manyLaddersDto.size(); i++) {
                    InvoiceLadderPrice invoiceLadderPrice = new InvoiceLadderPrice();
                    BeanUtils.copyProperties(manyLaddersDto.get(i), invoiceLadderPrice);
                    manyLadders.add(invoiceLadderPrice);
                }
            }

            //判断是否有梯度价
            if (!VerificationCheck.listIsNull(manyLadders)) {
                //判断梯度价是否合理
                for (int i = 0; i < manyLadders.size(); i++) {
                    if (i != manyLadders.size() - 1) {
                        InvoiceLadderPrice invoiceLadderPrice = manyLadders.get(i);
                        if (invoiceLadderPrice.getEndMoney().compareTo(invoiceLadderPrice.getStartMoney()) <= 0) {
                            throw new CommonException(300, "众包（开票）税率梯度价结束金额应该大于起始金额");
                        }
                        InvoiceLadderPrice invoiceLadderPriceNext = manyLadders.get(i + 1);
                        invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney());
                        if (invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney()) != 0) {
                            throw new CommonException(300, "众包（开票）税率梯度价上梯度结束金额应等于下梯度起始金额");
                        }
                    }
                    manyLadders.get(i).setTaxId(taxPackageUpdateDTO.getTaxId());
                    manyLadders.get(i).setTaxPackageId(manyTaxPackage.getId());
                }
                invoiceLadderPriceService.saveBatch(manyLadders);
            }
        }

        return ReturnJson.success("操作成功");
    }

    @Override
    public ReturnJson getTaxList(TaxListDTO taxListDto, String merchantId) {
        if (merchantId != null) {
            Merchant merchant = merchantDao.selectById(merchantId);
            Page<TaxListPO> taxListPOPage = new Page<>(taxListDto.getPageNo(), taxListDto.getPageSize());
            IPage<TaxListPO> taxListPage = taxDao.selectTaxList(taxListPOPage, taxListDto.getTaxName(), taxListDto.getStartDate(), taxListDto.getEndDate(), merchant.getCompanyId());
            return ReturnJson.success(taxListPage);
        } else {
            Page<TaxListPO> taxListPOPage = new Page<>(taxListDto.getPageNo(), taxListDto.getPageSize());
            IPage<TaxListPO> taxListPage = taxDao.selectTaxList(taxListPOPage, taxListDto.getTaxName(), taxListDto.getStartDate(), taxListDto.getEndDate(), null);
            return ReturnJson.success(taxListPage);
        }
    }

    @Override
    public ReturnJson getTaxInfo(String taxId) {
        //服务商信息
        Tax tax = taxDao.selectById(taxId);
        TaxPlatformVO taxPlatformVO = new TaxPlatformVO();
        BeanUtils.copyProperties(tax, taxPlatformVO);

        //服务商总包合作信息
        TaxPackage totalTaxPackage = taxPackageService.getOne(new QueryWrapper<TaxPackage>().lambda()
                .eq(TaxPackage::getTaxId, taxId)
                .eq(TaxPackage::getPackageStatus, 0));

        if (totalTaxPackage != null) {
            List<InvoiceLadderPrice> totalLadder = invoiceLadderPriceService.list(
                    new QueryWrapper<InvoiceLadderPrice>().lambda()
                            .eq(InvoiceLadderPrice::getTaxPackageId, totalTaxPackage.getId()));
            taxPlatformVO.setTotalTaxPackage(totalTaxPackage);
            taxPlatformVO.setTotalLadders(totalLadder);
        }

        //服务商众包合作信息
        TaxPackage manyTaxPackage = taxPackageService.getOne(new QueryWrapper<TaxPackage>().lambda()
                .eq(TaxPackage::getTaxId, taxId)
                .eq(TaxPackage::getPackageStatus, 1));

        if (manyTaxPackage != null) {
            List<InvoiceLadderPrice> manyLadder = invoiceLadderPriceService.list(
                    new QueryWrapper<InvoiceLadderPrice>().lambda()
                            .eq(InvoiceLadderPrice::getTaxPackageId, manyTaxPackage.getId()));
            taxPlatformVO.setManyTaxPackage(manyTaxPackage);
            taxPlatformVO.setManyLadders(manyLadder);
        }

        //主账号信息
        QueryWrapper<TaxWorker> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TaxWorker::getTaxId, taxId).eq(TaxWorker::getParentId, 0);
        TaxWorker taxWorker = taxWorkerService.getOne(queryWrapper);

        taxPlatformVO.setUserName(taxWorker.getUserName());
        taxPlatformVO.setLoginMobile(taxWorker.getLoginMobile());

        return ReturnJson.success(taxPlatformVO);
    }

    @Override
    public ReturnJson transactionRecordCount(String taxId) {
        HomePageVO homePageVO = new HomePageVO();
        BigDecimal total30DayMoney = paymentOrderDao.selectBy30DayPaasTax(taxId);
        homePageVO.setPayment30TotalMoney(total30DayMoney);
        BigDecimal totalMoney = paymentOrderDao.selectTotalPaasTax(taxId);
        homePageVO.setPaymentTotalMoney(totalMoney);
        BigDecimal totalServiceMoney=paymentOrderDao.selectTotalServicePaasTax(taxId);
        homePageVO.setPaymentTotalServiceMoney(totalServiceMoney);

        BigDecimal many30DayMoney = paymentOrderManyDao.selectBy30DayPaasTax(taxId);
        homePageVO.setPayment30ManyMoney(many30DayMoney);
        BigDecimal manyMoney = paymentOrderManyDao.selectTotalPaasTax(taxId);
        homePageVO.setPaymentManyMoney(manyMoney);
        BigDecimal manyServiceMoney = paymentOrderManyDao.selectTotalServicePaasTax(taxId);
        homePageVO.setPaymentManyServiceMoney(manyServiceMoney);

        BigDecimal invoiceManyDKMoney = paymentOrderManyDao.selectInvoiceManyDKMoneyPaasTax(taxId);
        homePageVO.setInvoiceManyDKMoney(invoiceManyDKMoney);

        InvoicePO totalInvoice = invoiceDao.selectInvoiceMoneyPaasTax(taxId);
        if (totalInvoice != null) {
            homePageVO.setInvoiceTotalCount(totalInvoice.getCount());
            homePageVO.setInvoiceTotalMoney(totalInvoice.getTotalMoney());
        }

        InvoicePO manyInvoice = crowdSourcingInvoiceDao.selectCrowdInvoiceMoneyPaasTax(taxId);
        homePageVO.setInvoiceManyCount(manyInvoice.getCount());
        homePageVO.setInvoiceManyMoney(manyInvoice.getTotalMoney());

        //商户数queryAppletFaqById
        Integer count = companyTaxService.count(new QueryWrapper<CompanyTax>().lambda()
                .eq(CompanyTax::getTaxId, taxId));
        homePageVO.setMerchantTotal(count);
        //创客数
        Integer countWorker = workerDao.queryWorkerCount(taxId, null);
        homePageVO.setWorkerTotal(countWorker);

        //获取10条具体的交易流水
        ReturnJson returnJson = this.transactionRecord(taxId, null, 1, 10);
        returnJson.setObj(homePageVO);
        return returnJson;
    }

    @Override
    public ReturnJson transactionRecord(String taxId, String merchantId, Integer page, Integer pageSize) {
        List<String> ids = new ArrayList<>();
        QueryWrapper<PaymentOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(PaymentOrder::getTaxId, taxId)
                .eq(StringUtils.isNotBlank(merchantId), PaymentOrder::getCompanyId, merchantId)
                .eq(PaymentOrder::getPaymentOrderStatus, 6);
        List<PaymentOrder> paymentOrders = paymentOrderDao.selectList(queryWrapper);
        for (PaymentOrder paymentOrder : paymentOrders) {
            ids.add(paymentOrder.getId());
        }

        QueryWrapper<PaymentOrderMany> queryWrapperMany = new QueryWrapper<>();
        queryWrapperMany.lambda()
                .eq(PaymentOrderMany::getTaxId, taxId)
                .eq(StringUtils.isNotBlank(merchantId), PaymentOrderMany::getCompanyId, merchantId)
                .eq(PaymentOrderMany::getPaymentOrderStatus, 3);
        List<PaymentOrderMany> paymentOrderManies = paymentOrderManyDao.selectList(queryWrapperMany);
        for (PaymentOrderMany paymentOrderMany : paymentOrderManies) {
            ids.add(paymentOrderMany.getId());
        }
        if (VerificationCheck.listIsNull(ids)) {
            return ReturnJson.success(ids);
        }
        Page<MerchantPaymentListPO> taxPage = new Page<>(page, pageSize);
        IPage<MerchantPaymentListPO> taxPaymentListPage = taxDao.selectTaxPaymentList(taxPage, ids);
        return ReturnJson.success(taxPaymentListPage);
    }

    @Override
    public ReturnJson getSellerById(String id) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        SellerVO sellerVo = taxDao.getSellerById(id);
        if (sellerVo != null) {
            returnJson = new ReturnJson("查询成功", sellerVo, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getTaxPaasList() {
        List<Tax> taxList = taxDao.selectList(new QueryWrapper<Tax>().lambda()
                .eq(Tax::getTaxStatus, 0));
        List<TaxListVO> taxListVOS = new ArrayList<>();
        for (int i = 0; i < taxList.size(); i++) {
            TaxListVO taxListVo = new TaxListVO();
            BeanUtils.copyProperties(taxList.get(i), taxListVo);
            taxListVOS.add(taxListVo);
        }
        return ReturnJson.success(taxListVOS);
    }

    @Override
    public ReturnJson getTaxList(Integer packageStatus) {
        List<TaxListVO> taxListVOS = taxDao.getTaxPaasList(packageStatus);
        return ReturnJson.success(taxListVOS);
    }

    @Override
    public ReturnJson queryTaxTransactionFlow(String taxId, Integer page, Integer pageSize) {
        Page taxPage = new Page(page, pageSize);
        IPage<TaxTransactionFlowVO> taxTransactionFlowVOS = taxDao.queryTaxTransactionFlow(taxId, taxPage);
        return ReturnJson.success(taxTransactionFlowVOS);
    }

    @Override
    public ReturnJson queryTaxCompanyList(String taxId, String userId, Integer pageNo, Integer pageSize) {
        Merchant merchant = merchantDao.selectById(userId);
        if (merchant == null) {
            return ReturnJson.error("不存在此商户，请重新登录！");
        }
        Page page = new Page(pageNo, pageSize);
        IPage iPage = taxDao.getTaxCompanyFlow(page, merchant.getCompanyId(), taxId);
        return ReturnJson.success(iPage);
    }

    @Override
    public ReturnJson queryCompanyFlowInfo(String userId, String taxId) {
        Merchant merchant = merchantDao.selectById(userId);
        if (merchant == null) {
            return ReturnJson.error("商户不存在，请重新登录");
        }
        String companyId = merchant.getCompanyId();
        //获取众包三十天支付流水
        BigDecimal payment30ManyMoney = paymentOrderManyDao.getFlowInfo(companyId, taxId, 30);
        //获取总包三十天支付流水
        BigDecimal payment30TotalMoney = paymentOrderDao.getFlowInfo(companyId, taxId, 30);
        //获取众包总支付流水
        BigDecimal paymentManyMoney = paymentOrderManyDao.getFlowInfo(companyId, taxId, null);
        //获取总包总支付流水
        BigDecimal paymentTotalMoney = paymentOrderDao.getFlowInfo(companyId, taxId, null);
        //获取总包发票数
        Integer invoiceTotalCount = paymentOrderDao.getInvoiceTotalCount(companyId, taxId);
        //获取总包发票金额
        BigDecimal invoiceTotalMoney = paymentOrderDao.getInvoiceTotalMoney(companyId, taxId);
        //总包+分包总服务费
        BigDecimal paymentTotalServiceMoney=paymentOrderDao.getPaymentTotalServiceMoney(companyId, taxId);
        //获取众包发票金额
        BigDecimal invoiceManyMoney = paymentOrderManyDao.getPaymentManyMoney(companyId, taxId);
        //获取众包发票数
        Integer invoiceManyCount = paymentOrderManyDao.getPaymentManyCount(companyId, taxId);
        //众包总服务费
        BigDecimal paymentManyServiceMoney=paymentManyMoney;
        //众包的发票代开金额
        BigDecimal invoiceManyDKMoney=paymentOrderManyDao.queryInvoiceManyDKMoney(companyId, taxId);


        CompanyFlowInfoVO companyFlowInfoVO = new CompanyFlowInfoVO();
        companyFlowInfoVO.setPayment30ManyMoney(payment30ManyMoney);
        companyFlowInfoVO.setPayment30TotalMoney(payment30TotalMoney);
        companyFlowInfoVO.setPaymentManyMoney(paymentManyMoney);
        companyFlowInfoVO.setPaymentTotalMoney(paymentTotalMoney);
        companyFlowInfoVO.setInvoiceManyCount(invoiceManyCount);
        companyFlowInfoVO.setInvoiceTotalCount(invoiceTotalCount);
        companyFlowInfoVO.setInvoiceManyMoney(invoiceManyMoney);
        companyFlowInfoVO.setInvoiceTotalMoney(invoiceTotalMoney);
        companyFlowInfoVO.setPaymentManyServiceMoney(paymentManyServiceMoney);
        companyFlowInfoVO.setPaymentTotalServiceMoney(paymentTotalServiceMoney);
        companyFlowInfoVO.setInvoiceManyDKMoney(invoiceManyDKMoney);
        return ReturnJson.success(companyFlowInfoVO);
    }

    @Override
    public ReturnJson updateTaxStatus(String taxId, Integer taxStatus) {

        Tax tax = getById(taxId);
        if (tax == null) {
            return ReturnJson.error("服务商不存在");
        }

        if (!(taxStatus.equals(tax.getTaxStatus()))) {
            tax.setTaxStatus(taxStatus);
            updateById(tax);
        }

        return ReturnJson.success("操作成功");
    }

    @Override
    public ReturnJson deleteInvoiceCatalog(String invoiceCatalogId) {
        InvoiceCatalog invoiceCatalog = invoiceCatalogDao.selectById(invoiceCatalogId);
        if (invoiceCatalog != null) {
            if (invoiceCatalog.getIsNot()) {
                invoiceCatalogDao.deleteById(invoiceCatalog);
                return ReturnJson.success("删除成功！");
            }
            return ReturnJson.error("此开票类目为系统默认，不能删除！");
        }
        return ReturnJson.error("找不到此信息！");
    }

    @Override
    public ReturnJson queryTaxInBankInfo(String taxId) {
        TaxInBankInfoVO taxInBankInfoVO = taxDao.queryTaxInBankInfo(taxId);
        return ReturnJson.success(taxInBankInfoVO);
    }

}
