package com.successfactors.sfmooc.service.impl;

import com.successfactors.sfmooc.service.WebService;
import com.successfactors.sfmooc.utils.WxMappingJackson2HttpMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class WebServiceImpl implements WebService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Environment env;

    @Override
    public String getAccessToken() {
        String appId = env.getProperty("app.appId");
        String appSecrect = env.getProperty("app.appSecrect");
        String apiPrefix = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
        String url = apiPrefix + "&appid=" + appId + "&secret=" + appSecrect;
        logger.info("get access token url: " + url);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        Map<String, Object> res = restTemplate.getForObject(url, Map.class);
        if(res != null){
            logger.info("Res: " + res);
            return (String)res.get("access_token");
        }
        return null;
    }
}
