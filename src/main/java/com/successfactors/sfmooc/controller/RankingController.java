package com.successfactors.sfmooc.controller;

import com.successfactors.sfmooc.domain.*;
import com.successfactors.sfmooc.service.RankingService;
import com.successfactors.sfmooc.service.SessionService;
import com.successfactors.sfmooc.service.UserService;
import com.successfactors.sfmooc.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ranking")
public class RankingController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionService sessionService;

    @Autowired
    private RankingService rankingService;

    @Autowired
    private UserService userService;

    @RequestMapping(value="/list", method = RequestMethod.GET)
    public List<RankingItem> getRankingListBySeason(){
        return rankingService.getRankingListBySeason("S1");
    }

    @RequestMapping(value = "/list/{groupId}", method = RequestMethod.GET)
    public List<RankingItem> getRankingListByGroup(@PathVariable ("groupId") int groupId){
        return rankingService.getRankingListByGroupId("S1", groupId);
    }

    @RequestMapping(value="/points/{userId}", method = RequestMethod.GET)
    public Result getPointsDetailForUser(@PathVariable("userId") String userId){
        if(StringUtils.isEmpty(userId)){
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        User user = userService.getUserById(userId);
        if(user != null){
            Map<String, Object> retObj = new HashMap<>();
            retObj.put("user", user);
            List<Points> pointsList = rankingService.getPointsDetailForUser(userId);
            int totalPoints = 0;
            if(pointsList != null){
                for(Points points : pointsList){
                    totalPoints += points.getCheckin();
                    totalPoints += points.getHost();
                    totalPoints += points.getExam();
                    totalPoints += points.getLottery();
                }
            }
            retObj.put("totalPoints", totalPoints);
            retObj.put("pointsList", pointsList);
            return new Result(1, Constants.SUCCESS, retObj);
        }
        return new Result(-1, Constants.NOT_AUTHORIZED);
    }

    @RequestMapping(value="/listSession/{group}", method = RequestMethod.GET)
    public List<RankingSession> getSessionRankingList(@PathVariable ("group") int group){
        return sessionService.getSessionRankingList(group);
    }

    
}
