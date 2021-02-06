package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.entity.CommissionProportion;
import com.example.mybatis.vo.CommissionProportionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jun.
 * @date 2021/1/28.
 * @time 9:26.
 */
public interface CommissionProportionDao  extends BaseMapper<CommissionProportion> {

    /**
     * 根据对象id,对象类型和客户类型查询
     */
    List<CommissionProportionVO> getObjectIdAndObjectTypeAndCustomerType(@Param("objectId") String objectId,@Param("objectType") Integer objectType,@Param("customerType") Integer customerType);


    /**
     * 跟对象id，对象类型和客户类型查询
     */
    void deleteObjectIdAndObjectTypeAndCustomerType(@Param("objectId") String objectId,@Param("objectType") Integer objectType,@Param("customerType") Integer customerType);
}
