package com.successfactors.sfmooc.dao;

import com.successfactors.sfmooc.domain.FetchParams;
import com.successfactors.sfmooc.domain.Session;
import com.successfactors.sfmooc.domain.SessionVO;
import com.successfactors.sfmooc.domain.UserSession;

import java.util.List;

public interface SessionDAO {

    int editSession(Session session);
    List<Session> getSessionList(FetchParams fetchParams);
    UserSession getSessionById(Integer sessionId, String userId);
    int register(String userId, Integer sessionId);
    int getEnrollments(Integer sessionId);
    int batchDelete(List<Integer> sessionIdList);
    int cancel(Integer sessionId);
    int start(Integer sessionId);
    String getCheckInCode(Integer sessionId);
    int updateCheckinCode(Integer sessionId, String checkinCode);
    List<String> getAttendeeList(Integer sessionId);
}
