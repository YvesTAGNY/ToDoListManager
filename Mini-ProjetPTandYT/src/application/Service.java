package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Service implements Runnable {

	private Socket serverService;

	Service(Socket s) {
		serverService = s;
	}

	@Override
	public void run() {
		System.out.println("Serveur en marche");
		ArrayList<Task> todoList = new ArrayList<Task>();

		
		
		/*
		 * Communication client seveur
		 */
		try {
			InputStream in = serverService.getInputStream();
			OutputStream out = serverService.getOutputStream();

			BufferedReader bin = new BufferedReader(new InputStreamReader(in));
			PrintStream pout = new PrintStream(out);
			String msg;
			int decision = 0;

			/*
			 * RecupÃ©ration des taches dans le fichier xml
			 */
			Task task = null;
			try {
				Task.initFilleXMLD("./ressource/Task.xml");

				while ((task = Task.decodeFromFile()) != null) {
					todoList.add(task);
					System.out.println(" t : " + task.toString());
				}
			} catch (Exception e) {
				System.out.println("fin de recupération des taches");
				Task.CloseFilleXMLD();
			}
			
			if(!todoList.isEmpty()){
				pout.println("FULL");
				task.setTodolist(todoList);
		
				try {
					Registry a = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
					a.rebind("od", task);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else
				pout.println("VOID");
			
			/*
			 * gestion de l'inscription et de la connection
			 */
			do {
				msg = bin.readLine();
				System.out.println("recu : " + msg);
				switch (msg) {
				case "NEW": {
					msg = bin.readLine();
					if (msg != null) {
						if (!StaxXMLUser.isExist(msg)) {// verification de
														// l'existance du client
														// dans les donnÃ©es
							pout.println("YES");
							decision = 1;
						} else {
							pout.println("NO");
						}
					}
					break;
				}
				case "OLD": {
					msg = bin.readLine();
					if (msg != null) {
						if (!StaxXMLUser.isExist(msg)) {
							pout.println("YES");
						} else {
							pout.println("NO");
							decision = 2;
						}
					}
					break;
				}
				}

				if (decision == 1 || decision == 2)
					break;

			} while (true);

			/*
			 * enregistrement du nouveau client dans le fichier XML
			 */
			System.out.println("new client : " + msg);
			if (decision == 1) {
				// serialisation du nouveau utilisateur
				User.initFilleXMLE("./ressource/User.xml");

				// sauvegarde les users deja enregistrÃ©
				for (User u : User.UserList)
					User.encodeToFile(u);

				// sauvegarde du nouveau user
				User user = new User();
				user.setName(msg);
				User.encodeToFile(user);

				User.CloseFilleXMLE();
			}

			/*
			 * gestion des fonctions de tache
			 */
			Task.initFilleXMLE("./ressource/Task.xml");
			try {
				while (true) {

					msg = bin.readLine();
					if (msg != null) {
						switch (msg) {
							case "AJOUTER": {
								msg = bin.readLine();
	            		 		String pt[] = msg.split("/");
	    			            Task t = new Task(pt[0], pt[1], pt[2], Integer.parseInt(pt[3]), pt[4], pt[5]);
	    						System.out.println("recu : " + t.toString());
	    						if(t != null)
	    							Task.encodeToFile(t);
	            		 		break;	
							}
							case "ATTRIBUER": {
								break;
							}
							case "PRENDRE": {
								break;
							}
							case "TERMINER": {
								break;
							}
							case "SUPPRIMER": {
								break;
							}

						}
					}
				}
			} finally {
				Task.CloseFilleXMLE();
				serverService.close();
			}

		} catch (IOException e) {
			System.out.println("client déconnecté");
		}
	}

}
