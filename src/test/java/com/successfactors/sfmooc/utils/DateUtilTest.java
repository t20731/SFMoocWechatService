package com.successfactors.sfmooc.utils;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilTest {

    @Test
    public void testFormatDateToMinutes(){
        String dateStr = "2018-10-21 11:48:00.0";
        Assert.assertEquals("2018-10-21 11:48", DateUtil.formatDateToMinutes(dateStr));
    }
}
