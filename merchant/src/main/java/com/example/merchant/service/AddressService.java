package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.Address;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 地址表 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface AddressService extends IService<Address> {
    ReturnJson getAddressAll(String merchantId);
    ReturnJson addOrUpdataAddress(Address address);
    ReturnJson updataAddressStatus(String addressId, Integer status);
    ReturnJson removeAddressById(String addressId);
}
