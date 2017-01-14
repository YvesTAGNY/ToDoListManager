package application;

import sun.util.calendar.BaseCalendar.Date;

public class Task {
	
	/* Attributes */
	private String title;
	private String description;
	private String priority;
	private int state;
	private User taskMaker ; 
	private User taskCreator;
	private Date endDate;
	
	/* getter and Setter*/
	
     public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public User getTaskMaker() {
		return taskMaker;
	}
	public void setTaskMaker(User taskMaker) {
		this.taskMaker = taskMaker;
	}
	public User getTaskCreator() {
		return taskCreator;
	}
	public void setTaskCreator(User taskCreator) {
		this.taskCreator = taskCreator;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/* Methods */
	
	private Void closeTask(){
		return null;}
	
	private Void deleteTask(){
		return null;}
	
	private Void giveTask(){
		return null;}
	
	private Void takeTask(){
		return null;}
}