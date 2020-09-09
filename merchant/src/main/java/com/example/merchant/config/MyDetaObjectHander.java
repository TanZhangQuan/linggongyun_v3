package com.example.merchant.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@Slf4j
@ComponentScan(value = {"com.example.redis.dao", "com.example.common.*"})
public class MyDetaObjectHander implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("come to insert fill .........");
        //setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject)
        this.setFieldValByName("createDate", LocalDateTime.now(),metaObject);
        this.setFieldValByName("updateDate", LocalDateTime.now(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("come to update fill .......");

        this.setFieldValByName("updateDate",new Date(),metaObject);

    }
}