package com.successfactors.sfmooc.controller;

import com.successfactors.sfmooc.domain.LotteryResult;
import com.successfactors.sfmooc.domain.Result;
import com.successfactors.sfmooc.service.LotteryService;
import com.successfactors.sfmooc.service.SessionService;
import com.successfactors.sfmooc.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private SessionService sessionService;

    @MessageMapping("/draw")
    @SendTo("/topic/lottery")
    public Result draw(Integer sessionId) {
        logger.info("web socket draw: " + sessionId);
        if (sessionId == null) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        LotteryResult result = lotteryService.draw(sessionId);
        if (result != null && result.getLuckyNumber() > 0) {
            return new Result(0, Constants.SUCCESS, result);
        } else {
            return new Result(-1, Constants.ERROR);
        }
    }

}
