package application;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import controller.ControleurClient;


public class Task extends UnicastRemoteObject implements ODI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* Attributes */
	private String title;
	private String description;
	private String priority;
	private int state;
	private String endDate;
	private String taskCreator;
	private String taskMaker;

	private ArrayList<Task> todoList;
	public Task tsk;
	
	/* getter and Setter */
	public Task()throws RemoteException {} 

	public Task(String title, String description, String priority, int state ,  String taskCreator, String taskMaker)throws RemoteException {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.state = state;
		this.taskCreator = taskCreator;
		this.taskMaker = taskMaker;

	}
	
	public Task(String title, String description, String priority, int state , String endDate,  String taskCreator, String taskMaker)throws RemoteException {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.state = state;
		this.endDate = endDate;
		this.taskCreator = taskCreator;
		this.taskMaker = taskMaker;

	}
	public String etatToString(){
		switch (this.state){
			case 0 : return "INIT";
			case 1 : return "EN COURS";
			case 2 : return "TERMINER";
		}
		return null;
	}
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

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTaskCreator() {
		return taskCreator;
	}

	public void setTaskCreator(String taskCreator) {
		this.taskCreator = taskCreator;
	}

	public String getTaskMaker() {
		return taskMaker;
	}

	public void setTaskMaker(String taskMaker) {
		this.taskMaker = taskMaker;
	}

	/* Methods */

	public void closeTask() {
		Date aujourdhui = new Date();
		DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(
		DateFormat.MEDIUM,DateFormat.MEDIUM);
		endDate = mediumDateFormat.format(aujourdhui);
		this.setEndDate(endDate);
		System.out.println("Date de fin : " +endDate);
		this.state = 2;
	}

	private void deleteTask(Task t) {
		ControleurClient.getTodoList().remove(t);
	}

	private void giveTask(User u) {
		this.taskMaker = u.getUserName();
	}

	private void takeTask(User u) {
		this.taskMaker = u.getUserName();
	}
	
	public String toString(){
		return "T:" + getTitle() + "/" + getDescription() + "/" + getPriority() + "/" + getState() + "/" + getEndDate() + "/" + getTaskCreator() + "/" + getTaskMaker();
	}
	
	static XMLEncoder xmle;
	static XMLDecoder xmld;
	
	static void initFilleXMLE(String fileName) throws IOException{
		File f= new File(fileName);
		f.createNewFile();
	    xmle = new XMLEncoder(new FileOutputStream(f,false));
	}
	
	public static void encodeToFile(Task t) throws FileNotFoundException, IOException {
		// serialisation de l'objet
		xmle.writeObject(t);
		xmle.flush();
	}
	
	static void CloseFilleXMLE() {
		// fermeture de l'encodeur
		xmle.close();
	}
	
	/*
	 * */
	
	static void initFilleXMLD(String fileName) throws IOException{
		File f= new File(fileName);
		f.createNewFile();
	    // ouverture de decodeur
	    xmld = new XMLDecoder(new FileInputStream(fileName));
	}
	
	public static Task decodeFromFile() throws FileNotFoundException, IOException {
		Object object = null;
		// deserialisation de l'objet
		object = (Task) xmld.readObject();
		return (Task) object;
	}

	static void CloseFilleXMLD() {
		// fermeture du decodeur
		xmld.close();
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
		Task t = new Task("Balayer", "Balayer la cour, et jeter les feuilles", "IMPORTANT",1,"Alix", "Pierre");
		Task t1 = new Task("Astiquer", "Balayer la cour, et jeter les feuilles", "FORTE",0,"Alix", "Alix");
		Task t2 = new Task("Décharger", "Balayer la cour, et jeter les feuilles", "FAIBLE",2,"Alix", "Yves");
		
		initFilleXMLE("./ressource/Task.xml");
		
		try {
			encodeToFile(t);
			encodeToFile(t1);
			encodeToFile(t2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CloseFilleXMLE();
		
		System.out.println("une tache : " + t.getTitle());
		System.out.println("une tache : " + t1.getTitle());
		System.out.println("une tache : " + t2.getTitle());

		Task.initFilleXMLD("./ressource/User.xml");
		Task task;
		try{
			while((task = Task.decodeFromFile()) != null){	 
				System.out.println("une tache d : " + task.getTitle());
			}
		}catch(Exception e) {
			System.out.println("fin");
			CloseFilleXMLD();
		}
	}

	@Override
	public String getTodolist() throws RemoteException {
		// TODO Auto-generated method stub
		if(todoList != null)
			return todoList.toString();
		else
			return "VIDE";
	}
	
	public void setTodolist( ArrayList<Task> tl){
		todoList = tl;
	}
}