package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.util.MD5;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.AgentInfoDto;
import com.example.merchant.dto.ManagersDto;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.*;
import com.example.merchant.util.AcquireID;
import com.example.mybatis.entity.Agent;
import com.example.mybatis.entity.CompanyInfo;
import com.example.mybatis.entity.Managers;
import com.example.mybatis.mapper.AgentDao;
import com.example.mybatis.mapper.CompanyInfoDao;
import com.example.mybatis.mapper.ManagersDao;
import com.example.mybatis.po.AgentListPO;
import com.example.mybatis.po.SalesManPaymentListPO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StructureServiceImpl implements StructureService {

    @Autowired
    private ManagersService managersService;

    @Autowired
    private CompanyInfoDao companyInfoDao;

    @Autowired
    private AgentDao agentDao;

    @Autowired
    private HomePageService homePageService;

    @Autowired
    private ManagersDao managersDao;

    @Autowired
    private AcquireID acquireID;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private AgentService agentService;

    @Value("${PWD_KEY}")
    private String PWD_KEY;

    /**
     * 添加或修改
     *
     * @param managersDto
     * @return
     */
    @Override
    public ReturnJson addOrUpdataSalesMan(ManagersDto managersDto) {
        if (!managersDto.getInitPassWord().equals(managersDto.getConfirmPassWord())) {
            return ReturnJson.error("输入的2次密码不一样！");
        }
        Managers managers = new Managers();
        if (managers.getId() != null) {
            managers = managersDao.selectById(managers.getId());
            BeanUtils.copyProperties(managersDto, managers);
            if (!managers.getPassWord().equals(managersDto.getConfirmPassWord())) {
                managers.setPassWord(PWD_KEY + MD5.md5(managersDto.getConfirmPassWord()));
            }
        } else {
            BeanUtils.copyProperties(managersDto, managers);
            managers.setPassWord(PWD_KEY + MD5.md5(managersDto.getConfirmPassWord()));
        }
        managersService.saveOrUpdate(managers);
        return ReturnJson.success("添加业务员成功！");
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
        IPage<Managers> managersIPage = managersService.page(managersPage, new QueryWrapper<Managers>().eq("user_sign", 2).eq("status", 0));
        return ReturnJson.success(managersIPage);
    }

    /**
     * 删除业务员
     *
     * @param salesManId
     * @return
     */
    @Override
    public ReturnJson removeSalesMan(String salesManId) throws CommonException {
        Integer companyInfoCount = companyInfoDao.selectCount(new QueryWrapper<CompanyInfo>().eq("sales_man_id", salesManId));
        Integer agentCount = agentDao.selectCount(new QueryWrapper<Agent>().eq("sales_man_id", salesManId));
        if (companyInfoCount + agentCount == 0) {
            managersService.removeById(salesManId);
            return ReturnJson.success("删除成功！");
        }
        homePageService.getHomePageInofpaas(salesManId);
        return ReturnJson.error("该业务员有业务记录，只能停用！");
    }


    /**
     * 业务员的流水统计
     *
     * @param salesManId
     * @return
     */
    @Override
    public ReturnJson getSalesManPaymentListCount(String salesManId) throws CommonException {
        List<String> companyIds = acquireID.getMerchantIds(salesManId);
        IPage<SalesManPaymentListPO> salesManPaymentListPOIPage = managersDao.selectSalesManPaymentList(new Page(1, 10), companyIds);
        ReturnJson returnJson = ReturnJson.success(salesManPaymentListPOIPage);
        ReturnJson homePageInof = homePageService.getHomePageInofpaas(salesManId);
        homePageInof.setData(returnJson.getData());
        return homePageInof;
    }

    /**
     * 业务员的流水
     *
     * @param salesManId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public ReturnJson getSalesManPaymentList(String salesManId, Integer page, Integer pageSize) throws CommonException {
        List<String> companyIds = acquireID.getMerchantIds(salesManId);
        Page<SalesManPaymentListPO> salesManPaymentListPOPage = new Page<>(page, pageSize);
        IPage<SalesManPaymentListPO> salesManPaymentListPOIPage = managersDao.selectSalesManPaymentList(salesManPaymentListPOPage, companyIds);
        return ReturnJson.success(salesManPaymentListPOIPage);
    }

    /**
     * 查询支付订单详情
     *
     * @param paymentOrderId
     * @param packageStatus
     * @return
     */
    @Override
    public ReturnJson getSalesManPaymentInfo(String paymentOrderId, Integer packageStatus) {
        return merchantService.getMerchantPaymentInfo(paymentOrderId, packageStatus);
    }

    /**
     * 获取支付清单列表
     *
     * @param paymentOrderId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public ReturnJson getPaymentInventory(String paymentOrderId, Integer page, Integer pageSize) {
        return merchantService.getMerchantPaymentInventory(paymentOrderId, page, pageSize);
    }

    /**
     * 添加或编辑代理商
     *
     * @param agentInfoDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson addOrUpdataAgent(AgentInfoDto agentInfoDto) {
        if (!agentInfoDto.getInitPassWord().equals(agentInfoDto.getConfirmPassWord())) {
            return ReturnJson.error("输入的2次密码不一样");
        }

        Managers managers = new Managers();
        Agent agent = new Agent();

        if (agentInfoDto.getAgentId() != null) {
            agent = agentDao.selectOne(new QueryWrapper<Agent>().eq("managers_id", agentInfoDto.getAgentId()));
            managers = managersDao.selectById(agentInfoDto.getAgentId());
            if (!agentInfoDto.getConfirmPassWord().equals(managers.getPaasName())) {
                managers.setPassWord(PWD_KEY + MD5.md5(agentInfoDto.getConfirmPassWord()));
            }
        } else {
            managers.setPassWord(PWD_KEY + MD5.md5(agentInfoDto.getConfirmPassWord()));
        }
        managers.setRealName(agentInfoDto.getAgentName());
        managers.setUserName(agentInfoDto.getUserName());
        managers.setUserSign(agentInfoDto.getUserSign());
        managers.setMobileCode(agentInfoDto.getLinkMobile());
        managers.setStatus(agentInfoDto.getAgentStatus());
        managersService.saveOrUpdate(managers);

        BeanUtils.copyProperties(agentInfoDto, agent);
        agent.setManagersId(managers.getId());
        agentService.saveOrUpdate(agent);
        return ReturnJson.success("添加代理商成功!");
    }

    /**
     * 查询所以代理商
     *
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public ReturnJson getAgentAll(Integer page, Integer pageSize) {
        Page<AgentListPO> agentListPOPage = new Page<>(page, pageSize);
        IPage<AgentListPO> agentListPOIPage = agentDao.selectAgentList(agentListPOPage);
        return ReturnJson.success(agentListPOIPage);
    }

    /**
     * 按ID查找代理商(编辑代理商时用来获取代理商信息)
     *
     * @param agentId
     * @return
     */
    @Override
    public ReturnJson findByAgentId(String agentId) {
        Agent agent = agentDao.selectOne(new QueryWrapper<Agent>().eq("managers_id", agentId));
        AgentInfoDto agentInfoDto = new AgentInfoDto();
        BeanUtils.copyProperties(agent, agentInfoDto);

        Managers managers = managersDao.selectById(agentId);
        agentInfoDto.setAgentId(agentId);
        agentInfoDto.setUserName(managers.getUserName());
        agentInfoDto.setConfirmPassWord(managers.getPassWord());
        agentInfoDto.setInitPassWord(managers.getPassWord());
        return ReturnJson.success(agentInfoDto);
    }

    /**
     * 删除没有业务的代理商
     *
     * @param agentId
     * @return
     */
    @Override
    public ReturnJson removeAgent(String agentId) {
        Integer count = companyInfoDao.selectCount(new QueryWrapper<CompanyInfo>().eq("agent_id", agentId));
        if (count > 0) {
            return ReturnJson.error("该代理商有业务记录，只能停用！");
        }
        agentDao.delete(new QueryWrapper<Agent>().eq("managers_id", agentId));
        managersDao.selectById(agentId);
        return ReturnJson.success("删除代理商成功！");
    }
}
