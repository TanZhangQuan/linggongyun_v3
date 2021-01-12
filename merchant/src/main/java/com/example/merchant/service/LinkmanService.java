package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.Linkman;

/**
 * <p>
 * 联系人表 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-14
 */
public interface LinkmanService extends IService<Linkman> {
    ReturnJson addOrUpdataLinkman(Linkman linkman, String merchantId);

    ReturnJson getLinkmanAll(String merchantId);

    ReturnJson updataStatus(String linkmanId, Integer status);

    ReturnJson removeLinkmenById(String linkmanId);
}
