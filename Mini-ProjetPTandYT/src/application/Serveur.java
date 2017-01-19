package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import controller.ControleurClient;

public class Serveur {

	public static final int PORT = 7171;
	
	public static void main(String[] args) throws IOException {
		
		ServerSocket socketServeur = new ServerSocket(PORT);
		do{
			 System.out.println("Serveur en attente de connexion ...");
			Socket socketClient = socketServeur.accept();
			Thread t = new Thread(new Service(socketClient)) ;
			t.start();
		}while(true);
	}

}
