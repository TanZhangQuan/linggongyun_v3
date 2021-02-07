package com.example.merchant.config;

import com.example.common.config.FileStorageConfig;
import com.example.common.config.TemplateConfig;
import com.example.merchant.interceptor.PaasLoginJWTInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Bean
    public PaasLoginJWTInterceptor paasLoginJWTInterceptor() {
        return new PaasLoginJWTInterceptor();
    }

    /**
     * interceptor配置
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(paasLoginJWTInterceptor())
                //添加需要验证登录用户操作权限的请求
                .addPathPatterns("/**")
                //这里add为“/**”,下面的exclude才起作用，且不管controller层是否有匹配客户端请求，拦截器都起作用拦截
//                .addPathPatterns("/hello")
                //如果add为具体的匹配如“/hello”，下面的exclude不起作用,且controller层不匹配客户端请求时拦截器不起作用

                //排除不需要验证登录用户操作权限的请求
//                .excludePathPatterns("/wang")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/swagger-ui.html")
                .excludePathPatterns("/images/**");
        //这里可以用registry.addInterceptor添加多个拦截器实例，后面加上匹配模式
        super.addInterceptors(registry);//最后将register往这里塞进去就可以了
    }

    //添加此方法解决上述问题
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler(FileStorageConfig.getImageAccessPath() + "/**")
                .addResourceLocations("file:" + FileStorageConfig.getImagePath());

        registry.addResourceHandler(FileStorageConfig.getExcelAccessPath() + "/**")
                .addResourceLocations("file:" + FileStorageConfig.getExcelPath());

        registry.addResourceHandler(FileStorageConfig.getVideoAccessPath() + "/**")
                .addResourceLocations("file:" + FileStorageConfig.getVideoPath());

        registry.addResourceHandler(TemplateConfig.getAccessPath() + "/**")
                .addResourceLocations("file:" + TemplateConfig.getPath());

        super.addResourceHandlers(registry);

    }
}