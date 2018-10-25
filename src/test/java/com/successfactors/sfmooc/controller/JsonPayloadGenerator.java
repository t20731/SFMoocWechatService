package com.successfactors.sfmooc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.successfactors.sfmooc.domain.*;
import com.successfactors.sfmooc.utils.DateUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JsonPayloadGenerator {

    @Test
    public void generateIdList(){
        List<Integer> idList = new ArrayList<>();
        idList.add(1001);
        idList.add(1002);
        generateJsonPayload(idList);
    }

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
        Location location = new Location();
        location.setName("PVG03 D5.7");
        session.setLocation(location);
        session.setDirection(new Direction(3));
        session.setDifficulty(0);
        session.setStatus(1);
        return session;
    }
}
