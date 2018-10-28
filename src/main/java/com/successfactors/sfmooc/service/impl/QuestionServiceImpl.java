package com.successfactors.sfmooc.service.impl;

import com.successfactors.sfmooc.dao.QuestionDAO;
import com.successfactors.sfmooc.domain.Question;
import com.successfactors.sfmooc.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuestionDAO questionDAO;

    @Override
    public int editQuestion(Question question) {
        return questionDAO.editQuestion(question);
    }

    @Override
    public List<Question> loadQuestions(Integer sessionId, int status) {
        return questionDAO.loadQuestions(sessionId, status);
    }

    @Override
    public int deleteQuestion(Integer questionId) {
        return questionDAO.deleteQuestion(questionId);
    }

    @Override
    public int publish(Integer sessionId) {
        return questionDAO.publish(sessionId);
    }
}
