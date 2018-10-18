package com.successfactors.t2.service;

public interface CheckinService {
    String generateCheckinCode();
    int confirmCheckinCode(Integer sessionId, String code, String userId);
}
