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
				
				
				/*ObjectOutputStream Oout = new ObjectOutputStream(out);
				Task.initFilleXMLD("./ressource/User.xml");
				Task task;
				try{
					while((task = Task.decodeFromFile()) != null){	 
						todoList.add(task);
						System.out.println("name t : " + task.toString());
					}
				}catch(Exception e) {
					System.out.println("lt :2 ");
					Task.CloseFilleXMLD();
					e.printStackTrace();
				}
				
		        Oout.writeObject(todoList);*/
		        
				BufferedReader bin = new BufferedReader(new InputStreamReader(in));
				PrintStream pout = new PrintStream(out);
				String msg = bin.readLine();
				String accord = "NO";
				
				do{
					System.out.println("recu : " + msg);
					
					//verification de l'existance du client dans les données
					if(msg != null){
						if(!StaxXMLUser.isExist(msg)){
						    pout.println("YES");
						    break;
						}else{
							pout.println("NO");
						}
					}
					msg = bin.readLine();
					
					if(msg.equals("OLD"))
						break;
					
				}while(accord.equals("NO"));
				
				msg = bin.readLine();
				System.out.println("recu- : " + msg);
				if(!msg.equals("OLD")){	
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
