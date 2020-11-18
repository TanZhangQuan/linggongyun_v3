package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.util.MD5;
import com.example.common.util.ReturnJson;
import com.example.common.util.VerificationCheck;
import com.example.merchant.dto.platform.AgentInfoDto;
import com.example.merchant.dto.platform.ManagersDto;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    @Value("${PWD_KEY}")
    private String PWD_KEY;

    /**
     * 添加或修改
     *
     * @param managersDto
     * @return
     */
    @Override
    public ReturnJson addSalesMan(ManagersDto managersDto) {
        if (StringUtils.isBlank(managersDto.getConfirmPassWord()) || StringUtils.isBlank(managersDto.getInitPassWord())) {
            return ReturnJson.error("密码不能为空！");
        }
        if (!managersDto.getInitPassWord().equals(managersDto.getConfirmPassWord())) {
            return ReturnJson.error("输入的2次密码不一样！");
        }
        Managers managers = new Managers();
        BeanUtils.copyProperties(managersDto, managers);
        managers.setPassWord(PWD_KEY + MD5.md5(managersDto.getConfirmPassWord()));
        managersService.save(managers);
        return ReturnJson.success("添加业务员成功！");
    }

    /**
     * 编辑业务员
     *
     * @param managersDto
     * @return
     */
    @Override
    public ReturnJson updateSalesMan(ManagersDto managersDto) {
        Managers managers = new Managers();
        BeanUtils.copyProperties(managersDto, managers);
        if (!StringUtils.isBlank(managersDto.getConfirmPassWord())) {
            if (!managersDto.getConfirmPassWord().equals(managersDto.getInitPassWord())) {
                return ReturnJson.error("输入的2次密码不一样！");
            }
            managers.setPassWord(PWD_KEY + MD5.md5(managersDto.getConfirmPassWord()));
        }
        if (StringUtils.isBlank(managersDto.getInitPassWord())) {
            if (!managersDto.getInitPassWord().equals(managersDto.getConfirmPassWord())) {
                return ReturnJson.error("输入的2次密码不一样！");
            }
            managers.setPassWord(PWD_KEY + MD5.md5(managersDto.getInitPassWord()));
        }
        managersService.updateById(managers);
        return ReturnJson.success("编辑业务员成功！");
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
        IPage<Managers> managersIPage = managersService.page(managersPage, new QueryWrapper<Managers>().eq("user_sign", 2));
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
        List<String> companyIds = acquireID.getCompanyIds(salesManId);
        if (VerificationCheck.listIsNull(companyIds)) {
            return ReturnJson.error("该业务员还没有产生流水！");
        }
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
     * 添加代理商
     *
     * @param agentInfoDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson addAgent(AgentInfoDto agentInfoDto) {
        if (StringUtils.isBlank(agentInfoDto.getConfirmPassWord()) || StringUtils.isBlank(agentInfoDto.getInitPassWord())) {
            return ReturnJson.error("密码不能为空！");
        }
        if (!agentInfoDto.getInitPassWord().equals(agentInfoDto.getConfirmPassWord())) {
            return ReturnJson.error("输入的2次密码不一样");
        }

        Managers managers = new Managers();
        Agent agent = new Agent();
        managers.setPassWord(PWD_KEY + MD5.md5(agentInfoDto.getConfirmPassWord()));
        managers.setRealName(agentInfoDto.getAgentName());
        managers.setUserName(agentInfoDto.getUserName());
        managers.setUserSign(agentInfoDto.getUserSign());
        managers.setMobileCode(agentInfoDto.getLinkMobile());
        managers.setStatus(agentInfoDto.getAgentStatus());
        managersService.save(managers);

        BeanUtils.copyProperties(agentInfoDto, agent);
        agent.setManagersId(managers.getId());
        agentService.save(agent);
        return ReturnJson.success("添加代理商成功!");
    }


    /**
     * 编辑代理商
     *
     * @param agentInfoDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson updataAgent(AgentInfoDto agentInfoDto) {

        Managers managers = new Managers();
        Agent agent = new Agent();
        if (!StringUtils.isBlank(agentInfoDto.getConfirmPassWord())) {
            if (!agentInfoDto.getConfirmPassWord().equals(agentInfoDto.getInitPassWord())) {
                return ReturnJson.error("输入的2次密码不一样");
            }
            managers.setPassWord(PWD_KEY + MD5.md5(agentInfoDto.getConfirmPassWord()));
        }
        if (!StringUtils.isBlank(agentInfoDto.getInitPassWord())) {
            if (!agentInfoDto.getInitPassWord().equals(agentInfoDto.getConfirmPassWord())) {
                return ReturnJson.error("输入的2次密码不一样");
            }
            managers.setPassWord(PWD_KEY + MD5.md5(agentInfoDto.getInitPassWord()));
        }
        managers.setRealName(agentInfoDto.getAgentName());
        managers.setUserName(agentInfoDto.getUserName());
        managers.setUserSign(agentInfoDto.getUserSign());
        managers.setMobileCode(agentInfoDto.getLinkMobile());
        managers.setStatus(agentInfoDto.getAgentStatus());
        managersService.updateById(managers);

        Agent agentOne = agentService.getOne(new QueryWrapper<Agent>().eq("mangers_id", agentInfoDto.getAgentId()));

        BeanUtils.copyProperties(agentInfoDto, agent);
        agent.setManagersId(managers.getId());
        agent.setId(agentOne.getId());
        agentService.updateById(agent);
        return ReturnJson.success("编辑代理商成功!");
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
        managers.setPassWord("");
        agentInfoDto.setAgentId(agentId);
        agentInfoDto.setUserName(managers.getUserName());
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
