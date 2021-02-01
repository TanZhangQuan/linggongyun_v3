package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.SaveOrUpdateAppletBannerDTO;
import com.example.merchant.dto.platform.SaveOrUpdateAppletFaqDTO;
import com.example.merchant.dto.platform.SaveOrUpdateAppletOtherInfoDTO;
import com.example.merchant.vo.platform.AppletBannerInfoVO;
import com.example.merchant.vo.platform.AppletFaqInfoVO;
import com.example.merchant.vo.platform.AppletOtherInfoVO;
import com.example.mybatis.entity.AppletBanner;
import com.example.mybatis.entity.AppletFaq;
import com.example.mybatis.entity.AppletOtherInfo;
import com.example.mybatis.mapper.AppletBannerDao;
import com.example.mybatis.mapper.AppletFaqDao;
import com.example.merchant.service.AppletFaqService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatis.mapper.AppletOtherInfoDao;
import com.example.mybatis.vo.AppletBannerVO;
import com.example.mybatis.vo.AppletFaqVO;
import com.example.mybatis.vo.AppletOtherVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 小程序常见问题 服务实现类
 * </p>
 *
 * @author xjw
 * @since 2021-01-12
 */
@Service
public class AppletFaqServiceImpl extends ServiceImpl<AppletFaqDao, AppletFaq> implements AppletFaqService {

    @Resource
    private AppletFaqDao appletFaqDao;

    @Resource
    private AppletBannerDao appletBannerDao;

    @Resource
    private AppletOtherInfoDao appletOtherInfoDao;

    @Override
    public ReturnJson saveOrUpdateAppletFaq(SaveOrUpdateAppletFaqDTO saveOrUpdateAppletFaqDto) {
        AppletFaq appletFaq = appletFaqDao.selectById(saveOrUpdateAppletFaqDto.getId());
        if (appletFaq == null) {
            appletFaq = new AppletFaq();
            BeanUtils.copyProperties(saveOrUpdateAppletFaqDto, appletFaq);
            appletFaqDao.insert(appletFaq);
            return ReturnJson.success("添加成功！");
        } else {
            BeanUtils.copyProperties(saveOrUpdateAppletFaqDto, appletFaq);
            appletFaqDao.updateById(appletFaq);
            return ReturnJson.success("修改成功！");
        }
    }

    @Override
    public ReturnJson selectAppletFaq(Integer pageNo, Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        IPage<AppletFaqVO> iPage = appletFaqDao.selectAppletFaq(page);
        return ReturnJson.success(iPage);
    }

    @Override
    public ReturnJson queryAppletFaqInfo(String id) {
        AppletFaq appletFaq = appletFaqDao.selectById(id);
        if (appletFaq != null) {
            AppletFaqInfoVO appletFaqVO = new AppletFaqInfoVO();
            BeanUtils.copyProperties(appletFaq, appletFaqVO);
            return ReturnJson.success(appletFaqVO);
        }
        return ReturnJson.error("不存在此信息！");
    }

    @Override
    public ReturnJson deleteAppletFaq(String id) {
        AppletFaq appletFaq = appletFaqDao.selectById(id);
        if (appletFaq != null) {
            appletFaqDao.deleteById(id);
            return ReturnJson.success("删除成功！");
        }
        return ReturnJson.error("不存在此信息！");
    }

    @Override
    public ReturnJson saveOrUpdateAppletBanner(SaveOrUpdateAppletBannerDTO saveOrUpdateAppletBannerDto) {
        AppletBanner appletBanner = appletBannerDao.selectById(saveOrUpdateAppletBannerDto.getId());
        if (appletBanner == null) {
            appletBanner = new AppletBanner();
            BeanUtils.copyProperties(saveOrUpdateAppletBannerDto, appletBanner);
            appletBannerDao.insert(appletBanner);
            return ReturnJson.success("添加成功！");
        } else {
            BeanUtils.copyProperties(saveOrUpdateAppletBannerDto, appletBanner);
            appletBannerDao.updateById(appletBanner);
            return ReturnJson.success("修改成功！");
        }
    }

    @Override
    public ReturnJson selectAppletBanner(Integer pageNo, Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        IPage<AppletBannerVO> iPage = appletBannerDao.selectAppletBanner(page);
        return ReturnJson.success(iPage);
    }

    @Override
    public ReturnJson queryAppletBannerInfo(String id) {
        AppletBanner appletBanner = appletBannerDao.selectById(id);
        if (appletBanner != null) {
            AppletBannerInfoVO appletBannerInfoVO = new AppletBannerInfoVO();
            BeanUtils.copyProperties(appletBanner, appletBannerInfoVO);
            return ReturnJson.success(appletBannerInfoVO);
        }
        return ReturnJson.error("不存在此信息！");
    }

    @Override
    public ReturnJson deleteAppletBanner(String id) {
        AppletBanner appletBanner = appletBannerDao.selectById(id);
        if (appletBanner != null) {
            appletBannerDao.deleteById(id);
            return ReturnJson.success("删除成功！");
        }
        return ReturnJson.error("不存在此信息！");
    }

    @Override
    public ReturnJson saveOrUpdateAppletOtherInfo(SaveOrUpdateAppletOtherInfoDTO saveOrUpdateAppletOtherInfoDto) {
        AppletOtherInfo appletOtherInfo = appletOtherInfoDao.selectById(saveOrUpdateAppletOtherInfoDto.getId());
        if (appletOtherInfo == null) {
            appletOtherInfo = new AppletOtherInfo();
            BeanUtils.copyProperties(saveOrUpdateAppletOtherInfoDto, appletOtherInfo);
            appletOtherInfoDao.insert(appletOtherInfo);
            return ReturnJson.success("添加成功！");
        } else {
            BeanUtils.copyProperties(saveOrUpdateAppletOtherInfoDto, appletOtherInfo);
            appletOtherInfoDao.updateById(appletOtherInfo);
            return ReturnJson.success("修改成功！");
        }
    }

    @Override
    public ReturnJson selectAppletOtherInfo(Integer pageNo, Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        IPage<AppletOtherVO> iPage = appletOtherInfoDao.selectAppletOtherInfo(page);
        return ReturnJson.success(iPage);
    }

    @Override
    public ReturnJson queryAppletOtherInfo(String id) {
        AppletOtherInfo appletOtherInfo = appletOtherInfoDao.selectById(id);
        if (appletOtherInfo != null) {
            AppletOtherInfoVO appletOtherInfoVO = new AppletOtherInfoVO();
            BeanUtils.copyProperties(appletOtherInfo, appletOtherInfoVO);
            return ReturnJson.success(appletOtherInfoVO);
        }
        return ReturnJson.error("不存在此信息！");
    }

    @Override
    public ReturnJson deleteAppletOtherInfo(String id) {
        AppletOtherInfo appletOtherInfo = appletOtherInfoDao.selectById(id);
        if (appletOtherInfo != null) {
            appletOtherInfoDao.deleteById(id);
            return ReturnJson.success("删除成功！");
        }
        return ReturnJson.error("不存在此信息！");
    }


}
