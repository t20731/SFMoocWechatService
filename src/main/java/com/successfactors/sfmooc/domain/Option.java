package com.successfactors.sfmooc.domain;

public class Option {
    private Integer id;
    private Integer questionId;
    private String number;
    private String content;
    private Integer isAnswer;

    public Option(){
    }

    public Option(String number, String content, Integer isAnswer){
        this.number = number;
        this.content = content;
        this.isAnswer = isAnswer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(Integer isAnswer) {
        this.isAnswer = isAnswer;
    }
}
