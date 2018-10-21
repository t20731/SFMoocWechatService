package com.successfactors.sfmooc.dao;

import com.successfactors.sfmooc.domain.FetchParams;
import com.successfactors.sfmooc.domain.Session;
import com.successfactors.sfmooc.domain.SessionVO;

import java.util.List;

public interface SessionDAO {

    int editSession(Session session);
    List<Session> getSessionList(FetchParams fetchParams);

//    List<Session> getSessionList(String season);
//
//    Session getSessionByDate(String date);
//
//    Session getSessionByOwner(String userId);
//
//    int updateCheckinCode(Integer sessionId, String checkinCode);
//
//    List<String> getAttendeeList();
//
//    int updateLuckyNumber(Integer sessionId, Integer luckyNumber);
//
//    List<SessionVO> loadHistorySessions();
//
//    String getCurrentSeason();
}
