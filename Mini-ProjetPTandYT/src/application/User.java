package application;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class User extends Thread {
	
	private String userName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String toString(){
		return getUserName();
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

	public static void encodeToFile(User u) throws FileNotFoundException, IOException {
		// serialisation de l'objet
		xmle.writeObject(u);
		xmle.flush();
	}

	public static Object decodeFromFile() throws FileNotFoundException, IOException {
		Object object = null;
		// deserialisation de l'objet
		object = (Task) xmld.readObject();
		return object;
	}
	
	public static void main(String[] args) throws IOException {
		try {
			
			initFilleXMLE("./ressource/User.xml");
			
            User user1 = new User();
            user1.setName("Roger");
            encodeToFile(user1);
            User user2 = new User();
            user2.setName("Rollan");
            encodeToFile(user2);
        } catch(Exception e) {
            e.printStackTrace();
        }
		CloseFilleXMLE();
		/*
		initFilleXMLD("./ressource/User.xml");
		try {
			User user3 = (User) decodeFromFile();
			System.out.println("name : " + user3.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		CloseFilleXMLD();*/
	}
}