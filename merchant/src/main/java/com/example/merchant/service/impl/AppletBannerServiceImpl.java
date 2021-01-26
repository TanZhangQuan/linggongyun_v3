package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.enums.Identification;
import com.example.common.util.ReturnJson;
import com.example.merchant.vo.platform.AppletFaqInfoVO;
import com.example.mybatis.entity.AppletBanner;
import com.example.mybatis.entity.AppletFaq;
import com.example.mybatis.entity.AppletOtherInfo;
import com.example.mybatis.mapper.AppletBannerDao;
import com.example.merchant.service.AppletBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatis.mapper.AppletFaqDao;
import com.example.mybatis.mapper.AppletOtherInfoDao;
import com.example.mybatis.vo.AppletFaqVO;
import com.example.mybatis.vo.AppletOtherVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 小程序轮播图 服务实现类
 * </p>
 *
 * @author xjw
 * @since 2021-01-12
 */
@Service
public class AppletBannerServiceImpl extends ServiceImpl<AppletBannerDao, AppletBanner> implements AppletBannerService {

    @Resource
    private AppletBannerDao appletBannerDao;

    @Resource
    private AppletFaqDao appletFaqDao;

    @Resource
    private AppletOtherInfoDao appletOtherInfoDao;

    @Override
    public ReturnJson queryAppletBanner() {
        List<String> banner = appletBannerDao.queryAppletBanner();
        return ReturnJson.success(banner);
    }

    @Override
    public ReturnJson queryAppletFaqList() {
        List<AppletFaqVO> appletFaqVOList = appletFaqDao.queryAppletFaq();
        return ReturnJson.success(appletFaqVOList);
    }

    @Override
    public ReturnJson queryAppletFaqById(String id) {
        AppletFaq appletFaq = appletFaqDao.selectById(id);
        if (appletFaq != null) {
            return ReturnJson.error("信息错误，请稍后再试！");
        }
        AppletFaqInfoVO appletFaqInfoVO = new AppletFaqInfoVO();
        BeanUtils.copyProperties(appletFaq, appletFaqInfoVO);
        return ReturnJson.success(appletFaqInfoVO);
    }

    @Override
    public ReturnJson queryAppletOtherInfo(Identification identification) {
        AppletOtherInfo appletOtherInfo = appletOtherInfoDao.selectOne(new QueryWrapper<AppletOtherInfo>().lambda()
                .eq(AppletOtherInfo::getEntryName, identification.getDesc()));
        if (appletOtherInfo != null) {
            String content=appletOtherInfo.getContent();
            return ReturnJson.success(content);
        }
        return ReturnJson.error("信息错误，请稍后再试！");
    }


}
