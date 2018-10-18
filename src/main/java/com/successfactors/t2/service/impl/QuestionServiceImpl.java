package com.successfactors.t2.service.impl;

import com.successfactors.t2.dao.QuestionDAO;
import com.successfactors.t2.domain.Question;
import com.successfactors.t2.service.QuestionService;
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
    public int deleteQuestion(Integer sessionId, Integer questionId) {
        return questionDAO.deleteQuestion(sessionId, questionId);
    }

    @Override
    public int publish(Integer sessionId) {
        return questionDAO.publish(sessionId);
    }
}
