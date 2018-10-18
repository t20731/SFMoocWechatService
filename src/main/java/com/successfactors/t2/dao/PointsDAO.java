package com.successfactors.t2.dao;

import com.successfactors.t2.domain.Points;
import com.successfactors.t2.domain.RankingItem;

import java.util.List;

public interface PointsDAO {
    List<RankingItem> getUserRankingList(String season);
    List<Points> getPointsDetailForUser(String userId);
    Points getPointsById(Integer sessionId, String userId);
    int updatePointsForHost(Integer sessionId, String userId);
    int updatePointsForCheckin(Integer sessionId, String userId);
    int updatePointsForLottery(Integer sessionId, Integer luckyNumber);
    int updatePointsForExam(Integer sessionId, String userId, int points);
}
