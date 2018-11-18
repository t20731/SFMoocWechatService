package com.successfactors.sfmooc.service;

import com.successfactors.sfmooc.domain.FetchParams;
import com.successfactors.sfmooc.domain.Session;
import com.successfactors.sfmooc.domain.SessionVO;
import com.successfactors.sfmooc.domain.UserSession;

import java.util.List;
import java.util.Set;

public interface SessionService {
    int editSession(Session session);
    List<Session> getSessionList(FetchParams fetchParams);
    UserSession getSessionById(Integer sessionId, String userId);
    int register(String userId, Integer sessionId);
    int like(String userId, Integer sessionId, int like);
    int getEnrollments(Integer sessionId);
    int batchDelete(List<Integer> sessionIdList);
    int cancel(Integer sessionId);
    String start(String userId, Integer sessionId);
    String getCheckInCode(Integer sessionId);
//    Session getSessionByDate(String date);
//    Session getSessionByOwner(String owner);
//    int updateCheckinCode(Integer sessionId, String checkinCode);
//    boolean isSessionOwnerOfToday(String userId);
    Set<String> getAttendeeList(Integer sessionId);
//    int updateLuckyNumber(Integer sessionId, Integer luckyNumber);
//    List<SessionVO> loadHistorySessions();
//    String getCurrentSeason();
}
