package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

private Socket clientSocket = null;
	
	public void openClient(){
		try {
			clientSocket = new Socket("localhost", 7171);
			 System.out.println("Client connecté");
		} catch (UnknownHostException e) {
			 System.err.println("Don't know about host");
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to host");
		}
	}
	
	public void clientSendUserName(String userName){
	  
	    if (clientSocket != null) {
	      try {
			OutputStream out = clientSocket.getOutputStream();
	        
	        PrintStream pout = new PrintStream(out);
	        pout.println(userName);
	        pout.close();
	       /* BufferedReader bin = new BufferedReader(new InputStreamReader(in));
			String msg = bin.readLine();
			System.out.println("recu : " + msg);*/
	      } catch (UnknownHostException e) {
	        System.err.println("Trying to connect to unknown host: " + e);
	      } catch (IOException e) {
	        System.err.println("IOException:  " + e);
	      }
	    }
	}
	
	// objet envoyer par  le client au serveur
	public void clientSendTask(Task t) throws IOException{
		
		OutputStream out = clientSocket.getOutputStream();
		ObjectOutputStream Oout = new ObjectOutputStream(out);
       // PrintStream pout = new PrintStream(out);
        Oout.writeObject(t);
        Oout.close();
	}
	
	public void closeClient(){
		try {
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}