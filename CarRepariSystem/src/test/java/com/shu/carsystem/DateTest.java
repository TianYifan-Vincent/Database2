package com.shu.carsystem;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateTest {
    @Test
    public void testDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, 1, -1, 18, 30);
        calendar.add(Calendar.HOUR,8);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(calendar.getTime()));
    }
}
