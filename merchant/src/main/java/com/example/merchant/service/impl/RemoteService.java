package com.example.merchant.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.example.merchant.config.MyBankConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


/**
 * <p>注释</p>
 *
 * @author fjl
 * @version $Id: PersonalService.java, v 0.1 2013-12-20 下午2:03:11 fjl Exp $
 */
@Service
public class RemoteService {
    private static Logger logger = LoggerFactory.getLogger(RemoteService.class);

    @Autowired
    private MyBankConfig myBankConfig;

    @Autowired
    private RestTemplate restTemplate;

    public Map<String, Object> invoke(Map<String, String> data, String url) {
        logger.debug("网商请求发起,参数: {}, URL: {}", data, url);
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String value = entry.getValue();
            paramMap.add(entry.getKey(), value);
        }
        String resp = restTemplate.postForObject(url, paramMap, String.class);
        Map<String, Object> m = (Map) JSONUtils.parse(resp);
        return m;
    }
}
