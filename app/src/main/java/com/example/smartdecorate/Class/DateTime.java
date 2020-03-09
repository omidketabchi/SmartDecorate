package com.example.smartdecorate.Class;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {

    private long year;
    private long month;
    private long day;
    private long hour;
    private long minute;
    private long second;

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public long getMonth() {
        return month;
    }

    public void setMonth(long month) {
        this.month = month;
    }

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public long getHour() {
        return hour;
    }

    public void setHour(long hour) {
        this.hour = hour;
    }

    public long getMinute() {
        return minute;
    }

    public void setMinute(long minute) {
        this.minute = minute;
    }

    public long getSecond() {
        return second;
    }

    public void setSecond(long second) {
        this.second = second;
    }

    public DateTime differenceBetweenTwoDateTime(String firstDateTime, String secondDateTime) {

        Date startDate = null;
        Date endDate = null;

        DateTime dateTime = new DateTime();

        String first = firstDateTime.replace("\n", " ");
        String second = secondDateTime.replace("\n", " ");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");

        try {

            startDate = simpleDateFormat.parse(first);
            endDate = simpleDateFormat.parse(second);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        dateTime.setDay(different / daysInMilli);
        different = different % daysInMilli;

        dateTime.setHour(different / hoursInMilli);
        different = different % hoursInMilli;

        dateTime.setMinute(different / minutesInMilli);
        different = different % minutesInMilli;

        dateTime.setSecond(different / secondsInMilli);

        return dateTime;
    }
}
