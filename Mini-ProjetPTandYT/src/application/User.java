package application;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class User extends Thread {
	
	public static List<User> UserList = new ArrayList();
	
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
	
	/*
	 * */
	public static void initFilleXMLE(String fileName) throws IOException{
		initFilleXMLD("./ressource/User.xml");
		User user;
		try{
			while((user = decodeFromFile()) != null){	 
				UserList.add(user);
				System.out.println("name : " + user.getName());
			}
		}catch(Exception e) {
			CloseFilleXMLD();
		}
		
		File f= new File(fileName);
		f.createNewFile();
	    xmle = new XMLEncoder(new FileOutputStream(f,false));
	}
	
	public static void encodeToFile(User u) throws FileNotFoundException, IOException {
		// serialisation de l'objet
		xmle.writeObject(u);
		xmle.flush();
	}
	
	public static void CloseFilleXMLE() {
		// fermeture de l'encodeur
		xmle.close();
	}
	

	/*
	 * */
	
	public static void initFilleXMLD(String fileName) throws IOException{
		File f= new File(fileName);
		f.createNewFile();
	    // ouverture de decodeur
	    xmld = new XMLDecoder(new FileInputStream(fileName));
	}
	
	public static User decodeFromFile() throws FileNotFoundException, IOException {
		Object object = null;
		// deserialisation de l'objet
		object = xmld.readObject();
		return (User) object;
	}
	
	public static void CloseFilleXMLD() {
		// fermeture du decodeur
		xmld.close();
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
		
		initFilleXMLD("./ressource/User.xml");
			User user;
	try{
			  while((user = decodeFromFile()) != null){
					 
				if(user != null)
					UserList.add(user);
					System.out.println("name : " + user.getName());
				}
	} catch(Exception e) {
		CloseFilleXMLD();
    }
		
	}
}