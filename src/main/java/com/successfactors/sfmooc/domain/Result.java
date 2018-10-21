package com.successfactors.sfmooc.domain;

public class Result {

    private int status;

    private String msg;

    private Object retObj;

    public Result(int status, String msg){
        this.status = status;
        this.msg = msg;
    }

    public Result(int status, String msg, Object retObj){
        this.status = status;
        this.msg = msg;
        this.retObj = retObj;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getRetObj() {
        return retObj;
    }

    public void setRetObj(Object retObj) {
        this.retObj = retObj;
    }
}
