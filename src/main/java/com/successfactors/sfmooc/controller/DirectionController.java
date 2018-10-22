package com.successfactors.sfmooc.controller;

import com.successfactors.sfmooc.domain.*;
import com.successfactors.sfmooc.service.DirectionService;
import com.successfactors.sfmooc.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/direction")
public class DirectionController {

    @Autowired
    private DirectionService directionService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result loadAll() {
        List<Direction> directions = directionService.getAll();
        return new Result(1, Constants.SUCCESS, directions);
    }

}
