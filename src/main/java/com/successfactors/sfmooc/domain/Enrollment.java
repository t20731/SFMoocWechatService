package com.successfactors.sfmooc.domain;

public class Enrollment {
    private String userId;
    private Integer sessionId;

    public Enrollment() {
    }

    public Enrollment(String userId, Integer sessionId) {
        this.userId = userId;
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }
}
