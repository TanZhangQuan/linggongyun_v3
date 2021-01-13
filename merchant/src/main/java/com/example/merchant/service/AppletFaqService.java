package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.SaveOrUpdateAppletBannerDTO;
import com.example.merchant.dto.platform.SaveOrUpdateAppletFaqDTO;
import com.example.merchant.dto.platform.SaveOrUpdateAppletOtherInfoDTO;
import com.example.mybatis.entity.AppletFaq;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 小程序常见问题 服务类
 * </p>
 *
 * @author xjw
 * @since 2021-01-12
 */
public interface AppletFaqService extends IService<AppletFaq> {

    /**
     * 添加小程序常用问题
     *
     * @param saveOrUpdateAppletFaqDto
     * @return
     */
    ReturnJson saveOrUpdateAppletFaq(SaveOrUpdateAppletFaqDTO saveOrUpdateAppletFaqDto);

    /**
     * 查看所有小程序常见问题
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    ReturnJson selectAppletFaq(Integer pageNo,Integer pageSize);

    /**
     * 查看某一个常见问题
     *
     * @param id
     * @return
     */
    ReturnJson queryAppletFaqInfo(String id);

    /**
     * 删除某一个常见问题
     *
     * @param id
     * @return
     */
    ReturnJson deleteAppletFaq(String id);

    /**
     * 添加小程序轮播图
     *
     * @param saveOrUpdateAppletBannerDto
     * @return
     */
    ReturnJson saveOrUpdateAppletBanner(SaveOrUpdateAppletBannerDTO saveOrUpdateAppletBannerDto);


    /**
     * 查看所有小程序轮播图
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    ReturnJson selectAppletBanner(Integer pageNo,Integer pageSize);

    /**
     * 查看某一个轮播图
     *
     * @param id
     * @return
     */
    ReturnJson queryAppletBannerInfo(String id);

    /**
     * 删除某一个轮播图
     *
     * @param id
     * @return
     */
    ReturnJson deleteAppletBanner(String id);

    /**
     * 添加小程序其他问题
     *
     * @param saveOrUpdateAppletOtherInfoDto
     * @return
     */
    ReturnJson saveOrUpdateAppletOtherInfo(SaveOrUpdateAppletOtherInfoDTO saveOrUpdateAppletOtherInfoDto);


    /**
     * 查看所有小程序其他问题
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    ReturnJson selectAppletOtherInfo(Integer pageNo,Integer pageSize);

    /**
     * 查看某一个其他问题
     *
     * @param id
     * @return
     */
    ReturnJson queryAppletOtherInfo(String id);

    /**
     * 删除某一个其他问题
     *
     * @param id
     * @return
     */
    ReturnJson deleteAppletOtherInfo(String id);


}
