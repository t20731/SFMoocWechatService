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

//    @RequestMapping(value = "/edit", method = RequestMethod.POST)
//    public Result editQuestion(@RequestBody Question question) {
//        if (question == null || StringUtils.isEmpty(question.getOwner())) {
//            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
//        }
//        String userId = question.getOwner();
//        Session session = sessionService.getSessionByOwner(userId);
//        if (session != null) {
//            question.setSessionId(session.getSessionId());
//            int status = questionService.editQuestion(question);
//            if (status == -1) {
//                return new Result(-1, "exceed_threshold");
//            }
//            return new Result(status, Constants.SUCCESS);
//        } else {
//            return new Result(-1, Constants.NOT_AUTHORIZED);
//        }
//    }
//
//    @RequestMapping(value = "/load/{userId}", method = RequestMethod.GET)
//    public Result loadQuestions(@PathVariable("userId") String userId) {
//        if (StringUtils.isEmpty(userId)) {
//            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
//        }
//        Session session = sessionService.getSessionByOwner(userId);
//        if (session != null) {
//            List<Question> questionList = questionService.loadQuestions(session.getSessionId(), 0);
//            return new Result(0, Constants.SUCCESS, questionList);
//        } else {
//            return new Result(-1, Constants.NOT_AUTHORIZED);
//        }
//    }
//
//    @RequestMapping(value = "/delete/{userId}/{questionId}", method = RequestMethod.DELETE)
//    public Result deleteQuestion(@PathVariable("userId") String userId,
//                                 @PathVariable("questionId") Integer questionId) {
//        if (StringUtils.isEmpty(userId) || questionId <= 0) {
//            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
//        }
//        Session session = sessionService.getSessionByOwner(userId);
//        if (session != null) {
//            int status = questionService.deleteQuestion(session.getSessionId(), questionId);
//            if (status > 0) {
//                return new Result(status, Constants.SUCCESS);
//            }
//            return new Result(0, "not_found");
//        }
//        return new Result(-1, Constants.NOT_AUTHORIZED);
//    }
//
//    @RequestMapping(value = "/publish/{userId}", method = RequestMethod.GET)
//    public Result publish(@PathVariable("userId") String userId) {
//        if (StringUtils.isEmpty(userId)) {
//            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
//        }
//        Session session = sessionService.getSessionByOwner(userId);
//        if (session != null) {
//            String today = DateUtil.formatDate(new Date());
//            if (today.equals(session.getSessionDate())) {
//                int status = questionService.publish(session.getSessionId());
//                if (status == -1) {
//                    return new Result(status, "published");
//                } else {
//                    return new Result(status, Constants.SUCCESS);
//                }
//            } else {
//                return new Result(-1, "not_today");
//            }
//        }
//        return new Result(-1, Constants.NOT_AUTHORIZED);
//    }

}
