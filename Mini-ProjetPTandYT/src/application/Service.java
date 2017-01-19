package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class Service implements Runnable {

	private Socket serverService;
	
	Service(Socket s){serverService = s;}

	@Override
	public void run() {
		 System.out.println("Serveur en marche");
			try {
				InputStream in = serverService.getInputStream();
				OutputStream out = serverService.getOutputStream();
				
				BufferedReader bin = new BufferedReader(new InputStreamReader(in));
				String msg = bin.readLine();
				System.out.println("recu : " + msg);
				
				//serialisation du nouveau utilisateur
				User.initFilleXMLE("./ressource/User.xml");
				
	            User user = new User();
	            user.setName(msg);
	            User.encodeToFile(user);
	            
	            User.CloseFilleXMLE();

	            while(true){ Thread.sleep(1000);msg = bin.readLine();
				System.out.println("recu : " + msg);}
	         
	           //bin.close();
	            
	            /*
	            ObjectInputStream Oint = new ObjectInputStream(in);
	            Task t = (Task) Oint.readObject();
				System.out.println("recu : " + t.toString());
				Oint.close();*/
				 /*PrintStream pout = new PrintStream(out);
			     pout.println("Combien ?");*/
	            
				//System.out.println("Serveur arrête");
				//serverService.close();
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
}
