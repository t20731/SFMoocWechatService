package com.successfactors.t2.service;

import com.successfactors.t2.domain.Announcement;

import java.util.List;

public interface AnnouncementService {
    int editAnnouncement(Announcement announcement);
    List<Announcement> getAnnouncementList();
}
