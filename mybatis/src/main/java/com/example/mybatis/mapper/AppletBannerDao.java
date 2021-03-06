package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.entity.AppletBanner;
import com.example.mybatis.vo.AppletBannerVO;

import java.util.List;

/**
 * <p>
 * 小程序轮播图 Mapper 接口
 * </p>
 *
 * @author xjw
 * @since 2021-01-12
 */
public interface AppletBannerDao extends BaseMapper<AppletBanner> {

    /**
     * 查看所有小程序轮播图
     *
     * @param page
     * @return
     */
    IPage<AppletBannerVO> selectAppletBanner(Page page);

    /**
     * 查询所有轮播图
     *
     * @return
     */
    List<String> queryAppletBanner();
}
