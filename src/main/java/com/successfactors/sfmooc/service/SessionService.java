package com.successfactors.sfmooc.service;

import com.successfactors.sfmooc.domain.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SessionService {
    int editSession(Session session);
    List<Session> getSessionList(FetchParams fetchParams);
    UserSession getSessionById(Integer sessionId, String userId);
    int register(String userId, Integer sessionId);
    int unRegister(String userId, Integer sessionId);
    int getEnrollments(Integer sessionId);
    int delete(Integer sessionId);
    int cancel(Integer sessionId);
    int like(String userId, Integer sessionId, Integer like);
    int getLike(String userId, Integer sessionId);
    int getSessionLikeCount(Integer sessionId );
    Map<String, Object> start(String userId, Integer sessionId);
    String getCheckInCode(Integer sessionId);
    Set<String> getAttendeeList(Integer sessionId);
    int updateLuckyNumber(Integer sessionId, Integer luckyNumber);
    List<String> getRegisteredUsers(Integer sessionId);
    List<RankingSession> getSessionRankingList(int group);
}
