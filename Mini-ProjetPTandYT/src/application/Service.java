package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

public class Service implements Runnable {

	private Socket serverService;
	
	Service(Socket s){serverService = s;}

	@Override
	public void run() {
		 System.out.println("Serveur en marche");
		 ArrayList<Task> todoList = new ArrayList<Task>();
		 
			try {
				InputStream in = serverService.getInputStream();
				OutputStream out = serverService.getOutputStream();
		        
				BufferedReader bin = new BufferedReader(new InputStreamReader(in));
				PrintStream pout = new PrintStream(out);
				String msg;
				int decision = 0;
				
				/*
				 * gestion de l'inscription et de la connection
				 * */
				do{
					msg = bin.readLine();
					System.out.println("recu : " + msg);
					switch(msg){
						case "NEW" :{
							msg = bin.readLine();
							if(msg != null){
								if(!StaxXMLUser.isExist(msg)){//verification de l'existance du client dans les données
								    pout.println("YES");
								    decision=1;
								}else{
									pout.println("NO");
								}
							}
							break;
						}
						case "OLD" :{
							msg = bin.readLine();
							if(msg != null){
								if(!StaxXMLUser.isExist(msg)){
								    pout.println("YES");
								}else{
									pout.println("NO");
									decision=2;
								}
							}
							break;
						}
					}
					
					if(decision == 1 || decision == 2)
						break;
					
				}while(true);
				
				/*
				 * enregistrement du nouveau client dans le fichier XML
				 * */
				System.out.println("new client : " + msg);
				if(decision == 1){	
					//serialisation du nouveau utilisateur
					User.initFilleXMLE("./ressource/User.xml");
					
					//sauvegarde les users deja enregistré
					for(User u : User.UserList)
						User.encodeToFile(u);
					
					//sauvegarde du nouveau user
		            User user = new User();
		            user.setName(msg);
		            User.encodeToFile(user);
		            
		            User.CloseFilleXMLE();
				}
	            Task.initFilleXMLE("./ressource/Task.xml");
	           
	            /*
				 * gestion des fonctions de tache
				 * */
	            try{
	            	 while(true){ 
			            ObjectInputStream Oint = new ObjectInputStream(in);
			            Task t = (Task) Oint.readObject();
						System.out.println("recu : " + t.toString());
						if(t != null)
						Task.encodeToFile(t);
	            	 }
	            } finally{
	            	Task.CloseFilleXMLE();
	            	serverService.close();
	            }
	            
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("client déconnecté");
			}
		}
	
}
