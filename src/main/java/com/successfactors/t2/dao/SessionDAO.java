package com.successfactors.t2.dao;

import com.successfactors.t2.domain.Session;
import com.successfactors.t2.domain.SessionVO;

import java.util.List;

public interface SessionDAO {
    List<Session> getSessionList(String season);

    Session getSessionByDate(String date);

    Session getSessionByOwner(String userId);

    int updateCheckinCode(Integer sessionId, String checkinCode);

    List<String> getAttendeeList();

    int updateLuckyNumber(Integer sessionId, Integer luckyNumber);

    List<SessionVO> loadHistorySessions();

    String getCurrentSeason();
}
