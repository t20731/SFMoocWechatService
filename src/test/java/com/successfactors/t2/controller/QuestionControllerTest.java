package com.successfactors.t2.controller;

import com.successfactors.t2.domain.Announcement;
import com.successfactors.t2.domain.Answer;
import com.successfactors.t2.domain.Option;
import com.successfactors.t2.domain.Question;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionControllerTest {

    public static void main(String... args){
        ObjectMapper mapper = new ObjectMapper();
       // Question question = constructQuestion();
       // Answer answer = constructAnswer();
        Announcement announcement = constructAnnouncement();
        try{
            String jsonString = mapper.writeValueAsString(announcement);
            System.out.println(jsonString);
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(announcement);
            System.out.println(jsonString);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static Question constructQuestion(){
        Question question = new Question();
        question.setOwner("oCNCe4vsdC_dr0wv6uY7uus1GheA");
        question.setContent("what's your name?");
        List<Option> options = new ArrayList<>();
        options.add(new Option("A", "ada", 1));
        options.add(new Option("B", "bella", 0));
        options.add(new Option("C", "cici", 0));
        options.add(new Option("C", "delta", 0));
        question.setOptions(options);
        return question;
    }

    private static Answer constructAnswer(){
        Answer answer = new Answer();
        answer.setUserId("oCNCe4vsdC_dr0wv6uY7uus1GheA");
        Map<Integer, String> selectedMap = new HashMap<>();
        selectedMap.put(34, "A");
        selectedMap.put(42, "A");
        selectedMap.put(43, "A");
        answer.setAnswerMap(selectedMap);
        return answer;
    }

    private static Announcement constructAnnouncement(){
        Announcement announcement = new Announcement();
        announcement.setContent("今日leetcode题目366");
        announcement.setCreatedBy("oCNCe4vsdC_dr0wv6uY7uus1GheA");
        announcement.setLastModifiedBy("oCNCe4vsdC_dr0wv6uY7uus1GheA");
        return announcement;
    }

}
