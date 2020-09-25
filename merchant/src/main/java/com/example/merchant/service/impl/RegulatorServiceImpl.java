package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.MD5;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.RegulatorDto;
import com.example.merchant.service.RegulatorService;
import com.example.mybatis.entity.Regulator;
import com.example.mybatis.mapper.RegulatorDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 监管部门 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-25
 */
@Service
public class RegulatorServiceImpl extends ServiceImpl<RegulatorDao, Regulator> implements RegulatorService {

    /**
     * 添加监管部门
     * @param regulatorDto
     * @return
     */
    @Value("${PWD_KEY}")
    private String PWD_KEY;

    @Override
    public ReturnJson addRegulator(RegulatorDto regulatorDto) {
        if (!StringUtils.isBlank(regulatorDto.getConfirmPassWord()) || !StringUtils.isBlank(regulatorDto.getPassWord())) {
            return ReturnJson.error("密码不能为空！");
        }
        if (!regulatorDto.getPassWord().equals(regulatorDto.getConfirmPassWord())){
            return ReturnJson.error("输入的2次密码不一样，请重新输入！");
        }
        Regulator regulator = new Regulator();
        BeanUtils.copyProperties(regulatorDto,regulator);
        regulator.setPassWord(PWD_KEY+ MD5.md5(regulatorDto.getPassWord()));
        this.save(regulator);
        return ReturnJson.success("添加监管部门成功！");
    }

    /**
     * 编辑监管部门
     * @param regulatorDto
     * @return
     */
    @Override
    public ReturnJson updateRegulator(RegulatorDto regulatorDto) {
        Regulator regulator = new Regulator();
        BeanUtils.copyProperties(regulatorDto,regulator);
        if (!StringUtils.isBlank(regulatorDto.getPassWord())){
            if (!regulatorDto.getPassWord().equals(regulatorDto.getConfirmPassWord())){
                return ReturnJson.error("输入的2次密码不一样，请重新输入！");
            }
            regulator.setPassWord(PWD_KEY+ MD5.md5(regulatorDto.getPassWord()));
        }
        if (!StringUtils.isBlank(regulatorDto.getConfirmPassWord())){
            if (!regulatorDto.getConfirmPassWord().equals(regulatorDto.getPassWord())){
                return ReturnJson.error("输入的2次密码不一样，请重新输入！");
            }
            regulator.setPassWord(PWD_KEY+ MD5.md5(regulatorDto.getConfirmPassWord()));
        }
        this.updateById(regulator);
        return ReturnJson.success("编辑监管部门成功！");
    }

    /**
     * 按ID查询监管部门
     * @param regulatorId
     * @return
     */
    @Override
    public ReturnJson getByRegulatorId(Long regulatorId) {
        Regulator regulator = this.getById(regulatorId);
        regulator.setPassWord("");
        return ReturnJson.success(regulator);
    }
}
