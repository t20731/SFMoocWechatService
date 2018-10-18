package com.successfactors.t2.service.impl;

import com.successfactors.t2.dao.UserDAO;
import com.successfactors.t2.domain.LoginBean;
import com.successfactors.t2.domain.User;
import com.successfactors.t2.service.CacheService;
import com.successfactors.t2.service.UserService;
import com.successfactors.t2.utils.WxMappingJackson2HttpMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private Environment env;

    @Override
    public String getOpenId(String code) {
        String appId = env.getProperty("app.appId");
        String appSecrect = env.getProperty("app.appSecrect");
        String apiPrefix = "https://api.weixin.qq.com/sns/jscode2session?";
        String url = apiPrefix + "appid=" + appId + "&secret=" + appSecrect + "&js_code=" + code + "&grant_type=authorization_code";
        logger.info("wechat api url: " + url);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        LoginBean loginBean = restTemplate.getForObject(url, LoginBean.class);
        if(loginBean != null){
            return loginBean.getOpenid();
        }
        return null;
    }


    @Override
    public int addUser(User user){
        return userDAO.addUser(user);
    }

    @Override
    public User getUserById(String userId) {
        return userDAO.getUserById(userId);
    }
}
