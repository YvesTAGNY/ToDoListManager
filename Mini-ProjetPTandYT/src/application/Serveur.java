package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import controller.ControleurClient;

public class Serveur {
	
	private static ArrayList<Task> todoList = new ArrayList<Task>();
	
	public static ArrayList<Task> getTodoList() {
		return todoList;
	}

	public static void setTodoList(ArrayList<Task> todoList) {
		Serveur.todoList = todoList;
	}

	public static final int PORT = 7171;
	
	public static void main(String[] args) throws IOException {
		
		ServerSocket socketServeur = new ServerSocket(PORT);
		do{
			Socket socketClient = socketServeur.accept();
			Thread t = new Thread(new Service(socketClient)) ;
			t.start();
		}while(true);
	}

}
