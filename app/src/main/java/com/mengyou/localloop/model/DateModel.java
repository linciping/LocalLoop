package com.mengyou.localloop.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Administrator on 2016/4/7.
 */
public class DateModel {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public DateModel() {

    }

    public DateModel(Date date) {
        this(date,"yyyy-MM-dd");
    }

    public DateModel(Date date,String pattern)
    {
        SimpleDateFormat format=new SimpleDateFormat(pattern);
        cutDateString(format.format(date));
    }

    public int intervalDay(DateModel model)
    {
        if (year==model.year&&month==model.month)
        {
            return model.day-day;
        }
        return 0;
    }

    private void cutDateString(String dateString)
    {
        String[] dates=dateString.split("-");
        year=Integer.parseInt(dates[0]);
        month=Integer.parseInt(dates[1]);
        day=Integer.parseInt(dates[2]);
    }

    public boolean equalsByDay(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateModel model = (DateModel) o;

        if (year != model.year) return false;
        if (month != model.month) return false;
        return day==model.day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateModel model = (DateModel) o;

        if (year != model.year) return false;
        if (month != model.month) return false;
        if (day != model.day) return false;
        if (hour != model.hour) return false;
        if (minute != model.minute) return false;
        return second == model.second;

    }

    @Override
    public int hashCode() {
        int result = year;
        result = 31 * result + month;
        result = 31 * result + day;
        result = 31 * result + hour;
        result = 31 * result + minute;
        result = 31 * result + second;
        return result;
    }

    public int getYear() {
        return year;
    }

    public int getMinute() {
        return minute;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getHour() {
        return hour;
    }

    public int getSecond() {
        return second;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}
