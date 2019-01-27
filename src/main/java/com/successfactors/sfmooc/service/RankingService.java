package com.successfactors.sfmooc.service;

import com.successfactors.sfmooc.domain.LuckyDog;
import com.successfactors.sfmooc.domain.Points;
import com.successfactors.sfmooc.domain.RankingItem;

import java.util.List;

public interface RankingService {

    List<RankingItem> getRankingListBySeason(String season);
    List<RankingItem> getRankingListByGroupId(String season, int groupId);
    List<Points> getPointsDetailForUser(String userId);
    int updatePointsForHost(Integer sessionId, String userId);
    int updatePointsForCheckin(Integer sessionId, String userId);
    int updatePointsForLottery(Integer sessionId, List<LuckyDog> luckyDogs);
    int getTotalPoints(String userId);

}
