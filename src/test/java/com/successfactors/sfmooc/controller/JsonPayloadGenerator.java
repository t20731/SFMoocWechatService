package com.successfactors.sfmooc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.successfactors.sfmooc.domain.Direction;
import com.successfactors.sfmooc.domain.FetchParams;
import com.successfactors.sfmooc.domain.Session;
import com.successfactors.sfmooc.domain.User;
import com.successfactors.sfmooc.utils.DateUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class JsonPayloadGenerator {

    @Test
    public void generateSession(){
        Session session = constructSession();
        generateJsonPayload(session);
    }

    @Test
    public void generateFetchParams(){
        FetchParams fetchParams = new FetchParams();
        fetchParams.setPageNum(1);
        fetchParams.setPageSize(10);
        fetchParams.setDirectionId(4);
        fetchParams.setOrderField("total_members");
        generateJsonPayload(fetchParams);
    }

    private void generateJsonPayload(Object object){
        ObjectMapper mapper = new ObjectMapper();
        try{
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
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
