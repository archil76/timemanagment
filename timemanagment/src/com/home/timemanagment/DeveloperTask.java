package com.home.timemanagment;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DeveloperTask
{
    private String id;
    private String name;
    private Customer customer;
    private boolean isActual;
 
	private List<TimeInterval> timeIntervals;
    private TaskState state;
    private long mlsecDuration;
    private String durationString;
    
    private DeveloperTask() {
        
    }
    
    private DeveloperTask(final String id) {
        
    }
    

	public DeveloperTask(String id, String name) {
        this.timeIntervals = new ArrayList<TimeInterval>();
        this.id = id;
        this.name = name;
        this.state = TaskState.NEW;
        this.isActual = false;
    }
   
    public DeveloperTask(final String id, final String name, TaskState state) {
        this.timeIntervals = new ArrayList<TimeInterval>();
        this.id = id;
        this.name = name;
        this.state = state;
        this.isActual = false;
    }
    
    public DeveloperTask(String id, String name, TaskState state, boolean isActual) {
        this.timeIntervals = new ArrayList<TimeInterval>();
        this.id = id;
        this.name = name;
        this.state = state;
        this.isActual = isActual;
    }
    
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    public List<TimeInterval> getTimeIntervals() {
        return this.timeIntervals;
    }
    
    public void setTimeIntervals(final List<TimeInterval> timeIntervals) {
        this.timeIntervals = timeIntervals;
    }
    
    public void addTimeInterval(final Timestamp startTime, final Timestamp endTime) {
        final TimeInterval timeInterval = new TimeInterval(startTime, endTime);
        this.timeIntervals.add(timeInterval);
    }
    
    public TimeInterval getLastTimeInterval() {
        final int timeIntervalsSize = this.timeIntervals.size();
        if (timeIntervalsSize > 0) {
            return this.timeIntervals.get(timeIntervalsSize - 1);
        }
        return null;
    }
    
    public TaskState getState() {
        return this.state;
    }
    
    public void setState(final TaskState state) {
        this.state = state;
    }
    
    public long getMlsecDuration() {
        return this.mlsecDuration;
    }
    
    public void setMlsecDuration(final long mlsecDuration) {
        this.mlsecDuration = mlsecDuration;
    }
    
    public String getDurationString() {
        long secDuration = this.mlsecDuration / 1000L;
        int hours = (int)secDuration / 3600;
        int minutes = (int)(secDuration % 3600L) / 60;
        int seconds = (int)(secDuration % 3600L) % 60;
        return String.format("%d:%d:%d", hours, minutes, seconds);
    }
    
    public void setDurationString(final String durationString) {
    }
    
    public boolean isActual() {
  		return isActual;
  	}
    
    public boolean getIsActual() {
  		return isActual;
  	}

  	public void setActual(boolean isActual) {
  		this.isActual = isActual;
  	}
  	
  	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}