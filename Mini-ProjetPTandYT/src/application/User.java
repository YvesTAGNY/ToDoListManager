package application;

import java.util.ArrayList;

public class User extends Thread {
	
	private String userName;
	private static ArrayList<Task> todoList = new ArrayList<Task>();
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}