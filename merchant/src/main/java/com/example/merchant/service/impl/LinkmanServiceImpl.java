package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.LinkmanService;
import com.example.mybatis.entity.Linkman;
import com.example.mybatis.entity.Merchant;
import com.example.mybatis.mapper.LinkmanDao;
import com.example.mybatis.mapper.MerchantDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private MerchantDao merchantDao;

    /**
     * 添加或修改联系人
     *
     * @param linkman
     * @return
     */
    @Override
    public ReturnJson addOrUpdataLinkman(Linkman linkman) {
        if (linkman.getIsNot() == 0) {
            Linkman linkmanOne = this.getOne(new QueryWrapper<Linkman>().eq("company_id", linkman.getCompanyId()).eq("is_not", 0));
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
        Merchant merchant = merchantDao.selectById(merchantId);
        List<Linkman> list = null;
        if (merchant == null) {
            list = this.list(new QueryWrapper<Linkman>().eq("company_id", merchantId).orderByAsc("is_not"));
        }
        if (merchant != null){
            list = this.list(new QueryWrapper<Linkman>().eq("company_id", merchant.getCompanyId()).orderByAsc("is_not"));
        }
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
