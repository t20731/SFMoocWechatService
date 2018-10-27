package com.successfactors.sfmooc.controller;

import com.successfactors.sfmooc.domain.Result;
import com.successfactors.sfmooc.service.RankingService;
import com.successfactors.sfmooc.service.SessionService;
import com.successfactors.sfmooc.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

@RestController
@RequestMapping("/checkin")
public class CheckinController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionService sessionService;

    @Autowired
    private RankingService rankingService;


    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public Result checkin(@RequestBody Map params) throws UnsupportedEncodingException {
        if(params == null){
            return new Result(-1, Constants.ILLEGAL_ARGUMENT, null);
        }
        String userId = (String) params.get("userId");
        String code = (String) params.get("code");
        Integer sessionId = (Integer) params.get("sessionId");
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(code) || sessionId == null || sessionId == 0) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT, null);
        }
        code = URLDecoder.decode(code, "UTF-8");
        logger.info("Code after decode: " + code);
        String checkInCode = sessionService.getCheckInCode(sessionId);
        if (checkInCode != null && checkInCode.equalsIgnoreCase(code)) {
            int status = rankingService.updatePointsForCheckin(sessionId, userId);
            return new Result(status, Constants.SUCCESS, null);
        } else {
            return new Result(-1, "wrong_code", null);
        }
    }



}
