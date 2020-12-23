package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.AddressDTO;
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

    /**
     * 获取商户的所以收货地址
     *
     * @param merchantId
     * @return
     */
    ReturnJson getAddressAll(String merchantId);

    /**
     * 添加或修改地址
     *
     * @param addressDto
     * @param merchantId
     * @return
     */
    ReturnJson addOrUpdataAddress(AddressDTO addressDto, String merchantId);

    /**
     * 停用或启用地址
     *
     * @param addressId
     * @param status
     * @return
     */
    ReturnJson updataAddressStatus(String addressId, Integer status);

    /**
     * 删除地址
     *
     * @param addressId
     * @return
     */
    ReturnJson removeAddressById(String addressId);
}
