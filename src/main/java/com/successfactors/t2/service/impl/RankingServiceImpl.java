package com.successfactors.t2.service.impl;

import com.successfactors.t2.dao.PointsDAO;
import com.successfactors.t2.domain.Points;
import com.successfactors.t2.domain.RankingItem;
import com.successfactors.t2.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingServiceImpl implements RankingService{
    @Autowired
    private PointsDAO pointsDAO;

    @Override
    public List<RankingItem> getRankingListBySeason(String season){
         return pointsDAO.getUserRankingList(season);
    }

    @Override
    public List<Points> getPointsDetailForUser(String userId) {
        return pointsDAO.getPointsDetailForUser(userId);
    }

    @Override
    public int updatePointsForHost(Integer sessionId, String userId) {
        return pointsDAO.updatePointsForHost(sessionId, userId);
    }

    @Override
    public int updatePointsForCheckin(Integer sessionId, String userId) {
        return pointsDAO.updatePointsForCheckin(sessionId, userId);
    }

    @Override
    public int updatePointsForLottery(Integer sessionId, Integer luckyNumber) {
        return pointsDAO.updatePointsForLottery(sessionId, luckyNumber);
    }
}
