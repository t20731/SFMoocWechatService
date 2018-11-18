package com.successfactors.sfmooc.domain;

public class Like {
    private String userId;
    private Integer sessionId;
    /*
    like = 0 means unlike - default value
    like = 1 means like
     */
    private int like;

    public Like(String userId, Integer sessionId, int like) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.like = like;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public Integer getSessionId() { return sessionId; }

    public void setSessionId(Integer sessionId) { this.sessionId = sessionId; }

    public int getLike() {return like;}

    public void setLike(int like) {this.like = like;}
}
