package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.AddressDTO;
import com.example.mybatis.entity.Address;
import com.example.mybatis.entity.Merchant;
import com.example.mybatis.mapper.AddressDao;
import com.example.merchant.service.AddressService;
import com.example.mybatis.mapper.MerchantDao;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 地址表 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressDao, Address> implements AddressService {

    @Resource
    private MerchantDao merchantDao;

    @Override
    public ReturnJson getAddressAll(String merchantId) {
        Merchant merchant = merchantDao.selectById(merchantId);
        List<Address> addressList = null;
        if (merchant == null) {
            addressList = this.list(new QueryWrapper<Address>().eq("company_id", merchantId).orderByAsc("is_not"));
        }
        if (merchant != null){
            addressList = this.list(new QueryWrapper<Address>().eq("company_id", merchant.getCompanyId()).orderByAsc("is_not"));
        }
        return ReturnJson.success(addressList);
    }

    @Override
    public ReturnJson addOrUpdataAddress(AddressDTO addressDto, String merchantId) {
        Merchant merchant = merchantDao.selectById(merchantId);
        if (merchant == null) {
            merchant = new Merchant();
            merchant.setCompanyId(merchantId);
        }
        Address address = new Address();
        BeanUtils.copyProperties(addressDto, address);
        address.setCompanyId(merchant.getCompanyId());
        if (address.getIsNot() == 0) {
            Address addressOne = this.getOne(new QueryWrapper<Address>().eq("company_id", address.getCompanyId()).eq("is_not", 0));
            if (addressOne != null) {
                addressOne.setIsNot(1);
                this.saveOrUpdate(addressOne);
            }
        }
        boolean flag = this.saveOrUpdate(address);
        if (flag) {
            return ReturnJson.success("操作成功！");
        }
        return ReturnJson.error("操作失败！");
    }

    @Override
    public ReturnJson updataAddressStatus(String addressId, Integer status) {
        Address address = new Address();
        address.setId(addressId);
        address.setStatus(status);
        boolean flag = this.updateById(address);
        if (flag) {
            return ReturnJson.success("操作成功！");
        }
        return ReturnJson.error("操作失败！");
    }

    @Override
    public ReturnJson removeAddressById(String addressId) {
        boolean flag = this.removeById(addressId);
        if (flag) {
            return ReturnJson.success("删除地址成功！");
        }
        return ReturnJson.success("删除地址失败！");
    }

}
