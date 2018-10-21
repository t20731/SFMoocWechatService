package com.successfactors.sfmooc.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Util {

    public static String encode(String content){
        return Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));
    }

    public static String decode(String encodedContent){
        return new String(Base64.getDecoder().decode(encodedContent), StandardCharsets.UTF_8);
    }
}
