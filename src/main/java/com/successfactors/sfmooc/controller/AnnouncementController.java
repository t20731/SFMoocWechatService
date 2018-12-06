package com.successfactors.sfmooc.controller;

import com.successfactors.sfmooc.domain.Announcement;
import com.successfactors.sfmooc.domain.Result;
import com.successfactors.sfmooc.domain.User;
import com.successfactors.sfmooc.service.AnnouncementService;
import com.successfactors.sfmooc.service.UserService;
import com.successfactors.sfmooc.utils.Base64Util;
import com.successfactors.sfmooc.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Result editAnnouncement(@RequestBody Announcement announcement) {
        if (announcement == null || (announcement.getCreatedBy() == null && announcement.getLastModifiedBy() == null)) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        String userId = announcement.getLastModifiedBy();
        User user = userService.getUserById(userId);
        String encodedContent = Base64Util.encode(announcement.getContent());
        logger.info("Content after encoded: " + encodedContent);
        announcement.setContent(encodedContent);
        int status = announcementService.editAnnouncement(announcement);
        return new Result(status, Constants.SUCCESS);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result getAnnouncementList() {
        List<Announcement> announcements = announcementService.getAnnouncementList();
        return new Result(0, Constants.SUCCESS, announcements);
    }
}
