package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.RegulatorDto;
import com.example.mybatis.entity.Regulator;

/**
 * <p>
 * 监管部门 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-25
 */
public interface RegulatorService extends IService<Regulator> {

    ReturnJson addRegulator(RegulatorDto regulatorDto);

    ReturnJson updateRegulator(RegulatorDto regulatorDto);

    ReturnJson getByRegulatorId(Long regulatorId);
}
