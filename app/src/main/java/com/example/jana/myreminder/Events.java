package com.example.jana.myreminder;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Events {
    private String mName;
    private Date mDate;
    private Time mTime;
    private String dateTime;
    private String mPhoto;
    private String mComment;


    public Events(String name, Date date, Time time, String photo, String comment) {
        this.mName = name;
        this.mDate = date;
        this.mTime = time;
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                mDate.getYear()+"-"+mDate.getMonth()+"-"+mDate.getDate()+ " " + mTime.getHours()+":"+mTime.getMinutes()+":"+mTime.getSeconds(), Locale.getDefault());
        Date date1 = new Date();
        this.dateTime = dateFormat.format(date1);
        this.mPhoto = photo;
        this.mComment = comment;
    }

    public Events() {}

    public Events(String string, String string1, String string2, String string3) {
        this.mName = string;
        this.dateTime = string1;
        this.mPhoto = string2;
        this.mComment = string3;
    }

    public String getDateTime(){
        return this.dateTime;
    }

    public String getmComment() {
        return mComment;
    }

    public void setmComment(String mComment) {
        this.mComment = mComment;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(String mPhoto) {
        this.mPhoto = mPhoto;
    }

    public Time getmTime() {
        return mTime;
    }

    public void setmTime(Time mTime) {
        this.mTime = mTime;
    }

}
