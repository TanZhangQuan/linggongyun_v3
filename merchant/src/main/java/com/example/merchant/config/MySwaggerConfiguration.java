package com.example.merchant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author teacher
 * @date 2019/9/25
 */
@Configuration
@EnableSwagger2
public class MySwaggerConfiguration {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //标题
                .title("零工云第三版")
                //简介
                .description("hello swagger")
                //服务条款
                .termsOfServiceUrl("1. xxx\n2. xxx\n3. xxx")
                //作者个人信息
                .contact(new Contact("admin", "http://www.baidu.com", ""))
                //版本
                .version("3.0")
                .build();
    }
}