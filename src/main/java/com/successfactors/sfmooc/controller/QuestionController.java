package com.successfactors.sfmooc.controller;

import com.successfactors.sfmooc.domain.Question;
import com.successfactors.sfmooc.domain.Result;
import com.successfactors.sfmooc.domain.Session;
import com.successfactors.sfmooc.service.QuestionService;
import com.successfactors.sfmooc.service.SessionService;
import com.successfactors.sfmooc.utils.Constants;
import com.successfactors.sfmooc.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionService sessionService;

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Result editQuestion(@RequestBody Question question) {
        if (question == null || StringUtils.isEmpty(question.getOwner())) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        int status = questionService.editQuestion(question);
        if (status == -1) {
            return new Result(-1, "exceed_threshold");
        }
        return new Result(status, Constants.SUCCESS);
    }

    @RequestMapping(value = "/load/{sessionId}", method = RequestMethod.GET)
    public Result loadQuestions(@PathVariable("sessionId") Integer sessionId) {
        if (sessionId == null || sessionId <= 0) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        List<Question> questionList = questionService.loadQuestions(sessionId, 0);
        return new Result(0, Constants.SUCCESS, questionList);
    }

    @RequestMapping(value = "/delete/{questionId}", method = RequestMethod.DELETE)
    public Result deleteQuestion(@PathVariable("questionId") Integer questionId) {
        if (questionId == null || questionId <= 0) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        int status = questionService.deleteQuestion(questionId);
        if (status > 0) {
            return new Result(status, Constants.SUCCESS);
        }
        return new Result(0, "not_found");
    }

    @RequestMapping(value = "/publish/{sessionId}", method = RequestMethod.GET)
    public Result publish(@PathVariable("sessionId") Integer sessionId) {
        if (sessionId == null || sessionId <= 0) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        String checkInCode = sessionService.getCheckInCode(sessionId);
        if (checkInCode != null) {
            int status = questionService.publish(sessionId);
            if (status == -1) {
                return new Result(status, "published");
            } else {
                return new Result(status, Constants.SUCCESS);
            }
        }
        return new Result(-1, Constants.NOT_AUTHORIZED);
    }

}
