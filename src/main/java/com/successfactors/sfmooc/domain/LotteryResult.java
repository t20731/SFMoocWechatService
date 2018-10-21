package com.successfactors.sfmooc.domain;

import java.util.List;

public class LotteryResult {

    private Integer luckyNumber;
    private List<String> luckyDogs;

    public LotteryResult(){

    }

    public LotteryResult(Integer luckyNumber, List<String> luckyDogs){
        this.luckyNumber = luckyNumber;
        this.luckyDogs = luckyDogs;
    }

    public Integer getLuckyNumber() {
        return luckyNumber;
    }

    public void setLuckyNumber(Integer luckyNumber) {
        this.luckyNumber = luckyNumber;
    }

    public List<String> getLuckyDogs() {
        return luckyDogs;
    }

    public void setLuckyDogs(List<String> luckyDogs) {
        this.luckyDogs = luckyDogs;
    }
}
