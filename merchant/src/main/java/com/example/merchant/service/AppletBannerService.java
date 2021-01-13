package com.example.merchant.service;

import com.example.common.enums.Identification;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.AppletBanner;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 小程序轮播图 服务类
 * </p>
 *
 * @author xjw
 * @since 2021-01-12
 */
public interface AppletBannerService extends IService<AppletBanner> {

    /**
     * 查询所有的轮播图并返回
     *
     * @return
     */
    ReturnJson queryAppletBanner();

    /**
     * 查询所有的常见问题
     *
     * @return
     */
    ReturnJson queryAppletFaqList();

    /**
     * 查询常见问题
     *
     * @param id
     * @return
     */
    ReturnJson queryAppletFaqById(String id);

    /**
     * 标识
     *
     * @param identification
     * @return
     */
    ReturnJson queryAppletOtherInfo(Identification identification);
}
