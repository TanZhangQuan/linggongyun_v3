package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.entity.AppletOtherInfo;
import com.example.mybatis.vo.AppletOtherVO;

/**
 * <p>
 * 小程序其他问题 Mapper 接口
 * </p>
 *
 * @author xjw
 * @since 2021-01-12
 */
public interface AppletOtherInfoDao extends BaseMapper<AppletOtherInfo> {

    /**
     * 查看所有小程序轮播图
     *
     * @param page
     * @return
     */
    IPage<AppletOtherVO> selectAppletOtherInfo(Page page);
}
