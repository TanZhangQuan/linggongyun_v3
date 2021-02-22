package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.config.JwtConfig;
import com.example.common.util.MD5;
import com.example.common.util.ReturnJson;
import com.example.common.util.VerificationCheck;
import com.example.merchant.dto.platform.*;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.*;
import com.example.merchant.util.AcquireID;
import com.example.merchant.vo.platform.QuerySalesmanVO;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.po.AgentListPO;
import com.example.mybatis.po.SalesManPaymentListPO;
import com.example.mybatis.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StructureServiceImpl implements StructureService {

    @Resource
    private ManagersService managersService;

    @Resource
    private CompanyInfoDao companyInfoDao;

    @Resource
    private AgentDao agentDao;

    @Resource
    private HomePageService homePageService;

    @Resource
    private ManagersDao managersDao;

    @Resource
    private AcquireID acquireID;

    @Resource
    private MerchantService merchantService;

    @Resource
    private AgentService agentService;

    @Resource
    private MenuDao menuDao;

    @Resource
    private ObjectMenuService objectMenuService;

    @Resource
    private AgentTaxDao agentTaxDao;

    @Resource
    private AgentLadderServiceDao agentLadderServiceDao;

    @Resource
    private CommissionProportionDao commissionProportionDao;

    @Resource
    private AchievementStatisticsDao achievementStatisticsDao;

    @Resource
    private TaxPackageDao taxPackageDao;

    @Resource
    private InvoiceLadderPriceDao invoiceLadderPriceDao;

    @Override
    @Transactional
    public ReturnJson addSalesMan(ManagersDTO managersDto) {

//        List<CommissionProportionDTO> agentCommissionProportion = managersDto.getAgentCommissionProportion();
//        if (agentCommissionProportion != null && agentCommissionProportion.size() > 0) {
//            Collections.sort(agentCommissionProportion);
//            for (int i = 0; i < agentCommissionProportion.size(); i++) {
//                if (agentCommissionProportion.get(i).getEndMoney().compareTo(agentCommissionProportion.get(i).getStartMoney()) < 0) {
//                    return new ReturnJson("您输入的阶梯有问题！", 300);
//                }
//                if (i < agentCommissionProportion.size() - 1) {
//                    if (agentCommissionProportion.get(i).getEndMoney().compareTo(agentCommissionProportion.get(i + 1).getStartMoney()) > 0) {
//                        return new ReturnJson("您输入的阶梯有问题！", 300);
//                    }
//                }
//            }
//        }
//
//        List<CommissionProportionDTO> directCommissionProportion = managersDto.getDirectCommissionProportion();
//        if (directCommissionProportion != null && directCommissionProportion.size() > 0) {
//            Collections.sort(directCommissionProportion);
//            for (int i = 0; i < directCommissionProportion.size(); i++) {
//                if (directCommissionProportion.get(i).getEndMoney().compareTo(directCommissionProportion.get(i).getStartMoney()) < 0) {
//                    return new ReturnJson("您输入的阶梯有问题！", 300);
//                }
//                if (i < directCommissionProportion.size() - 1) {
//                    if (directCommissionProportion.get(i).getEndMoney().compareTo(directCommissionProportion.get(i + 1).getStartMoney()) > 0) {
//                        return new ReturnJson("您输入的阶梯有问题！", 300);
//                    }
//                }
//            }
//        }

        Managers managersOne = managersDao.selectOne(new QueryWrapper<Managers>().lambda()
                .eq(Managers::getMobileCode, managersDto.getMobileCode()));
        Managers managers = managersDao.selectById(managersDto.getId());
        if (managers == null) {
            if (managersOne != null) {
                return ReturnJson.error("此手机号码已近注册过！");
            }
            managers = new Managers();
            BeanUtils.copyProperties(managersDto, managers);
            managers.setRoleName("业务员");
            managers.setPassWord(MD5.md5(JwtConfig.getSecretKey() + managersDto.getInitPassWord()));
            managersService.save(managers);
            //赋予主账号权限
            List<MenuListVO> listVos = menuDao.getAgentMenuList();
            List<ObjectMenu> objectMenuList = new ArrayList<>();
            for (int i = 0; i < listVos.size(); i++) {
                ObjectMenu objectMenu = new ObjectMenu();
                objectMenu.setMenuId(listVos.get(i).getId());
                objectMenu.setObjectUserId(managers.getId());
                objectMenuList.add(objectMenu);
                if (listVos.get(i).getList() != null && listVos.get(i).getList().size() > 0) {
                    for (MenuVO menu : listVos.get(i).getList()) {
                        objectMenu = new ObjectMenu();
                        objectMenu.setMenuId(menu.getId());
                        objectMenu.setObjectUserId(managers.getId());
                        objectMenuList.add(objectMenu);
                        for (Menu menu1 : menu.getList()) {
                            objectMenu = new ObjectMenu();
                            objectMenu.setMenuId(menu1.getId());
                            objectMenu.setObjectUserId(managers.getId());
                            objectMenuList.add(objectMenu);
                        }
                    }
                }
            }
            objectMenuService.saveBatch(objectMenuList);
        } else {
            if (!managersOne.getId().equals(managers.getId())) {
                return ReturnJson.error("此手机号码已近注册过！");
            }
            BeanUtils.copyProperties(managersDto, managers);
            if (managers.getPassWord() != null) {
                managers.setPassWord(MD5.md5(JwtConfig.getSecretKey() + managersDto.getInitPassWord()));
            }
            managersService.updateById(managers);
        }
//        commissionProportionDao.deleteObjectIdAndObjectTypeAndCustomerType(managers.getId(), 0, 1);
//        for (int i = 0; i < agentCommissionProportion.size(); i++) {
//
//            CommissionProportion commissionProportion = new CommissionProportion();
//            BeanUtils.copyProperties(agentCommissionProportion.get(i), commissionProportion);
//            commissionProportion.setObjectId(managers.getId());
//            commissionProportionDao.insert(commissionProportion);
//        }
//        commissionProportionDao.deleteObjectIdAndObjectTypeAndCustomerType(managers.getId(), 0, 0);
//        for (int i = 0; i < directCommissionProportion.size(); i++) {
//            CommissionProportion commissionProportion = new CommissionProportion();
//            BeanUtils.copyProperties(directCommissionProportion.get(i), commissionProportion);
//            commissionProportion.setObjectId(managers.getId());
//            commissionProportionDao.insert(commissionProportion);
//        }
        return ReturnJson.success("成功！");
    }

    /**
     * 按ID查找业务员
     *
     * @param managersId
     * @return
     */
    @Override
    public ReturnJson findBySalesManId(String managersId) {
        Managers managersEntity = managersService.getById(managersId);
        if (null == managersEntity) {
            return ReturnJson.error("没有此业务员");
        }
        managersEntity.setPassWord("");
        ManagersVO managers = new ManagersVO();
        BeanUtils.copyProperties(managersEntity, managers);
//        List<CommissionProportionVO> directCustomer = commissionProportionDao.getObjectIdAndObjectTypeAndCustomerType(managersId, 0, 0);
//        List<CommissionProportionVO> agent = commissionProportionDao.getObjectIdAndObjectTypeAndCustomerType(managersId, 0, 1);
//        managers.setAgent(agent);
//        managers.setDirectCustomer(directCustomer);

        return ReturnJson.success(managers);
    }

    /**
     * 分页查询所以业务员
     *
     * @return
     */
    @Override
    public ReturnJson getSalesManAll(Integer page, Integer pageSize) {
        Page<Managers> managersPage = new Page<>(page, pageSize);
        IPage<Managers> managersIPage = managersService.page(managersPage,
                new QueryWrapper<Managers>().lambda()
                        .eq(Managers::getUserSign, 2));
        return ReturnJson.success(managersIPage);
    }

    @Override
    public ReturnJson removeSalesMan(String salesManId) throws CommonException {
        Integer companyInfoCount = companyInfoDao.selectCount(
                new QueryWrapper<CompanyInfo>().lambda()
                        .eq(CompanyInfo::getSalesManId, salesManId));
        Integer agentCount = agentDao.selectCount(
                new QueryWrapper<Agent>().lambda()
                        .eq(Agent::getSalesManId, salesManId));
        if (companyInfoCount + agentCount == 0) {
            managersService.removeById(salesManId);
            return ReturnJson.success("删除成功！");
        }
        homePageService.getHomePageInofpaas(salesManId);
        return ReturnJson.error("该业务员有业务记录，只能停用！");
    }


    @Override
    public ReturnJson getSalesManPaymentListCount(String salesManId) throws CommonException {
        List<String> companyIds = acquireID.getCompanyIds(salesManId);
        if (VerificationCheck.listIsNull(companyIds)) {
            return ReturnJson.error("该业务员还没有产生流水！");
        }
        IPage<SalesManPaymentListPO> salesManPaymentListPOIPage = managersDao.selectSalesManPaymentList(new Page(1, 10), companyIds);
        ReturnJson returnJson = ReturnJson.success(salesManPaymentListPOIPage);
        ReturnJson homePageInof = homePageService.getHomePageInofpaas(salesManId);
        homePageInof.setData(returnJson.getData());
        return homePageInof;
    }

    @Override
    public ReturnJson getSalesManPaymentList(String salesManId, Integer page, Integer pageSize) throws CommonException {
        List<String> companyIds = acquireID.getCompanyIds(salesManId);
        if (VerificationCheck.listIsNull(companyIds)) {
            return ReturnJson.error("该业务员还没有产生流水！");
        }
        Page<SalesManPaymentListPO> salesManPaymentListPOPage = new Page<>(page, pageSize);
        IPage<SalesManPaymentListPO> salesManPaymentListPOIPage = managersDao.selectSalesManPaymentList(salesManPaymentListPOPage, companyIds);
        return ReturnJson.success(salesManPaymentListPOIPage);
    }

    @Override
    public ReturnJson getSalesManPaymentInfo(String paymentOrderId, Integer packageStatus) {
        return merchantService.getMerchantPaymentInfo(paymentOrderId, packageStatus);
    }

    @Override
    public ReturnJson getPaymentInventory(String paymentOrderId, Integer page, Integer pageSize) {
        return merchantService.getMerchantPaymentInventory(paymentOrderId, page, pageSize);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson addAgent(AgentInfoDTO agentInfoDto) throws Exception {
//        List<CommissionProportionDTO> directCommissionProportion = agentInfoDto.getDirectCommissionProportion();
//        Collections.sort(directCommissionProportion);
//        for (int i = 0; i < directCommissionProportion.size(); i++) {
//            if (directCommissionProportion.get(i).getEndMoney().compareTo(directCommissionProportion.get(i).getStartMoney()) < 0) {
//                return new ReturnJson("您输入的阶梯有问题！", 300);
//            }
//            if (i < directCommissionProportion.size() - 1) {
//                if (directCommissionProportion.get(i).getEndMoney().compareTo(directCommissionProportion.get(i + 1).getStartMoney()) > 0) {
//                    return new ReturnJson("您输入的阶梯有问题！", 300);
//                }
//            }
//        }
        Managers managersOne = managersDao.selectOne(
                new QueryWrapper<Managers>().lambda()
                        .eq(Managers::getMobileCode, agentInfoDto.getLinkMobile()));
        Managers managers = new Managers();
        Agent agent = new Agent();
        //ID为空进入添加
        if (StringUtils.isEmpty(agentInfoDto.getAgentId())) {
            if (managersOne != null) {
                return ReturnJson.error("此手机号码已近注册过！");
            }
            managersOne = managersDao.selectOne(
                    new QueryWrapper<Managers>().lambda()
                            .eq(Managers::getUserName, agentInfoDto.getUserName()));
            if (managersOne != null) {
                return ReturnJson.error("此用户名已近注册过！");
            }
            managers.setPassWord(MD5.md5(JwtConfig.getSecretKey() + agentInfoDto.getInitPassWord()));
            managers.setRealName(agentInfoDto.getAgentName());
            managers.setUserName(agentInfoDto.getUserName());
            managers.setUserSign(agentInfoDto.getUserSign());
            managers.setMobileCode(agentInfoDto.getLinkMobile());
            managers.setStatus(agentInfoDto.getAgentStatus());
            managers.setRoleName("代理商");
            managersService.save(managers);
            BeanUtils.copyProperties(agentInfoDto, agent);
            agent.setManagersId(managers.getId());
            agentService.save(agent);

            //添加完之后自动为主账号赋予权限
            List<MenuListVO> listVos = menuDao.getAgentMenuList();
            List<ObjectMenu> objectMenuList = new ArrayList<>();
            for (int i = 0; i < listVos.size(); i++) {
                ObjectMenu objectMenu = new ObjectMenu();
                objectMenu.setMenuId(listVos.get(i).getId());
                objectMenu.setObjectUserId(managers.getId());
                objectMenuList.add(objectMenu);
                if (listVos.get(i).getList() != null && listVos.get(i).getList().size() > 0) {
                    for (MenuVO menu : listVos.get(i).getList()) {
                        objectMenu = new ObjectMenu();
                        objectMenu.setMenuId(menu.getId());
                        objectMenu.setObjectUserId(managers.getId());
                        objectMenuList.add(objectMenu);
                        for (Menu menu1 : menu.getList()) {
                            objectMenu = new ObjectMenu();
                            objectMenu.setMenuId(menu1.getId());
                            objectMenu.setObjectUserId(managers.getId());
                            objectMenuList.add(objectMenu);
                        }
                    }
                }
            }
            objectMenuService.saveBatch(objectMenuList);
        } else {
            managers = managersDao.selectById(agentInfoDto.getAgentId());
            if (!managersOne.getId().equals(managers.getId())) {
                return ReturnJson.error("此手机号码已近注册过！");
            }
            managersOne = managersDao.selectOne(
                    new QueryWrapper<Managers>().lambda()
                            .eq(Managers::getUserName, agentInfoDto.getUserName()));
            if (!managersOne.getUserName().equals(managers.getUserName())) {
                return ReturnJson.error("此用户名已近注册过！");
            }
            //编辑
            managers.setPassWord(MD5.md5(JwtConfig.getSecretKey() + agentInfoDto.getInitPassWord()));
            managers.setRealName(agentInfoDto.getAgentName());
            managers.setUserName(agentInfoDto.getUserName());
            managers.setUserSign(agentInfoDto.getUserSign());
            managers.setMobileCode(agentInfoDto.getLinkMobile());
            managers.setStatus(agentInfoDto.getAgentStatus());
            managersService.updateById(managers);
            Agent agentOne = agentService.getOne(new QueryWrapper<Agent>().lambda()
                    .eq(Agent::getManagersId, agentInfoDto.getAgentId()));
            BeanUtils.copyProperties(agentInfoDto, agent);
            agent.setManagersId(managers.getId());
            agent.setId(agentOne.getId());
            agentService.updateById(agent);
        }
//        commissionProportionDao.deleteObjectIdAndObjectTypeAndCustomerType(managers.getId(), 1, 0);
//        for (int i = 0; i < directCommissionProportion.size(); i++) {
//            CommissionProportion commissionProportion = new CommissionProportion();
//            BeanUtils.copyProperties(directCommissionProportion.get(i), commissionProportion);
//            commissionProportion.setObjectId(agent.getManagersId());
//            commissionProportionDao.insert(commissionProportion);
//        }
//        QueryWrapper<AgentTax> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda().eq(AgentTax::getAgentId, managers.getId());
//        List<AgentTax> agentTaxes = agentTaxDao.selectList(queryWrapper);
//        for (AgentTax agentTax : agentTaxes) {
//            if (1 == agentTax.getChargeStatus()) {
//                QueryWrapper<AgentLadderService> queryAgentLadderService = new QueryWrapper<>();
//                queryAgentLadderService.lambda().eq(AgentLadderService::getAgentTaxId, agentTax.getId());
//                List<AgentLadderService> agentLadderServices = agentLadderServiceDao.selectList(queryAgentLadderService);
//                for (AgentLadderService agentLadderService : agentLadderServices) {
//                    agentLadderServiceDao.deleteById(agentLadderService.getId());
//                }
//            }
//            agentTaxDao.deleteById(agentTax.getId());
//        }
//        List<AgentTaxDTO> agentTaxDtos = agentInfoDto.getAgentTaxDtos();
//        for (AgentTaxDTO agentTaxDTO : agentTaxDtos) {
//            AgentTax agentTax = new AgentTax();
//            BeanUtils.copyProperties(agentTaxDTO, agentTax);
//            agentTax.setAgentId(agent.getManagersId());
//            QueryWrapper<TaxPackage> queryWrapperTaxPackage = new QueryWrapper<>();
//            queryWrapperTaxPackage.lambda().eq(TaxPackage::getTaxId, agentTax.getTaxId())
//                    .eq(TaxPackage::getPackageStatus, agentTax.getPackageStatus());
//            TaxPackage taxPackage = taxPackageDao.selectOne(queryWrapperTaxPackage);
//            if (agentTaxDTO.getChargeStatus() == 1) {
//                List<AddAgentLadderServiceDTO> addCompanyLadderServiceDtoList = agentTaxDTO.getAddCompanyLadderServiceDtoList();
//                for (AddAgentLadderServiceDTO addAgentLadderServiceDTO : addCompanyLadderServiceDtoList) {
//                    AgentLadderService agentLadderService = new AgentLadderService();
//                    BeanUtils.copyProperties(addAgentLadderServiceDTO, agentLadderService);
//                    agentLadderService.setAgentTaxId(agentTax.getId());
//                    QueryWrapper<InvoiceLadderPrice> queryWrapperInvoiceLadderPrice = new QueryWrapper<>();
//
//
//                    queryWrapperInvoiceLadderPrice.lambda().eq(InvoiceLadderPrice::getTaxPackageId, taxPackage.getId())
//                            .eq(InvoiceLadderPrice::getStartMoney, agentLadderService.getStartMoney())
//                            .eq(InvoiceLadderPrice::getEndMoney, agentLadderService.getEndMoney())
//                            .eq(InvoiceLadderPrice::getPackaegStatus, taxPackage.getPackageStatus() == 0 ? 4 : 5);
//
//
//                    InvoiceLadderPrice invoiceLadderPrice = invoiceLadderPriceDao.selectOne(queryWrapperInvoiceLadderPrice);
//                    if (agentLadderService.getServiceCharge().compareTo(invoiceLadderPrice.getRate()) < 0) {
//                        throw new CommonException(300, invoiceLadderPrice.getStartMoney() + "-" + invoiceLadderPrice.getEndMoney() + "输入数据需不低于" + taxPackage.getTaxPrice());
//                    }
//
//                    agentLadderServiceDao.insert(agentLadderService);
//                }
//            } else {
//                if (agentTax.getServiceCharge().compareTo(taxPackage.getTaxPrice()) < 0) {
//                    throw new CommonException(300, "输入数据需不低于" + taxPackage.getTaxPrice());
//                }
//            }
//            agentTaxDao.insert(agentTax);
//        }
        return ReturnJson.success("成功!");
    }

    @Override
    public ReturnJson getAgentAll(Integer page, Integer pageSize) {
        Page<AgentListPO> agentListPOPage = new Page<>(page, pageSize);
        IPage<AgentListPO> agentListPOIPage = agentDao.selectAgentList(agentListPOPage);
        return ReturnJson.success(agentListPOIPage);
    }

    @Override
    public ReturnJson findByAgentId(String agentId) {
        Agent agent = agentDao.selectOne(new QueryWrapper<Agent>().lambda()
                .eq(Agent::getManagersId, agentId));
        if (null == agent) {
            return ReturnJson.error("没有此代理商");
        }
        AgentInfoVO agentInfoDto = new AgentInfoVO();
        BeanUtils.copyProperties(agent, agentInfoDto);
        List<CommissionProportionVO> directCommissionProportion = commissionProportionDao.getObjectIdAndObjectTypeAndCustomerType(agentId, 1, 0);
        Managers managers = managersDao.selectById(agentId);
        managers.setPassWord("");
        agentInfoDto.setAgentId(agentId);
        agentInfoDto.setUserName(managers.getUserName());
        agentInfoDto.setDirectCommissionProportion(directCommissionProportion);

//        QueryWrapper<AgentTax> queryWrapper = new QueryWrapper<>();
//        List<AgentTax> agentTaxes = agentTaxDao.selectList(queryWrapper.lambda().eq(AgentTax::getAgentId, agentId));
//        List<AgentTaxVO> agentTaxVOS = new ArrayList<>();
//        for (AgentTax agentTax : agentTaxes) {
//            AgentTaxVO agentTaxVO = new AgentTaxVO();
//            if (agentTax.getChargeStatus() == 0) {
//                BeanUtils.copyProperties(agentTax, agentTaxVO);
//                agentTaxVOS.add(agentTaxVO);
//            } else {
//                BeanUtils.copyProperties(agentTax, agentTaxVO);
//                QueryWrapper<AgentLadderService> queryWrapper1 = new QueryWrapper<>();
//                queryWrapper1.lambda().eq(AgentLadderService::getAgentTaxId, agentTax.getId());
//                List<AgentLadderService> agentLadderServices = agentLadderServiceDao.selectList(queryWrapper1);
//                agentTaxVO.setAgentLadderServices(agentLadderServices);
//                agentTaxVOS.add(agentTaxVO);
//            }
//        }
//        agentInfoDto.setAgentTaxVo(agentTaxVOS);

        return ReturnJson.success(agentInfoDto);
    }

    @Override
    public ReturnJson removeAgent(String agentId) {
        Integer count = companyInfoDao.selectCount(new QueryWrapper<CompanyInfo>().lambda()
                .eq(CompanyInfo::getAgentId, agentId));
        if (count > 0) {
            return ReturnJson.error("该代理商有业务记录，只能停用！");
        }
        agentDao.delete(new QueryWrapper<Agent>().lambda()
                .eq(Agent::getManagersId, agentId));
        managersDao.selectById(agentId);
        return ReturnJson.success("删除代理商成功！");
    }

    @Override
    public ReturnJson querySalesman(String userId) {
        Managers managers = managersDao.selectById(userId);
        //默认查询所有的业务员
        List<Managers> list = managersDao.selectList(new QueryWrapper<Managers>().lambda()
                .eq(Managers::getUserSign, 2));

        //代理商登录的时候更具代理商返回上级的业务员
        if (managers.getUserSign() == 1) {
            Agent agent = agentDao.selectOne(new QueryWrapper<Agent>().lambda()
                    .eq(Agent::getManagersId, managers.getId()));
            list = managersDao.selectList(new QueryWrapper<Managers>().lambda()
                    .eq(Managers::getId, agent.getSalesManId()));
        }

        //业务员登录的时候返回自己
        if (managers.getUserSign() == 2) {
            list = managersDao.selectList(new QueryWrapper<Managers>().lambda()
                    .eq(Managers::getId, managers.getId()));
        }
        List<QuerySalesmanVO> querySalesmanVOList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            QuerySalesmanVO querySalesmanVo = new QuerySalesmanVO();
            BeanUtils.copyProperties(list.get(i), querySalesmanVo);
            querySalesmanVOList.add(querySalesmanVo);
        }
        return ReturnJson.success(querySalesmanVOList);
    }

    @Override
    @Transactional
    public void timerStatistics() {
        //业务员
        List<TimerStatisticsVO> timerSalesManStatisticsVOS = managersDao.timerSalesManStatistics();
        for (TimerStatisticsVO timerStatisticsVO : timerSalesManStatisticsVOS) {
            AchievementStatistics achievementStatistics = new AchievementStatistics();
            achievementStatistics.setObjectId(timerStatisticsVO.getId());
            achievementStatistics.setObjectType(0);
            achievementStatistics.setMerchantCount(timerStatisticsVO.getMerchantCount());
            achievementStatistics.setMerchantBusiness(timerStatisticsVO.getManyMerchantBusiness().add(timerStatisticsVO.getTotalMerchantBusiness()));
            achievementStatistics.setAgentCount(timerStatisticsVO.getAgentCount());
            achievementStatistics.setAgentBusiness(timerStatisticsVO.getManyAgentBusiness().add(timerStatisticsVO.getTotalAgentBusiness()));
            List<CommissionProportion> merchantCommissionProportions = commissionProportionDao.selectList(new QueryWrapper<CommissionProportion>().lambda()
                    .eq(CommissionProportion::getObjectId, timerStatisticsVO.getId())
                    .eq(CommissionProportion::getObjectType, 0)
                    .eq(CommissionProportion::getCustomerType, 0));
            BigDecimal commissionProportions = this.getCommissionProportions(merchantCommissionProportions, achievementStatistics.getMerchantBusiness());

            List<CommissionProportion> AgentCommissionProportions = commissionProportionDao.selectList(new QueryWrapper<CommissionProportion>().lambda()
                    .eq(CommissionProportion::getObjectId, timerStatisticsVO.getId())
                    .eq(CommissionProportion::getObjectType, 0)
                    .eq(CommissionProportion::getCustomerType, 1));
            BigDecimal commissionProportions1 = this.getCommissionProportions(AgentCommissionProportions, achievementStatistics.getAgentBusiness());
            achievementStatistics.setMerchantRate(commissionProportions);
            achievementStatistics.setAgentRate(commissionProportions1);
            achievementStatistics.setMerchantCommission(commissionProportions.multiply(achievementStatistics.getMerchantBusiness()));
            achievementStatistics.setAgentCommission(commissionProportions1.multiply(achievementStatistics.getAgentBusiness()));
            BigDecimal add = timerStatisticsVO.getManyMerchantCommission().add(timerStatisticsVO.getTotalMerchantCommission());
            achievementStatistics.setMerchantDifference(add);
            BigDecimal add1 = timerStatisticsVO.getManyAgentCommission().add(timerStatisticsVO.getTotalAgentCommission());
            achievementStatistics.setAgentCommission(add1);
            achievementStatistics.setTotalCommission(commissionProportions.add(commissionProportions1).add(add).add(add1));
            achievementStatistics.setSettlementState(0);
            achievementStatisticsDao.insert(achievementStatistics);
        }
        //代理商
        List<TimerStatisticsVO> timerAgentStatisticsVOS = managersDao.timerAgentStatistics();

        for (TimerStatisticsVO timerStatisticsVO : timerAgentStatisticsVOS) {
            AchievementStatistics achievementStatistics = new AchievementStatistics();
            achievementStatistics.setObjectId(timerStatisticsVO.getId());
            achievementStatistics.setObjectType(1);
            achievementStatistics.setMerchantCount(timerStatisticsVO.getMerchantCount());
            achievementStatistics.setMerchantBusiness(timerStatisticsVO.getManyMerchantBusiness().add(timerStatisticsVO.getTotalMerchantBusiness()));
            achievementStatistics.setAgentCount(0);
            achievementStatistics.setAgentBusiness(BigDecimal.ZERO);
            List<CommissionProportion> merchantCommissionProportions = commissionProportionDao.selectList(new QueryWrapper<CommissionProportion>().lambda()
                    .eq(CommissionProportion::getObjectId, timerStatisticsVO.getId())
                    .eq(CommissionProportion::getObjectType, 1)
                    .eq(CommissionProportion::getCustomerType, 0));
            BigDecimal commissionProportions = this.getCommissionProportions(merchantCommissionProportions, achievementStatistics.getMerchantBusiness());
            achievementStatistics.setMerchantRate(commissionProportions);
            achievementStatistics.setMerchantCommission(commissionProportions.multiply(achievementStatistics.getMerchantBusiness()));
            achievementStatistics.setAgentCommission(BigDecimal.ZERO);
            BigDecimal add = timerStatisticsVO.getManyMerchantCommission().add(timerStatisticsVO.getTotalMerchantCommission());
            achievementStatistics.setMerchantDifference(add);
            achievementStatistics.setAgentCommission(BigDecimal.ZERO);
            achievementStatistics.setTotalCommission(commissionProportions.add(add));
            achievementStatistics.setSettlementState(0);
            achievementStatisticsDao.insert(achievementStatistics);
        }
    }

    @Override
    public ReturnJson salesmanAndAgentStatistics(String userId, Integer objectType, String time, Integer pageNo, Integer pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Managers managers = managersDao.selectById(userId);
        if (null == managers) {
            ReturnJson.error("没有此管理员");
        }
        if (managers.getUserSign() == 3) {
            userId = null;
        }
        if (objectType == 1) {
            if (managers.getUserSign() == 2) {
                QueryWrapper<Managers> queryWrapper = new QueryWrapper<>();
                List<Managers> managersList = managersDao.selectList(queryWrapper.lambda().eq(Managers::getParentId, managers.getId()));
                for (Managers manager : managersList) {
                    userId += manager.getId() + ",";
                }
                userId = userId.substring(0, userId.length() - 1);
            }
        }
        if (StringUtils.isBlank(time)) {
            Date date = new Date();
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(date);
            rightNow.add(Calendar.MONTH, 1);
            Date dt1 = rightNow.getTime();
            time = sdf.format(dt1);
        }
        Page<AchievementStatisticsVO> achievementStatisticsVOPage = new Page<>(pageNo, pageSize);
        IPage<AchievementStatisticsVO> achievementStatisticsVOIPage = achievementStatisticsDao.salesmanAndAgentStatistics(userId, objectType, time + "%", achievementStatisticsVOPage);
        return ReturnJson.success(achievementStatisticsVOIPage);
    }

    @Override
    public ReturnJson totalSalesmanAndAgentStatistics(String userId, Integer objectType) {
        Managers managers = managersDao.selectById(userId);
        if (null == managers) {
            ReturnJson.error("没有此管理员");
        }
        if (managers.getUserSign() == 3) {
            userId = null;
        }
        if (objectType == 1) {
            if (managers.getUserSign() == 2) {
                QueryWrapper<Managers> queryWrapper = new QueryWrapper<>();
                List<Managers> managersList = managersDao.selectList(queryWrapper.lambda().eq(Managers::getParentId, managers.getId()));
                for (Managers manager : managersList) {
                    userId += manager.getId() + ",";
                }
                userId = userId.substring(0, userId.length() - 1);
            }
        }
        TotalAchievementStatisticsVO totalAchievementStatisticsVO = achievementStatisticsDao.totalSalesmanAndAgentStatistics(userId, objectType);
        return ReturnJson.success(totalAchievementStatisticsVO);
    }

    @Override
    public ReturnJson salesmanSAndAgentStatisticsDetail(String managersId, String achievementStatisticsId) {
        Managers managers = managersDao.selectById(managersId);
        if (null == managers) {
            ReturnJson.error("没有此管理员");
        }
        if (managers.getUserSign() == 3) {
            ReturnJson.error("参数错误");
        }
        Integer objectType = null;

        Map<String, Object> map = new HashMap<>();
        TotalAchievementStatisticsVO totalAchievementStatisticsVO = achievementStatisticsDao.totalSalesmanAndAgentStatistics(managersId, objectType);
        AchievementStatistics achievementStatistics = achievementStatisticsDao.selectById(achievementStatisticsId);
        map.put("totalAchievementStatisticsVO", totalAchievementStatisticsVO);
        map.put("achievementStatistics", achievementStatistics);
        if (managers.getUserSign() == 2) {
            List<CommissionProportionVO> merchantCommissionProportion = commissionProportionDao.getObjectIdAndObjectTypeAndCustomerType(managersId, 0, 0);
            List<CommissionProportionVO> agentCommissionProportion = commissionProportionDao.getObjectIdAndObjectTypeAndCustomerType(managersId, 0, 1);
            map.put("merchantCommissionProportion", merchantCommissionProportion);
            map.put("agentCommissionProportion", agentCommissionProportion);
        }
        if (managers.getUserSign() == 1) {
            List<CommissionProportionVO> merchantCommissionProportion = commissionProportionDao.getObjectIdAndObjectTypeAndCustomerType(managersId, 1, 0);
            map.put("merchantCommissionProportion", merchantCommissionProportion);
        }
        return ReturnJson.success(map);
    }

    @Override
    public ReturnJson salesmanSAndAgentDetail(String managersId, Integer customerType, Integer pageNo, Integer pageSize) {
        Managers managers = managersDao.selectById(managersId);
        if (null == managers) {
            ReturnJson.error("没有此管理员");
        }
        if (managers.getUserSign() == 3) {
            ReturnJson.error("参数错误");
        }
        Page<SalesmanSAndAgentDetailVO> salesmanStatisticsVOPage = new Page<>(pageNo, pageSize);
        IPage<SalesmanSAndAgentDetailVO> salesmanStatisticsVOIPage = null;
        if (managers.getUserSign() == 2) {
            salesmanStatisticsVOIPage = achievementStatisticsDao.salesmanDetail(managersId, customerType, salesmanStatisticsVOPage);
        } else {
            salesmanStatisticsVOIPage = achievementStatisticsDao.agentDetail(managersId, salesmanStatisticsVOPage);
        }

        return ReturnJson.success(salesmanStatisticsVOIPage);
    }

    /**
     * 获取综合费率
     *
     * @param realMoney
     * @return
     */
    private BigDecimal getCommissionProportions(List<CommissionProportion> commissionProportions, BigDecimal realMoney) {
        BigDecimal compositeTax = BigDecimal.ZERO;
        for (CommissionProportion commissionProportion : commissionProportions) {
            BigDecimal startMoney = commissionProportion.getStartMoney();
            if (realMoney.compareTo(startMoney) >= 0) {
                compositeTax = commissionProportion.getCommissionRate().divide(new BigDecimal(100));
            }
        }
        return compositeTax;
    }
}
