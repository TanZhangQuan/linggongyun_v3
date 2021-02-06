package com.example.merchant.controller.quratz;


import org.quartz.CronTrigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author jun.
 * @date 2020/11/16.
 * @time 15:24.
 */
@Configuration
public class QuratzConfig {

    //Job  编辑任务
    @Bean
    MethodInvokingJobDetailFactoryBean job() {
        MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
        bean.setTargetBeanName("quratzJob");//类的bean名称
        bean.setTargetMethod("calculationSalesman");//不能使用重写的方法
        return bean;
    }


    //Trigger  创建定时器
    @Bean
    CronTriggerFactoryBean cronTriggerFactoryBean() {
        CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
        bean.setJobDetail(job().getObject());//可以对应不同的Job，一个job可以被多个trigger关联，但是一个trigger只能关联一个job
        bean.setCronExpression("0 52 23 L * ?");//corn表达式,每天凌晨1点执行一次
        //bean.setCronExpression("*/5 * * * * ?");//corn表达式,每5秒执行一次
        return bean;
    }


    //Scheduler  加载定时器
    @Bean
    SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        CronTrigger cronTrigger = cronTriggerFactoryBean().getObject();
        bean.setTriggers(cronTrigger);
        return bean;
    }
}
