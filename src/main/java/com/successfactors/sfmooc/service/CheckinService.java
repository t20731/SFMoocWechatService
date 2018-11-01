package com.successfactors.sfmooc.service;

public interface CheckinService {
      String generateCheckinCode();
      int confirmCheckinCode(Integer sessionId, String code, String userId);
      String generateTileImageSrc();
}
