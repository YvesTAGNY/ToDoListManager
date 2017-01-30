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

	ArrayList<Task> todoList = new ArrayList<Task>();

	Service(Socket s) {
		serverService = s;
	}

	@Override
	public void run() {
		System.out.println("Serveur en marche");

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
					System.out.println(" tâche : " + task.toString());
				}
			} catch (Exception e) {
				System.out.println("fin de recupération des taches");
				Task.CloseFilleXMLD();
			}

			saveTask();

			if (!todoList.isEmpty()) {
				pout.println("FULL");
				task.setTodolist(todoList);

				try {
					Registry a = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
					a.rebind("od", task);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else
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
														// dans les données
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
			System.out.println("Client : " + msg);
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
			try {
				while (true) {

					msg = bin.readLine();
					if (msg != null) {
						switch (msg) {
						case "AJOUTER": {
							msg = bin.readLine();
							String pt[] = msg.split("/");
							Task t = new Task(pt[0], pt[1], pt[2], Integer.parseInt(pt[3]), pt[4], pt[5]);
							todoList.add(t);
							System.out.println("nouvelle tâche : " + t.toString());
							saveTask();
							break;
						}
						case "ATTRIBUER": {
							msg = bin.readLine();
							if (!StaxXMLUser.isExist(msg)) {
								pout.println("NO");
								System.out.println(msg + " n'est pas enregistré");
							} else {
								pout.println("YES");
								msg = bin.readLine();
								String pt[] = msg.split(" ");
								System.out.println(
										"Modification a faire, attribution de la tâche : " + pt[0] + " à " + pt[1]);
								indexTask(todoList, pt[0]).setTaskMaker(pt[1]);
							}
							break;
						}
						case "PRENDRE": {
							msg = bin.readLine();
							String pt[] = msg.split(" ");
							System.out.println("Modification a faire, " + pt[1] + " prend de la tâche : " + pt[0]);
							indexTask(todoList, pt[0]).setTaskMaker(pt[1]);
							saveTask();
							break;
						}
						case "TERMINER": {
							msg = bin.readLine();
							String pt[] = msg.split(" ");
							System.out.println("Modification a faire, la tâche : " + pt[0] + " est terminer");
							indexTask(todoList, pt[0]).closeTask();
							saveTask();
							break;
						}
						case "SUPPRIMER": {
							msg = bin.readLine();
							String pt[] = msg.split(" ");
							System.out.println("Modification a faire, la tâche : " + pt[0] + " est supprimer");
							todoList.remove(indexTask(todoList, pt[0]));
							saveTask();
							break;
						}
						case "ACTUALISER": {
							ArrayList<Task> tl = updateTask();
							todoList.removeAll(todoList);
							todoList = tl;
							String stl = todoList.toString();
							System.out.println("Mise à jour " + stl);
							pout.println(stl);
							saveTask();
							break;
						}
						case "QUITTER": {
							saveTask();
							serverService.close();
							System.out.println("client déconnecté.");
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
			Task.CloseFilleXMLE();
			try {
				Task.initFilleXMLE("./ressource/Task.xml");
				for (Task t : todoList)
					Task.encodeToFile(t);
				Task.CloseFilleXMLE();
				serverService.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("client déconnecté");
		}
	}

	/*
	 * recupérer la tâche à modifier
	 */
	private Task indexTask(ArrayList<Task> todoList, String taskTitle) {
		for (Task tl : todoList) {
			if (tl.getTitle().equals(taskTitle)) {
				return tl;
			}
		}
		return null;
	}

	/*
	 * sauvegarde de tâches
	 */
	private void saveTask() throws IOException {
		Task.initFilleXMLE("./ressource/Task.xml");
		for (Task t : todoList)
			Task.encodeToFile(t);
		Task.CloseFilleXMLE();
		System.out.println("todolist : " + todoList);
	}

	/*
	 * recupération de tâches
	 */
	private ArrayList<Task> updateTask() throws IOException {
		ArrayList<Task> todoList = new ArrayList<Task>();
		Task task = null;
		try {
			Task.initFilleXMLD("./ressource/Task.xml");

			while ((task = Task.decodeFromFile()) != null) {
				todoList.add(task);
			}
		} catch (Exception e) {
			System.out.println("recupération  terminé");
			Task.CloseFilleXMLD();
		}
		return todoList;
	}
}
