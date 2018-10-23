package com.successfactors.sfmooc.domain;

public class UserSession {
    private Session session;
    private boolean userRegistered;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public boolean isUserRegistered() {
        return userRegistered;
    }

    public void setUserRegistered(boolean userRegistered) {
        this.userRegistered = userRegistered;
    }
}
