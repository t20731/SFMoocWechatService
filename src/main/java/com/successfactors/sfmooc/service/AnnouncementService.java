package com.successfactors.sfmooc.service;

import com.successfactors.sfmooc.domain.Announcement;

import java.util.List;

public interface AnnouncementService {
    int editAnnouncement(Announcement announcement);
    List<Announcement> getAnnouncementList();
}
