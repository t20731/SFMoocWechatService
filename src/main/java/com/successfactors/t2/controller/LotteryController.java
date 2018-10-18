package com.successfactors.t2.controller;


import com.successfactors.t2.domain.Result;
import com.successfactors.t2.domain.Session;
import com.successfactors.t2.service.LotteryService;
import com.successfactors.t2.service.SessionService;
import com.successfactors.t2.utils.Constants;
import com.successfactors.t2.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Set;

@RestController
@RequestMapping("/lottery")
public class LotteryController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private SessionService sessionService;

    @RequestMapping(value = "/validate/{userId}", method = RequestMethod.GET)
    public Result isValidUser(@PathVariable("userId") String userId){
        if(StringUtils.isEmpty(userId)){
            return new Result(-1, Constants.ILLEGAL_ARGUMENT, false);
        }
        Set<String> userList = sessionService.getAttendeeList();
        if(userList != null && userList.contains(userId)){
            return new Result(0, Constants.SUCCESS, true);
        }
        return new Result(-1, Constants.ERROR, false);
    }

    @RequestMapping(value = "/bet/{userId}/{number}", method = RequestMethod.GET)
    public Result bet(@PathVariable("userId") String userId, @PathVariable("number") Integer number) {
        if (StringUtils.isEmpty(userId) || number == null) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        String today = DateUtil.formatDate(new Date());
        Session session = sessionService.getSessionByDate(today);
        if (session != null) {
            Set<String> userList = sessionService.getAttendeeList();
            if (userList.contains(userId)) {
                int status = lotteryService.bet(userId, session.getSessionId(), number);
                return new Result(status, Constants.SUCCESS);
            } else {
                return new Result(-1, "not_checkin");
            }
        } else {
            return new Result(-1, Constants.NOT_AUTHORIZED);
        }
    }

//    @RequestMapping(value = "/draw/{userId}", method = RequestMethod.GET)
//    public Result draw(@PathVariable("userId") String userId) {
//        if (StringUtils.isEmpty(userId)) {
//            return new Result(-1, "illegal_argument");
//        }
//        Session session = sessionService.getSessionByOwner(userId);
//        String today = DateUtil.formatDate(new Date());
//        if (session != null && today.equals(session.getSessionDate())) {
//            LotteryResult result = lotteryService.draw(userId, session.getSessionId());
//            if (result != null && result.getLuckyNumber() > 0) {
//                return new Result(0, Constants.SUCCESS, result);
//            } else {
//                return new Result(-1, "error");
//            }
//        }
//        return new Result(-1, "not_authorized");
//    }

}
