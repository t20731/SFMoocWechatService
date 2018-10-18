package com.successfactors.t2.controller;

import com.successfactors.t2.domain.Result;
import com.successfactors.t2.domain.Session;
import com.successfactors.t2.service.CheckinService;
import com.successfactors.t2.service.RankingService;
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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Set;

@RestController
@RequestMapping("/checkin")
public class CheckinController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CheckinService checkinService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private RankingService rankingService;

    private static String checkinCode;

    @RequestMapping(value = "/code/{userId}", method = RequestMethod.GET)
    public Result generateCheckinCode(@PathVariable("userId") String userId) {
        if (!StringUtils.isEmpty(userId)) {
            if (sessionService.isSessionOwnerOfToday(userId)) {
                String code = checkinService.generateCheckinCode();
                if (code != null) {
                    return new Result(1, Constants.SUCCESS, code);
                } else {
                    return new Result(0, "no_code", null);
                }
            } else {
                return new Result(-1, Constants.NOT_AUTHORIZED, null);
            }
        }
        return new Result(-1, Constants.ILLEGAL_ARGUMENT, null);
    }

    @RequestMapping(value = "/confirm/{userId}/{code}", method = RequestMethod.GET)
    public Result confirmCheckinCode(@PathVariable("code") String code, @PathVariable("userId") String userId)
    throws UnsupportedEncodingException{
        if (!StringUtils.isEmpty(code) && !StringUtils.isEmpty(userId)) {
            code = URLDecoder.decode(code, "UTF-8");
            logger.info("Code after decode: "+code);
            Set<String> userList = sessionService.getAttendeeList();
            if(userList != null && userList.contains(userId)){
                return new Result(0, "checked_in", true);
            }
            Session session = sessionService.getSessionByOwner(userId);
            String today = DateUtil.formatDate(new Date());
            if (session != null && today.equals(session.getSessionDate())) {
                int status = checkinService.confirmCheckinCode(session.getSessionId(), code, userId);
                if (status > 0) {
                    return new Result(status, Constants.SUCCESS, code);
                } else {
                    return new Result(0, "no_update", null);
                }
            } else {
                return new Result(-1, Constants.NOT_AUTHORIZED, null);
            }
        }
        return new Result(-1, Constants.ILLEGAL_ARGUMENT, null);
    }


    @RequestMapping(value = "/{userId}/{code}", method = RequestMethod.GET)
    public Result checkin(@PathVariable("userId") String userId, @PathVariable("code") String code)
            throws UnsupportedEncodingException{
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(code)) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT, null);
        }
        code = URLDecoder.decode(code, "UTF-8");
        logger.info("Code after decode: "+code);
        Set<String> userList = sessionService.getAttendeeList();
        if(userList != null && userList.contains(userId)){
            return new Result(0, "checked_in", true);
        }
        Session session = sessionService.getSessionByDate(DateUtil.formatDate(new Date()));
        if (session != null && !userId.equals(session.getOwner())) {
            if (code.equalsIgnoreCase(session.getCheckinCode())) {
                int status = rankingService.updatePointsForCheckin(session.getSessionId(), userId);
                return new Result(status, Constants.SUCCESS, null);
            } else {
                return new Result(-1, "wrong_code", null);
            }
        }
        return new Result(-1, Constants.NOT_AUTHORIZED, null);
    }



}
