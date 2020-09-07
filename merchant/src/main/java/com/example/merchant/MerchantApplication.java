package com.example.merchant;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@MapperScan(value = "com.example.mybatis.mapper")
public class MerchantApplication {

	public static void main(String[] args) {
		log.info("商户服务启动");
		SpringApplication.run(MerchantApplication.class, args);
		log.info("商户服务结束");
	}

}
