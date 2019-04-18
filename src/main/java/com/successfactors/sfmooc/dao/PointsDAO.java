package com.successfactors.sfmooc.dao;

import com.successfactors.sfmooc.domain.LuckyDog;
import com.successfactors.sfmooc.domain.Points;
import com.successfactors.sfmooc.domain.RankingItem;

import java.util.List;

public interface PointsDAO {
    List<RankingItem> getUserRankingList(String season);
    List<RankingItem> getUserRankingListByGroupId(String season, int groupId);
    List<Points> getPointsDetailForUser(String userId);
    Points getPointsById(Integer sessionId, String userId);
    int updatePointsForHost(Integer sessionId, String userId);
    int updatePointsForCheckin(Integer sessionId, String userId);
    int updatePointsForLottery(Integer sessionId, List<LuckyDog> luckyDogs);
    int updatePointsForExam(Integer sessionId, String userId, int points);
    int getTotalPoints(String userId);
}
