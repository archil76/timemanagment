package com.home.timemanagment;

import java.util.ArrayList;

public class DeveloperTasksList {
	
	public TaskState state;
	public ArrayList<DeveloperTask> list;
	
	public TaskState getState() {
		return state;
	}
	public void setState(TaskState state) {
		this.state = state;
	}
	public ArrayList<DeveloperTask> getList() {
		return list;
	}
	public void setList(ArrayList<DeveloperTask> list) {
		this.list = list;
	}
	
	
	
	

}
