package com.home.timemanagment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Timestamp;

public class TimeInterval
{
    private Timestamp startTime;
    private Timestamp endTime;
    private long mlsecDuration;
    
    public long getMlsecDuration() {
        return this.mlsecDuration;
    }
    
    public Timestamp getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(final Timestamp startTime) {
        this.startTime = startTime;
        this.calcDuration();
    }
    
    public Timestamp getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(final Timestamp endTime) {
        this.endTime = endTime;
        this.calcDuration();
    }
    
    private void calcDuration() {
        if (this.startTime == null) {
            this.mlsecDuration = 0L;
            return;
        }
        this.mlsecDuration = ((this.endTime == null) ? Calendar.getInstance().getTimeInMillis() : this.endTime.getTime()) - this.startTime.getTime();
    }
    
    public TimeInterval(final Timestamp timestamp, final Timestamp endTime) {
        this.startTime = timestamp;
        this.endTime = endTime;
        this.calcDuration();
    }
    
    @Override
    public String toString() {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d.M.y HH:mm:ss");
        String startTimeString = "";
        if (this.startTime == null) {
            startTimeString = "  .  .     :  ";
        }
        else {
            startTimeString = simpleDateFormat.format(this.startTime);
        }
        String endTimeString = "";
        if (this.endTime == null) {
            endTimeString = "  .  .     :  ";
        }
        else {
            endTimeString = simpleDateFormat.format(this.endTime);
        }
        final String durationString = Long.toString(this.mlsecDuration / 1000L);
        return startTimeString + " - " + endTimeString + "; Длительность (с): " + durationString;
    }
}