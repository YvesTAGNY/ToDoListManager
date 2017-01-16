package application;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
	
	 @Override
	public String toString(){
		return "User ::"  +userName;
		
	}
	
	public static void encodeToFile(Object object, String fileName) throws FileNotFoundException, IOException {
		// ouverture de l'encodeur vers le fichier
		XMLEncoder encoder = new XMLEncoder(new FileOutputStream(fileName));
		try {
			// serialisation de l'objet
			encoder.writeObject(object);
			encoder.flush();
	    } finally {
	    	// fermeture de l'encodeur
	        encoder.close();
	    }
	}
	
	public static Object decodeFromFile(String fileName) throws FileNotFoundException, IOException {
	    Object object = null;
	    // ouverture de decodeur
	    XMLDecoder decoder = new XMLDecoder(new FileInputStream(fileName));
	    try {
	        // deserialisation de l'objet
	        object = decoder.readObject();
	    } finally {
	        // fermeture du decodeur
	        decoder.close();
	    }
	    return object;
	}
	
	public static void main(String[] args) {
		try {
            User user1 = new User();
            user1.setName("Roger");
            encodeToFile(user1, "./ressource/User.xml");
            User user2 = new User();
            user2.setName("Rollan");
            encodeToFile(user2, "./ressource/User.xml");
        } catch(Exception e) {
            e.printStackTrace();
        }
		
		try {
			User user3 = (User) decodeFromFile("./ressource/User.xml");
			System.out.println("name : " + user3.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}