package com.successfactors.sfmooc.domain;

public class Group {
    private Integer id;
    private String name;
    private Boolean canJoin = false;
    public Group( ) {
    }
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCanJoin() {
        return canJoin;
    }

    public void setCanJoin(Boolean canJoin) {
        this.canJoin = canJoin;
    }
}
