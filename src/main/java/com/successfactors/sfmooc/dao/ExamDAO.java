package com.successfactors.sfmooc.dao;

import com.successfactors.sfmooc.domain.RankingItem;

import java.util.List;
import java.util.Map;

public interface ExamDAO {
    Map<Integer, String> getAnswers(List<Integer> questionIds);
    List<RankingItem> getExamRankingList(Integer sessionId);
}
