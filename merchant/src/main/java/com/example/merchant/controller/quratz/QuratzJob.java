package com.example.merchant.controller.quratz;

import com.example.merchant.service.StructureService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;


/**
 * @author .
 * @date 2020/11/16.
 * @time 16:41.
 */
@Slf4j
@Component(value = "quratzJob")
@AllArgsConstructor
public class QuratzJob {

    @Resource
    private StructureService structureService;


    /**
     * 每个月底处理上个产生的佣金
     */
    public void calculationSalesman() throws Exception {
        log.info("Y----------------start" + new Date() + "---------------");
        structureService.timerStatistics();
        log.info("Y----------------end" + new Date() + "---------------");
    }
}
