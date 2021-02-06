package com.example.merchant.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class RemoteService {

    @Autowired
    private RestTemplate restTemplate;

    public Map<String, Object> invoke(Map<String, String> data, String url) {
        log.info("网商请求发起,参数: {}, URL: {}", data, url);
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
