package com.successfactors.sfmooc.service;

import com.successfactors.sfmooc.domain.Answer;
import com.successfactors.sfmooc.domain.RankingItem;

import java.util.List;

public interface ExamService {
    Answer submitAnswers(Integer sessionId, Answer answer);
    List<RankingItem> getExamRankingList(Integer sessionId);
}
