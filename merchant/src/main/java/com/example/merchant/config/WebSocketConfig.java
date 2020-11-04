package com.example.merchant.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 开启websocket的支持
 * @author niebo
 */
@Configuration
public class WebSocketConfig {
    private static Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        logger.info("WebSocketConfig 注入成功！");
        return new ServerEndpointExporter();
    }
}
