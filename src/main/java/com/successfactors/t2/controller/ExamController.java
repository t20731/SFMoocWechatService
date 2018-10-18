package com.successfactors.t2.controller;

import com.successfactors.t2.domain.*;
import com.successfactors.t2.service.ExamService;
import com.successfactors.t2.service.QuestionService;
import com.successfactors.t2.service.SessionService;
import com.successfactors.t2.utils.Constants;
import com.successfactors.t2.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ExamService examService;

    @RequestMapping(value = "/load/question/{sessionId}", method = RequestMethod.GET)
    public Result loadQuestionsBySession(@PathVariable("sessionId") Integer sessionId) {
        if (StringUtils.isEmpty(sessionId)) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        List<Question> questionList = questionService.loadQuestions(sessionId, 1);
        return new Result(0, Constants.SUCCESS, questionList);
    }

    @RequestMapping(value = "/load/session", method = RequestMethod.GET)
    public Result loadHistorySessions() {
        List<SessionVO> sessions = sessionService.loadHistorySessions();
        return new Result(0, Constants.SUCCESS, sessions);
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public Result submitAnswers(@RequestBody Answer answer) {
        if (answer == null || StringUtils.isEmpty(answer.getUserId())
                || CollectionUtils.isEmpty(answer.getAnswerMap())) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        String today = DateUtil.formatDate(new Date());
        Session session = sessionService.getSessionByDate(today);
        if (session != null) {
            if (!session.getOwner().equals(answer.getUserId())) {
                Set<String> attendList = sessionService.getAttendeeList();
                if (attendList != null && attendList.contains(answer.getUserId())) {
                    Answer result = examService.submitAnswers(session.getSessionId(), answer);
                    if (result != null) {
                        return new Result(0, Constants.SUCCESS, result);
                    } else {
                        return new Result(-1, "submitted");
                    }
                } else {
                    return new Result(-2, Constants.NOT_AUTHORIZED);
                }
            } else {
                return new Result(-1, Constants.NOT_AUTHORIZED);
            }
        }
        return new Result(-3, Constants.NOT_AUTHORIZED);
    }

}
