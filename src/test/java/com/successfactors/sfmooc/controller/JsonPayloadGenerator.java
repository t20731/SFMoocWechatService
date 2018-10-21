package com.successfactors.sfmooc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.successfactors.sfmooc.domain.Direction;
import com.successfactors.sfmooc.domain.Session;
import com.successfactors.sfmooc.domain.User;
import com.successfactors.sfmooc.utils.DateUtil;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class JsonPayloadGenerator {
    public static void main(String... args){
        ObjectMapper mapper = new ObjectMapper();
        Session session = constructSession();
        try{
            String jsonString = mapper.writeValueAsString(session);
            System.out.println(jsonString);
            jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(session);
            System.out.println(jsonString);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static Session constructSession(){
        Session session = new Session();
        session.setOwner(new User("mango"));
        session.setTopic("React 16 quick start");
        session.setDescription("React 16 quick start React 16 quick start React 16 quick start");
        session.setStartDate(DateUtil.formatDateToMinutes(new Date()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 30);
        session.setEndDate(DateUtil.formatDateToMinutes(calendar.getTime()));
        session.setLocation("PVG03 D5.7");
        session.setDirection(new Direction(3));
        session.setDifficulty(0);
        session.setStatus(1);
        return session;
    }
}
