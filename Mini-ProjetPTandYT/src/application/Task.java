package application;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;


public class Task implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* Attributes */
	private String title;
	private String description;
	private String priority;
	private int state;
	private Date endDate;
	private String taskCreator;
	private String taskMaker;
	
	

	/* getter and Setter */
	public Task(){}

	public Task(String title, String description, String priority, String taskMaker) {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.taskMaker = taskMaker;

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

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
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

	private Void closeTask() {
		return null;
	}

	private Void deleteTask() {
		return null;
	}

	private Void giveTask() {// attribuer une tache, besoin d'un nom en paramêtre
		return null;
	}
	
	public void takeTask(User u) {// s'attribuer une tâche, pas besoin de rentrer un nom en paramêtre
		this.taskMaker= u.getUserName();
		
	}
	

	public static void encodeToFile(Task t, String fileName) throws FileNotFoundException, IOException {
		// ouverture de l'encodeur vers le fichier
		File f= new File(fileName);
		f.createNewFile();
	    final XMLEncoder xmle = new XMLEncoder(new FileOutputStream(f,false));
		try {
			// serialisation de l'objet
			
			xmle.writeObject(t);
			xmle.flush();
		} finally {
			// fermeture de l'encodeur
			xmle.close();
		}
	}

	public static Object decodeFromFile(String fileName) throws FileNotFoundException, IOException {
		Object object = null;
		// ouverture de decodeur
		XMLDecoder decoder = new XMLDecoder(new FileInputStream(fileName));
		try {
			// deserialisation de l'objet
			object = (Task) decoder.readObject();
		} finally {
			// fermeture du decodeur
			decoder.close();
		}
		return object;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
		Task t = new Task("Balayer", "Balayer la cour, et jeter les feuilles", "IMPORTANT", "Pierre");
		Task t1 = new Task("Astiquer", "Balayer la cour, et jeter les feuilles", "FORTE", "Alix");
		Task t2 = new Task("Décharger", "Balayer la cour, et jeter les feuilles", "FAIBLE", "Yves");
		
		
		
		try {
			encodeToFile(t, "./ressource/User.xml");
			encodeToFile(t1, "./ressource/User.xml");
			encodeToFile(t2, "./ressource/User.xml");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("une tache : " + t.getTitle());
		System.out.println("une tache : " + t1.getTitle());
		System.out.println("une tache : " + t2.getTitle());
	
		
		

	}
}
