package com.successfactors.sfmooc.controller;


import com.successfactors.sfmooc.domain.Result;
import com.successfactors.sfmooc.domain.Session;
import com.successfactors.sfmooc.service.LotteryService;
import com.successfactors.sfmooc.service.SessionService;
import com.successfactors.sfmooc.utils.Constants;
import com.successfactors.sfmooc.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/lottery")
public class LotteryController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private SessionService sessionService;

//    @RequestMapping(value = "/validate/{userId}", method = RequestMethod.GET)
//    public Result isValidUser(@PathVariable("userId") String userId){
//        if(StringUtils.isEmpty(userId)){
//            return new Result(-1, Constants.ILLEGAL_ARGUMENT, false);
//        }
//        Set<String> userList = sessionService.getAttendeeList();
//        if(userList != null && userList.contains(userId)){
//            return new Result(0, Constants.SUCCESS, true);
//        }
//        return new Result(-1, Constants.ERROR, false);
//    }

    @RequestMapping(value = "/bet", method = RequestMethod.POST)
    public Result bet(@RequestBody Map params) {
        String  userId = (String) params.get("userId");
        Integer sessionId = (Integer) params.get("sessionId");
        Integer number = (Integer) params.get("pickNumber");
        if (StringUtils.isEmpty(userId) || sessionId == null || number == null) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        Set<String> userList = sessionService.getAttendeeList(sessionId);
        if (userList.contains(userId)) {
            int status = lotteryService.bet(userId, sessionId, number);
            return new Result(status, Constants.SUCCESS);
        } else {
            return new Result(-1, "not_checkin");
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
