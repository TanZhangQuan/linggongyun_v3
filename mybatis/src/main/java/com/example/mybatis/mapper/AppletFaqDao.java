package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.entity.AppletFaq;
import com.example.mybatis.vo.AppletFaqVO;

/**
 * <p>
 * 小程序常见问题 Mapper 接口
 * </p>
 *
 * @author xjw
 * @since 2021-01-12
 */
public interface AppletFaqDao extends BaseMapper<AppletFaq> {

    /**
     * 查看所有小程序常见问题
     *
     * @param page
     * @return
     */
    IPage<AppletFaqVO> selectAppletFaq(Page page);
}
