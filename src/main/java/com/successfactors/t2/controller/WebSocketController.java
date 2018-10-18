package com.successfactors.t2.controller;

import com.successfactors.t2.domain.LotteryResult;
import com.successfactors.t2.domain.Result;
import com.successfactors.t2.domain.Session;
import com.successfactors.t2.service.LotteryService;
import com.successfactors.t2.service.SessionService;
import com.successfactors.t2.utils.Constants;
import com.successfactors.t2.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.util.Date;

@Controller
public class WebSocketController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private SessionService sessionService;

    @MessageMapping("/draw")
    @SendTo("/topic/lottery")
    public Result draw(String userId) {
        logger.info("web socket draw: " + userId);
        if (StringUtils.isEmpty(userId)) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        Session session = sessionService.getSessionByOwner(userId);
        String today = DateUtil.formatDate(new Date());
        if (session != null && today.equals(session.getSessionDate())) {
            LotteryResult result = lotteryService.draw(userId, session.getSessionId());
            if (result != null && result.getLuckyNumber() > 0) {
                return new Result(0, Constants.SUCCESS, result);
            } else {
                return new Result(-1, Constants.ERROR);
            }
        }
        return new Result(-1, Constants.NOT_AUTHORIZED);
    }

}
