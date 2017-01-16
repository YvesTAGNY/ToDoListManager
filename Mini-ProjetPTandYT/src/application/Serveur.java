package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Serveur {

	private static ArrayList<Task> todoList = new ArrayList<Task>();
	public static final int PORT = 5000;
	
	public static void main(String[] args) throws IOException {
		
		ServerSocket socketServeur = new ServerSocket(PORT);
		do{
			Socket socketClient = socketServeur.accept();
			Thread t = new Thread(new Service(socketClient)) ;
			t.start();
		}while(true);
	}

}
