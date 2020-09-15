package com.example.paas;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@EnableSwagger2
@SpringBootApplication
@MapperScan(value = {"com.example.mybatis.mapper"})
public class PaasApplication {

	public static void main(String[] args) {
		log.info("平台管理中心正在启动。。。。。。。。。");
		SpringApplication.run(PaasApplication.class, args);
		log.info("平台管理中心启动完成。。。。。。。。");
	}

}
