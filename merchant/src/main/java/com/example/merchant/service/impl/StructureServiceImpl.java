package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.util.MD5;
import com.example.common.util.ReturnJson;
import com.example.common.util.VerificationCheck;
import com.example.merchant.dto.platform.AgentInfoDTO;
import com.example.merchant.dto.platform.ManagersDTO;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.*;
import com.example.merchant.util.AcquireID;
import com.example.merchant.vo.platform.QuerySalesmanVO;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.AgentDao;
import com.example.mybatis.mapper.CompanyInfoDao;
import com.example.mybatis.mapper.ManagersDao;
import com.example.mybatis.mapper.MenuDao;
import com.example.mybatis.po.AgentListPO;
import com.example.mybatis.po.SalesManPaymentListPO;
import com.example.mybatis.vo.MenuListVO;
import com.example.mybatis.vo.MenuVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @Value("${PWD_KEY}")
    private String PWD_KEY;

    @Override
    public ReturnJson addSalesMan(ManagersDTO managersDto) {
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
            managers.setPassWord(MD5.md5(PWD_KEY + managersDto.getInitPassWord()));
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
            return ReturnJson.success("添加业务员成功！");
        } else {
            if (!managersOne.getId().equals(managers.getId())) {
                return ReturnJson.error("此手机号码已近注册过！");
            }
            BeanUtils.copyProperties(managersDto, managers);
            if (managers.getPassWord() != null) {
                managers.setPassWord(MD5.md5(PWD_KEY + managersDto.getInitPassWord()));
            }
            managersService.updateById(managers);
            return ReturnJson.success("编辑业务员成功！");
        }

    }

    /**
     * 按ID查找业务员
     *
     * @param managersId
     * @return
     */
    @Override
    public ReturnJson findBySalesManId(String managersId) {
        Managers managers = managersService.getById(managersId);
        managers.setPassWord("");
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
    public ReturnJson addAgent(AgentInfoDTO agentInfoDto) {
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
            managers.setPassWord(MD5.md5(PWD_KEY + agentInfoDto.getInitPassWord()));
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
            List<MenuListVO> listVos = menuDao.getMenuList();
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

            return ReturnJson.success("添加代理商成功!");
        } else {
            managers = managersDao.selectById(agentInfoDto.getAgentId());
            if (!managersOne.getId().equals(managers.getId())) {
                return ReturnJson.error("此手机号码已近注册过！");
            }
            //编辑
            managers.setPassWord(MD5.md5(PWD_KEY + agentInfoDto.getInitPassWord()));
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
            return ReturnJson.success("编辑代理商成功!");
        }


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
        AgentInfoDTO agentInfoDto = new AgentInfoDTO();
        BeanUtils.copyProperties(agent, agentInfoDto);

        Managers managers = managersDao.selectById(agentId);
        managers.setPassWord("");
        agentInfoDto.setAgentId(agentId);
        agentInfoDto.setUserName(managers.getUserName());
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
}
