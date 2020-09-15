package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.Address;
import com.example.mybatis.mapper.AddressDao;
import com.example.merchant.service.AddressService;
import org.springframework.stereotype.Service;

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

    @Override
    public ReturnJson getAddressAll(String merchantId) {
        List<Address> addressList = this.list(new QueryWrapper<Address>().eq("merchant_id", merchantId).orderByAsc("is_not"));
        return ReturnJson.success(addressList);
    }

    @Override
    public ReturnJson addOrUpdataAddress(Address address) {
        if (address.getIsNot() == 0) {
            Address addressOne = this.getOne(new QueryWrapper<Address>().eq("merchant_id", address.getMerchantId()).eq("is_not", 0));
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
