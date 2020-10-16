package com.example.merchant;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@EnableSwagger2
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(value = {"com.example.mybatis.mapper"})
@CrossOrigin(origins = "https://lgy-v3.lgyun.com.cn/", maxAge = 3600)
public class MerchantApplication {

	public static void main(String[] args) {
		log.info("服务正在启动");
		SpringApplication.run(MerchantApplication.class, args);
		log.info("服务启动完成");
	}

}
