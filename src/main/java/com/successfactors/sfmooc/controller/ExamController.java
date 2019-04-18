package com.successfactors.sfmooc.controller;

import com.successfactors.sfmooc.domain.*;
import com.successfactors.sfmooc.service.ExamService;
import com.successfactors.sfmooc.service.QuestionService;
import com.successfactors.sfmooc.service.SessionService;
import com.successfactors.sfmooc.utils.Constants;
import com.successfactors.sfmooc.utils.DateUtil;
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
        if (sessionId == null || sessionId <= 0) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        List<Question> questionList = questionService.loadQuestions(sessionId, 1);
        return new Result(0, Constants.SUCCESS, questionList);
    }


    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public Result submitAnswers(@RequestBody Answer answer) {
        if (answer == null || StringUtils.isEmpty(answer.getUserId())
                || CollectionUtils.isEmpty(answer.getAnswerMap())) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        Integer sessionId = answer.getSessionId();
        Set<String> attendList = sessionService.getAttendeeList(sessionId);
        if (attendList != null && attendList.contains(answer.getUserId())) {
            Answer result = examService.submitAnswers(sessionId, answer);
            if (result != null) {
                return new Result(0, Constants.SUCCESS, result);
            } else {
                return new Result(-1, "submitted");
            }
        } else {
            return new Result(-2, Constants.NOT_AUTHORIZED);
        }
    }

    @RequestMapping(value = "/ranking/list/{sessionId}", method = RequestMethod.GET)
    public Result getExamRankingList(@PathVariable("sessionId") Integer sessionId) {
        if (sessionId == null || sessionId <= 0) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        List<RankingItem> rankingList = examService.getExamRankingList(sessionId);
        return new Result(0, Constants.SUCCESS, rankingList);
    }

}
