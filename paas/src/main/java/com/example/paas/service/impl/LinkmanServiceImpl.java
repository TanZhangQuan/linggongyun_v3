package com.example.paas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ReturnJson;
import com.example.paas.service.LinkmanService;
import com.example.mybatis.entity.Linkman;
import com.example.mybatis.mapper.LinkmanDao;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 联系人表 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-14
 */
@Service
public class LinkmanServiceImpl extends ServiceImpl<LinkmanDao, Linkman> implements LinkmanService {

    /**
     * 添加或修改联系人
     * @param linkman
     * @return
     */
    @Override
    public ReturnJson addOrUpdataLinkman(Linkman linkman) {
        if (linkman.getIsNot() == 0) {
            Linkman linkmanOne = this.getOne(new QueryWrapper<Linkman>().eq("merchant_id", linkman.getMerchantId()).eq("is_not", 1));
            if (linkmanOne != null) {
                linkmanOne.setIsNot(1);
                this.saveOrUpdate(linkmanOne);
            }
        }
        boolean flag = this.saveOrUpdate(linkman);
        if (flag) {
            return ReturnJson.success("操作成功！");
        }
        return ReturnJson.error("操作失败！");
    }

    @Override
    public ReturnJson getLinkmanAll(String merchantId) {
        List<Linkman> list = this.list(new QueryWrapper<Linkman>().eq("merchant_id", merchantId).orderByAsc("is_not"));
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson updataStatus(String linkmanId, Integer status) {
        Linkman linkman = new Linkman();
        linkman.setStatus(status);
        linkman.setId(linkmanId);
        boolean flag = this.updateById(linkman);
        if (flag) {
            return ReturnJson.success("操作成功！");
        }
        return ReturnJson.error("操作失败！");
    }

    @Override
    public ReturnJson removeLinkmenById(String linkmanId) {
        boolean flag = this.removeById(linkmanId);
        if (flag) {
            return ReturnJson.success("删除成功！");
        }
        return ReturnJson.error("删除失败！");
    }
}
