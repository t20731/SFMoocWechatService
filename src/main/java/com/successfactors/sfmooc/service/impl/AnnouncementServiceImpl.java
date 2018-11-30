package com.successfactors.sfmooc.service.impl;

import com.successfactors.sfmooc.dao.AnnouncementDAO;
import com.successfactors.sfmooc.domain.Announcement;
import com.successfactors.sfmooc.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService{

    @Autowired
    private AnnouncementDAO announcementDAO;

    @Override
    public int editAnnouncement(Announcement announcement) {
        return announcementDAO.editAnnouncement(announcement);
    }

    @Override
    public List<Announcement> getAnnouncementList() {
        return announcementDAO.getAnnouncementList();
    }
}
