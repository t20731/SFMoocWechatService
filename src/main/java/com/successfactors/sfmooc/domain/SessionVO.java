package com.successfactors.sfmooc.domain;

import java.util.List;

public class SessionVO {

    private List<Direction> directions;

    private List<Session> hotSessions;

    private List<Session> sessions;

    private List<Location> locations;

    private List<Group> groups;

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

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Session> getHotSessions() {
        return hotSessions;
    }

    public void setHotSessions(List<Session> hotSessions) {
        this.hotSessions = hotSessions;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
