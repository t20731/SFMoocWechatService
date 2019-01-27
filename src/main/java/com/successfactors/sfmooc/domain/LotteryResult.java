package com.successfactors.sfmooc.domain;

import java.util.List;

public class LotteryResult {

    private Integer luckyNumber;
    private List<LuckyDog> luckyDogs;

    public LotteryResult(){

    }

    public LotteryResult(Integer luckyNumber, List<LuckyDog>  luckyDogs){
        this.luckyNumber = luckyNumber;
        this.luckyDogs = luckyDogs;
    }

    public Integer getLuckyNumber() {
        return luckyNumber;
    }

    public void setLuckyNumber(Integer luckyNumber) {
        this.luckyNumber = luckyNumber;
    }

    public List<LuckyDog> getLuckyDogs() {
        return luckyDogs;
    }

    public void setLuckyDogs(List<LuckyDog> luckyDogs) {
        this.luckyDogs = luckyDogs;
    }
}
