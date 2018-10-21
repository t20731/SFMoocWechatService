package com.successfactors.sfmooc.controller;

import com.successfactors.sfmooc.domain.Result;
import com.successfactors.sfmooc.domain.User;
import com.successfactors.sfmooc.service.SessionService;
import com.successfactors.sfmooc.service.UserService;
import com.successfactors.sfmooc.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result addUser(@RequestBody User user) {
        if (user.getId() == null || user.getNickName() == null || user.getGender() == null) {
            String errorMsg = "user info is not correct";
            logger.info(errorMsg);
            return new Result(-1, errorMsg);
        }
        int status = userService.addUser(user);
        return new Result(status, Constants.SUCCESS);
    }

    @RequestMapping(value = "/login/{code}", method = RequestMethod.GET)
    public Result getOpenId(@PathVariable("code") String code) {
        if(StringUtils.isEmpty(code)){
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        String openId = userService.getOpenId(code);
        if(openId != null){
            return new Result(0, Constants.SUCCESS, openId);
        }
        return new Result(-1, Constants.ERROR);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result getUserById(@PathVariable("id") String id) {
        if(StringUtils.isEmpty(id)){
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        User user = userService.getUserById(id);
        if(user != null){
            return new Result(0, Constants.SUCCESS, user);
        }
        return new Result(-1, Constants.ERROR);
    }


}