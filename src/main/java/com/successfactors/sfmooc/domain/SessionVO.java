package com.successfactors.sfmooc.domain;

import java.util.List;

public class SessionVO {

    private List<Direction> directions;

    private List<Session> sessions;

    public SessionVO() {
    }

    public List<Direction> getDirections() {
        return directions;
    }

    public void setDirections(List<Direction> directions) {
        this.directions = directions;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}
