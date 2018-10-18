package com.successfactors.t2.service;

import com.successfactors.t2.domain.Session;
import com.successfactors.t2.domain.SessionVO;

import java.util.List;
import java.util.Set;

public interface SessionService {
    List<Session> getSessionList();
    Session getSessionByDate(String date);
    Session getSessionByOwner(String owner);
    int updateCheckinCode(Integer sessionId, String checkinCode);
    boolean isSessionOwnerOfToday(String userId);
    Set<String> getAttendeeList();
    int updateLuckyNumber(Integer sessionId, Integer luckyNumber);
    List<SessionVO> loadHistorySessions();
    String getCurrentSeason();
}
