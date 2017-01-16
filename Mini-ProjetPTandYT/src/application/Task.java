package application;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
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
	private String endDate;
	private String taskCreator;
	private String taskMaker;

	/* getter and Setter */
	public Task(){}

	public Task(String title, String description, String priority, String taskCreator, String taskMaker) {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.taskCreator = taskCreator;
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

	private Void closeTask() {
		return null;
	}

	private Void deleteTask() {
		return null;
	}

	private Void giveTask() {
		return null;
	}

	private Void takeTask() {
		return null;
	}
	
	public String toString(){
		return "Tâche : " + getTitle() + " " + getDescription() + " " + getPriority() + " " + getState() + " " + getEndDate() + " " + getTaskCreator() + " " + getTaskMaker();
	}
	
	static XMLEncoder xmle;
	static XMLDecoder xmld;
	static void initFilleXMLE(String fileName) throws IOException{
		File f= new File(fileName);
		f.createNewFile();
	    xmle = new XMLEncoder(new FileOutputStream(f,false));
	}
	
	static void CloseFilleXMLE() {
		// fermeture de l'encodeur
		xmle.close();
	}
	static void initFilleXMLD(String fileName) throws IOException{
		File f= new File(fileName);
		f.createNewFile();
	    // ouverture de decodeur
	    xmld = new XMLDecoder(new FileInputStream(fileName));
	}
	
	static void CloseFilleXMLD() {
		// fermeture du decodeur
		xmld.close();
	}

	public static void encodeToFile(Task t) throws FileNotFoundException, IOException {
		// serialisation de l'objet
		xmle.writeObject(t);
		xmle.flush();
	}

	public static Object decodeFromFile() throws FileNotFoundException, IOException {
		Object object = null;
		// deserialisation de l'objet
		object = (Task) xmld.readObject();
		return object;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
		Task t = new Task("Balayer", "Balayer la cour, et jeter les feuilles", "IMPORTANT","Alix", "Pierre");
		Task t1 = new Task("Astiquer", "Balayer la cour, et jeter les feuilles", "FORTE","Alix", "Alix");
		Task t2 = new Task("Décharger", "Balayer la cour, et jeter les feuilles", "FAIBLE","Alix", "Yves");
		
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
	
		
		

	}
}