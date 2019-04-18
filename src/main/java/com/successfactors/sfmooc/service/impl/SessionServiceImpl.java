package com.successfactors.sfmooc.service.impl;

import com.successfactors.sfmooc.dao.SessionDAO;
import com.successfactors.sfmooc.domain.FetchParams;
import com.successfactors.sfmooc.domain.RankingSession;
import com.successfactors.sfmooc.domain.Session;
import com.successfactors.sfmooc.domain.UserSession;
import com.successfactors.sfmooc.service.CheckinService;
import com.successfactors.sfmooc.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SessionServiceImpl implements SessionService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CheckinService checkinService;

    @Autowired
    private SessionDAO sessionDAO;

    @Override
    public int register(String userId, Integer sessionId) {
        return sessionDAO.register(userId, sessionId);
    }

    @Override
    public int unRegister(String userId, Integer sessionId) {
        return sessionDAO.unRegister(userId, sessionId);
    }

    @Override
    public int editSession(Session session) {
        if(session.getId() == null || session.getId() == 0){
            String imageSrc = checkinService.generateTileImageSrc();
            logger.info("Generate image src is: " + imageSrc);
            session.setTileImageSrc(imageSrc);
        }
        return sessionDAO.editSession(session);
    }

    @Override
    public List<Session> getSessionList(FetchParams fetchParams) {
        return sessionDAO.getSessionList(fetchParams);
    }

    @Override
    public UserSession getSessionById(Integer sessionId, String userId) {
        return sessionDAO.getSessionById(sessionId, userId);
    }

    @Override
    public int getEnrollments(Integer sessionId) {
        return sessionDAO.getEnrollments(sessionId);
    }

    @Override
    public int delete(Integer sessionId) {
        return sessionDAO.delete(sessionId);
    }

    @Override
    public int cancel(Integer sessionId) {
        return sessionDAO.cancel(sessionId);
    }

    @Override
    public Map<String, Object> start(String userId, Integer sessionId) {
        Map<String, Object> retMap = new HashMap<>(2);
        String checkInCode = checkinService.generateCheckinCode();
        logger.info("CheckIn code: " + checkInCode);
        retMap.put("CheckInCode", checkInCode);
        int sharePoints = checkinService.confirmCheckinCode(sessionId, checkInCode, userId);
        retMap.put("SharePoints", sharePoints);
        return retMap;
    }

    @Override
    public String getCheckInCode(Integer sessionId) {
        return sessionDAO.getCheckInCode(sessionId);
    }

    @Override
    public int getSessionLikeCount(Integer sessionId ){
        return sessionDAO.getSessionLikeCount(sessionId);
    }
    @Override
    public int like(String userId, Integer sessionId, Integer like) {
        return sessionDAO.like(userId, sessionId, like);
    }
    @Override
    public int getLike(String userId, Integer sessionId) {
        return sessionDAO.getLike(userId, sessionId);
    }

    @Override
    public Set<String> getAttendeeList(Integer sessionId) {
        List<String> userList = sessionDAO.getAttendeeList(sessionId);
        if(userList != null){
            return new LinkedHashSet<>(userList);
        }
        return null;
    }

    @Override
    public List<String> getRegisteredUsers(Integer sessionId) {
        return sessionDAO.getRegisteredUsers(sessionId);
    }

    @Override
    public int updateLuckyNumber(Integer sessionId, Integer luckyNumber) {
        return sessionDAO.updateLuckyNumber(sessionId, luckyNumber);
    }

    @Override
    public List<RankingSession> getSessionRankingList(int group) {
        List<RankingSession> rankingSessions = new ArrayList<>();
        List<Session> sessions = sessionDAO.getSessionRankingList(group);
        int i =0;
         for (Session session : sessions){
             RankingSession rankingSession = new RankingSession();
             rankingSession.setRank(++i);
             rankingSession.setSession(session);
             rankingSessions.add(rankingSession);
         }
         return rankingSessions;
    }
}
