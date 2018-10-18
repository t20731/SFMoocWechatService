package com.successfactors.t2.service;

import com.successfactors.t2.domain.Points;
import com.successfactors.t2.domain.RankingItem;

import java.util.List;

public interface RankingService {

    List<RankingItem> getRankingListBySeason(String season);
    List<Points> getPointsDetailForUser(String userId);
    int updatePointsForHost(Integer sessionId, String userId);
    int updatePointsForCheckin(Integer sessionId, String userId);
    int updatePointsForLottery(Integer sessionId, Integer luckyNumber);

}
