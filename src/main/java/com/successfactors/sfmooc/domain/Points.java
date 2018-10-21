package com.successfactors.sfmooc.domain;

public class Points {
    private String userId;
    private Integer sessionId;
    private Integer betNumber;
    private int checkin;
    private int host;
    private Integer exam;
    private int lottery;
    private String date;

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

    public Integer getBetNumber() {
        return betNumber;
    }

    public void setBetNumber(Integer betNumber) {
        this.betNumber = betNumber;
    }

    public int getCheckin() {
        return checkin;
    }

    public void setCheckin(int checkin) {
        this.checkin = checkin;
    }

    public int getHost() {
        return host;
    }

    public void setHost(int host) {
        this.host = host;
    }

    public Integer getExam() {
        return exam;
    }

    public void setExam(Integer exam) {
        this.exam = exam;
    }

    public int getLottery() {
        return lottery;
    }

    public void setLottery(int lottery) {
        this.lottery = lottery;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
