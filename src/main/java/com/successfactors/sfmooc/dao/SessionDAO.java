package com.successfactors.sfmooc.dao;

import com.successfactors.sfmooc.domain.*;

import java.util.List;

public interface SessionDAO {
    int editSession(Session session);
    List<Session> getSessionList(FetchParams fetchParams);
    UserSession getSessionById(Integer sessionId, String userId);
    int register(String userId, Integer sessionId);
    int unRegister(String userId, Integer sessionId);
    int getEnrollments(Integer sessionId);
    int delete(Integer sessionId);
    int cancel(Integer sessionId);
    int start(Integer sessionId);
    String getCheckInCode(Integer sessionId);
    int updateCheckinCode(Integer sessionId, String checkinCode);
    List<String> getAttendeeList(Integer sessionId);
    int updateLuckyNumber(Integer sessionId, Integer luckyNumber);
    int getSessionLikeCount(Integer sessionId);
    int like(String userId, Integer sessionId, Integer like);
    int getLike(String userId, Integer sessionId);
    List<String> getRegisteredUsers(Integer sessionId);
    List<Session> getSessionRankingList(int group);
    int getSharePoints(Integer sessionId);
}
