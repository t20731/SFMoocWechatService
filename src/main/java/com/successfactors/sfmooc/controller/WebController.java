package com.successfactors.sfmooc.controller;

import com.successfactors.sfmooc.domain.Result;
import com.successfactors.sfmooc.service.WebService;
import com.successfactors.sfmooc.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web")
public class WebController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WebService webService;

    @RequestMapping(value = "/access_token", method = RequestMethod.GET)
    public Result getAccessToken() {
        String accessToken = webService.getAccessToken();
        if(accessToken != null){
            return new Result(0, Constants.SUCCESS, accessToken);
        }
        return new Result(-1, Constants.ERROR);
    }

}
